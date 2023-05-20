import 'dart:async';
import 'dart:developer';
import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:flutter/material.dart';
import 'package:firebase_core/firebase_core.dart';
// import 'firebase_options.dart';
// import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:flutter_local_notifications/flutter_local_notifications.dart';
import 'package:flutter_inappwebview/flutter_inappwebview.dart';

Future<void> _firebaseMessagingBackgroundHandler(RemoteMessage message) async {
  print("백그라운드 메시지 처리.. ${message.notification!.body!}");
}

void initializeNotification() async {
  FirebaseMessaging.onBackgroundMessage(_firebaseMessagingBackgroundHandler);

  final flutterLocalNotificationsPlugin = FlutterLocalNotificationsPlugin();
  await flutterLocalNotificationsPlugin
      .resolvePlatformSpecificImplementation<
          AndroidFlutterLocalNotificationsPlugin>()
      ?.createNotificationChannel(const AndroidNotificationChannel(
          'high_importance_channel', 'high_importance_notification',
          importance: Importance.max));

  await flutterLocalNotificationsPlugin.initialize(const InitializationSettings(
    android: AndroidInitializationSettings("@mipmap/ic_launcher"),
  ));

  await FirebaseMessaging.instance.setForegroundNotificationPresentationOptions(
    alert: true,
    badge: true,
    sound: true,
  );
}

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp();
  initializeNotification();
  runApp(
    const MaterialApp(
      home: WebViewApp(),
    ),
  );
}

class WebViewApp extends StatefulWidget {
  const WebViewApp({Key? key}) : super(key: key);

  @override
  State<WebViewApp> createState() => _WebViewAppState();
}

class _WebViewAppState extends State<WebViewApp> {
  InAppWebViewController? _webViewController;

  final Completer<InAppWebViewController> _completerController =
      Completer<InAppWebViewController>();
  var messageString = "";
  var deviceToken = "";
  Uri webUrl = Uri.parse('http://http://13.125.249.51/');

  final GlobalKey webViewKey = GlobalKey();

  void getMyDeviceToken() async {
    final token = await FirebaseMessaging.instance.getToken();
    setState(() {
      deviceToken = token!; // 가져온 이미지를 _image에 저장
    });
    print("내 디바이스 토큰: $deviceToken");
  }

  @override
  void initState() {
    // if (Platform.isAndroid) {
    //   WebView.platform = SurfaceAndroidWebView();
    // }
    getMyDeviceToken();
    FirebaseMessaging.onMessage.listen((RemoteMessage message) async {
      RemoteNotification? notification = message.notification;

      if (notification != null) {
        FlutterLocalNotificationsPlugin().show(
          notification.hashCode,
          notification.title,
          notification.body,
          const NotificationDetails(
            android: AndroidNotificationDetails(
              'high_importance_channel',
              'high_importance_notification',
              importance: Importance.max,
            ),
          ),
        );
        setState(() {
          messageString = message.notification!.body!;
          print("Foreground 메시지 수신: $messageString");
        });
      }
    });
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
        onWillPop: () => _goBack(context),
        child: Scaffold(
          body: InAppWebView(
            key: webViewKey,
            onWebViewCreated: (InAppWebViewController webViewController) {
              _completerController.future
                  .then((value) => _webViewController = value);
              _completerController.complete(webViewController);
              webViewController.addJavaScriptHandler(
                  //  디바이스 토큰을 웹뷰에 전달
                  handlerName: 'getDeviceToken',
                  callback: (args) {
                    return deviceToken;
                  });
            },
            initialUrlRequest: URLRequest(url: webUrl),
            initialOptions: InAppWebViewGroupOptions(
              crossPlatform: InAppWebViewOptions(
                javaScriptCanOpenWindowsAutomatically: true, // 자바스크립트 새 창 실행 허용
                javaScriptEnabled: true, // 자바스크립트 실행 허용
                useOnDownloadStart: true,
                useOnLoadResource: true,
                allowFileAccessFromFileURLs: true,
                verticalScrollBarEnabled: true,
              ),
              android: AndroidInAppWebViewOptions(
                  allowContentAccess: true,
                  builtInZoomControls: true,
                  thirdPartyCookiesEnabled: true,
                  allowFileAccess: true,
                  supportMultipleWindows: true // 새 창 실행 허용
                  ),
            ),
            onConsoleMessage: (InAppWebViewController controller,
                ConsoleMessage consoleMessage) {
              // 웹에서 찍은 콘솔 볼 수 있음
              log("WEB CONSOLE: ${consoleMessage.message}");
            },
          ),
        ));
  }

  Future<bool> _goBack(BuildContext context) async {
    if (_webViewController == null) {
      return true;
    }
    if (await _webViewController!.canGoBack()) {
      _webViewController!.goBack();
      return Future.value(false);
    } else {
      return Future.value(true);
    }
  }
}

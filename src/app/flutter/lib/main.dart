import 'dart:async';
import 'dart:io'; // Add this import.
import 'package:flutter/material.dart';
import 'package:webview_flutter/webview_flutter.dart';

void main() {
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
  WebViewController? _webViewController;

  final Completer<WebViewController> _completerController =
      Completer<WebViewController>();

  // Add from here ...
  @override
  void initState() {
    if (Platform.isAndroid) {
      WebView.platform = SurfaceAndroidWebView();
    }
    super.initState();
  }
  // ... to here.

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: () => _goBack(context),
      child: Scaffold(
        body: WebView(
          onWebViewCreated: (WebViewController webViewController) {
            _completerController.future
                .then((value) => _webViewController = value);
            _completerController.complete(webViewController);
          },
          initialUrl: 'http://13.125.249.51',
          javascriptMode: JavascriptMode.unrestricted,
        ),
      ),
    );
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

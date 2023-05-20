export const isMobileWidth = (windowWidth: number) => windowWidth <= 600;

export const getDeviceToken = async () => {
  const deviceToken = window.flutter_inappwebview?.callHandler(
    'getDeviceToken',
    '',
  );

  return deviceToken;
};

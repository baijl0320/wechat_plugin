import 'dart:async';

import 'package:flutter/services.dart';

class WechatPlugin {
  static final String TAG = "WechatPlugin";

  static const MethodChannel _channel =
      const MethodChannel('wechat_plugin');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}

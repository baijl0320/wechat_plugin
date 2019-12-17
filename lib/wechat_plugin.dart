import 'dart:async';

import 'package:flutter/services.dart';

/**
 * Provide some APIs for wechat, include login, auth, share and pay.
 * */
class WechatPlugin {
  // LOG TAG
  static final String TAG = "WechatPlugin";

  // METHOD CONSTANTS
  static final String METHOD_GET_PLATFORM_VERSION = "getPlatformVersion";
  static final String METHOD_REGISTER_WECHAT = "registerWechat";
  static final String METHOD_LOGIN_WECHAT = "loginWechat";
  static final String METHOD_LOGIN_WECHAT_BY_QRCODE = "loginWechatByQRCode";
  static final String METHOD_SHARE_TEXT_TO_WECHAT = "shareText2Wechat";
  static final String METHOD_SHARE_WEBPAGE_TO_WECHAT = "shareWebpage2Wechat";
  static final String METHOD_SHARE_IMAGE_TO_WECHAT = "shareImage2Wechat";
  static final String METHOD_SHARE_MUSIC_TO_WECHAT = "shareMusic2Wechat";
  static final String METHOD_SHARE_VIDEO_TO_WECHAT = "shareVideo2Wechat";
  static final String METHOD_REQUEST_WECHAT_PAY = "requestWechatPay";

  static const MethodChannel _channel = const MethodChannel('wechat_plugin');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod(METHOD_GET_PLATFORM_VERSION);
    return version;
  }

  /**
   * Register Wechat for App
   * appid: your appid support to wechat
   * isEnableMTA:
   * */
  static Future<bool> registerWechat({
    String appid,
    bool isEnableMTA: false,
  }) async {
    final Map<String, dynamic> params = <String, dynamic>{
      'appid': appid,
      'isEnableMTA': isEnableMTA,
    };

    final bool isSucceed = await _channel.invokeMethod(METHOD_REGISTER_WECHAT, params);
    return isSucceed;
  }

  /**
   * Login Wechat
   * */
  static Future<int> loginWechat({
    String scope: "snsapi_userinfo",
    String state: "login",
  }) async {
    final Map<String, dynamic> params = <String, dynamic>{
      'scope': scope,
      'state': state,
    };

    final int res = await _channel.invokeMethod(METHOD_LOGIN_WECHAT, params);
    return res;
  }

  /**
   * Login Wechat by QRCode
   * */
  static Future<int> loginWechatByQRCode({
    String appid,
    String url,
    String scope: "snsapi_userinfo",
    String state: "login",
  }) async {
    final Map<String, dynamic> params = <String, dynamic>{
      'appid': appid,
      'url': url,
      'scope': scope,
      'state': state,
    };

    final int res = await _channel.invokeMethod(METHOD_LOGIN_WECHAT_BY_QRCODE, params);
    return res;
  }

  /**
   * Share text to wechat
   * text: content to be shared
   * scene: where to share
   * */
  static Future<int> shareText2Wechat({
    String text: "",
    int scene: 0,
  }) async {
    final Map<String, dynamic> params = <String, dynamic>{
      'text': text,
      'scene': scene,
    };

    final int res = await _channel.invokeMethod(METHOD_SHARE_TEXT_TO_WECHAT, params);
    return res;
  }

  /**
   * Share webpage to wechat
   * webpageUrl: webpage url to be shared
   * title: webpage title
   * description: webpage description
   * imageUrl: webpage thumbnail url
   * scene: where to share
   * */
  static Future<int> shareWebpage2Wechat({
    String webpageUrl: "",
    String title: "",
    String description: "",
    String imageUrl: "",
    int scene: 0,
  }) async {
    final Map<String, dynamic> params = <String, dynamic>{
      'webpageUrl': webpageUrl,
      'title': title,
      'description': description,
      'imageUrl': imageUrl,
      'scene': scene,
    };

    final int res = await _channel.invokeMethod(METHOD_SHARE_WEBPAGE_TO_WECHAT, params);
    return res;
  }

  /**
   * Share image to wechat
   * webpageUrl: webpage url to be shared
   * title: webpage title
   * description: webpage description
   * imageUrl: webpage thumbnail url
   * scene: where to share
   * */
  static Future<int> shareImage2Wechat({
    String webpageUrl: "",
    String title: "",
    String description: "",
    String imageUrl: "",
    int scene: 0,
  }) async {
    final Map<String, dynamic> params = <String, dynamic>{
      'webpageUrl': webpageUrl,
      'title': title,
      'description': description,
      'imageUrl': imageUrl,
      'scene': scene,
    };

    final int res = await _channel.invokeMethod(METHOD_SHARE_IMAGE_TO_WECHAT, params);
    return res;
  }

  /**
   * Share music to wechat
   * */
  static Future<int> shareMusic2Wechat({
    String imgUrl: "",
    String musicUrl: "",
    String title: "",
    String description: "",
    String musicDataUrl: "",
    String musicLowBandUrl: "",
    String musicLowBandDataUrl: "",
    int scene: 0,
  }) async {
    final Map<String, dynamic> params = <String, dynamic>{
      'imgUrl': imgUrl,
      'musicUrl': musicUrl,
      'title': title,
      'description': description,
      'musicDataUrl': musicDataUrl,
      'musicLowBandUrl': musicLowBandUrl,
      'musicLowBandDataUrl': musicLowBandDataUrl,
      'scene': scene,
    };

    final int res = await _channel.invokeMethod(METHOD_SHARE_MUSIC_TO_WECHAT, params);
    return res;
  }

  /**
   * Share video to wechat
   * */
  static Future<int> shareVideo2Wechat({
    String imgUrl: "",
    String videoUrl: "",
    String title: "",
    String description: "",
    String videoLowBandUrl: "",
    int scene: 0,
  }) async {
    final Map<String, dynamic> params = <String, dynamic>{
      'imgUrl': imgUrl,
      'videoUrl': videoUrl,
      'title': title,
      'description': description,
      'videoLowBandUrl': videoLowBandUrl,
      'scene': scene,
    };

    final int res = await _channel.invokeMethod(METHOD_SHARE_VIDEO_TO_WECHAT, params);
    return res;
  }

  /**
   * Request wechat pay
   * */
  static Future<int> requestWechatPay({
    String partnerId: "",
    String prepayId: "",
    String nonceStr: "",
    String timeStamp: "",
    String sign: "",
    String package: "",
    String appid: "",
  }) async {
    final Map<String, dynamic> params = <String, dynamic>{
      'partnerId': partnerId,
      'prepayId': prepayId,
      'nonceStr': nonceStr,
      'timeStamp': timeStamp,
      'sign': sign,
      'package': package,
      'appid': appid,
    };

    final int res = await _channel.invokeMethod(METHOD_REQUEST_WECHAT_PAY, params);
    return res;
  }
}

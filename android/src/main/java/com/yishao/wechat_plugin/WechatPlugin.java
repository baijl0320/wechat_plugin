package com.yishao.wechat_plugin;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * WechatPlugin
 */
public class WechatPlugin implements MethodCallHandler {
    // LOG TAG
    private static final String TAG = "WechatPlugin";

    // METHOD CONSTANTS
    private static final String METHOD_GET_PLATFORM_VERSION = "getPlatformVersion";
    private static final String METHOD_REGISTER_WECHAT = "registerWechat";
    private static final String METHOD_LOGIN_WECHAT = "loginWechat";
    private static final String METHOD_LOGIN_WECHAT_BY_QRCODE = "loginWechatByQRCode";
    private static final String METHOD_SHARE_TEXT_TO_WECHAT = "shareText2Wechat";
    private static final String METHOD_SHARE_WEBPAGE_TO_WECHAT = "shareWebpage2Wechat";
    private static final String METHOD_SHARE_IMAGE_TO_WECHAT = "shareImage2Wechat";
    private static final String METHOD_SHARE_MUSIC_TO_WECHAT = "shareMusic2Wechat";
    private static final String METHOD_SHARE_VIDEO_TO_WECHAT = "shareVideo2Wechat";
    private static final String METHOD_REQUEST_WECHAT_PAY = "requestWechatPay";

    private static Registrar sRegistrar;
    private static Result sResult;

    private Context context;
    private Activity activity;

    private WechatPlugin(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    /**
     * Plugin registration.
     */
    public static void registerWith(Registrar registrar) {
        sRegistrar = registrar;
        Activity activity = registrar.activity();
        Context context = registrar.context();

        final MethodChannel channel = new MethodChannel(registrar.messenger(), "wechat_plugin");
        channel.setMethodCallHandler(new WechatPlugin(context, activity));

        IntentFilter filter = new IntentFilter();
        filter.addAction("sendResp");
        context.registerReceiver(createReceiver(), filter);
    }

    @Override
    public void onMethodCall(MethodCall call, Result result) {
        Log.d(TAG, "onMethodCall call.method = " + call.method);

        sResult = result;

        switch (call.method) {
            case METHOD_GET_PLATFORM_VERSION: {
                getPlatformVersion(call, result);
            }
            break;
            case METHOD_REGISTER_WECHAT: {
                registerWechat(call, result);
            }
            break;
            case METHOD_LOGIN_WECHAT: {
                loginWechat(call, result);
            }
            break;
            case METHOD_LOGIN_WECHAT_BY_QRCODE: {
                loginWechatByQRCode(call, result);
            }
            break;
            case METHOD_SHARE_TEXT_TO_WECHAT: {
                shareText2Wechat(call, result);
            }
            break;
            case METHOD_SHARE_WEBPAGE_TO_WECHAT: {
                shareWebpage2Wechat(call, result);
            }
            break;
            case METHOD_SHARE_IMAGE_TO_WECHAT: {
                shareImage2Wechat(call, result);
            }
            break;
            case METHOD_SHARE_MUSIC_TO_WECHAT: {
                shareMusic2Wechat(call, result);
            }
            break;
            case METHOD_SHARE_VIDEO_TO_WECHAT: {
                shareVideo2Wechat(call, result);
            }
            break;
            case METHOD_REQUEST_WECHAT_PAY: {
                requestWechatPay(call, result);
            }
            break;
            default: {
                result.notImplemented();
            }

        }
    }

    private void getPlatformVersion(MethodCall call, Result result) {
        result.success("Android " + android.os.Build.VERSION.RELEASE);
    }

    private void registerWechat(MethodCall call, Result result) {

    }

    private void loginWechat(MethodCall call, Result result) {

    }

    private void loginWechatByQRCode(MethodCall call, Result result) {

    }

    private void shareText2Wechat(MethodCall call, Result result) {

    }

    private void shareWebpage2Wechat(MethodCall call, Result result) {

    }

    private void shareImage2Wechat(MethodCall call, Result result) {

    }

    private void shareMusic2Wechat(MethodCall call, Result result) {

    }

    private void shareVideo2Wechat(MethodCall call, Result result) {

    }

    private void requestWechatPay(MethodCall call, Result result) {

    }

    private static BroadcastReceiver createReceiver() {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.hasExtra("type")) {
                    String type = intent.getStringExtra("type");
                    Log.d(TAG, "onReceive type " + type);

                    if (type.equals("SendAuthResp")) {

                    } else if (type.equals("ShareResp")) {

                    } else if (type.equals("PayResp")) {

                    }
                }
            }
        };
    }
}

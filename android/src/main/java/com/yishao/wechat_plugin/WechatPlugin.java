package com.yishao.wechat_plugin;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.util.Log;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yishao.wechat_plugin.util.BitmapUtil;

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

    private IWXAPI iwxapi = null;

    private Context context;
    private Activity activity;

    private final int WX_THUMBNAIL_SIZE_LIMIT = 31;

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
        final String appid = call.argument("appid");
        iwxapi = WXAPIFactory.createWXAPI(context, appid, true);
        if (iwxapi == null) {
            result.success(false);
        } else {
            iwxapi.registerApp(appid);
            result.success(true);
        }
    }

    private void loginWechat(MethodCall call, Result result) {
        final String scope = call.argument("scope");
        final String state = call.argument("state");

        Log.d(TAG, "loginWechat scope = "+scope+" state = "+state);

        if (iwxapi == null) {
            Log.e(TAG, "Error: iwxapi is null");
            return;
        }

        final SendAuth.Req req = new SendAuth.Req();
        req.scope = scope;
        req.state = state;
        iwxapi.sendReq(req);
    }

    private void loginWechatByQRCode(MethodCall call, Result result) {
        final String appid = call.argument("appid");
        final String url = call.argument("url");
        final String scope = call.argument("scope");
        final String state = call.argument("state");

        Log.d(TAG, "loginWechatByQRCode appid = " + appid
                        +" url = "+url
                        +" scope = "+scope
                        +" state = "+state
                        );

        if (iwxapi == null) {
            Log.e(TAG, "Error: iwxapi is null");
            return;
        }

        // TODO 待补充完整
    }

    private void shareText2Wechat(MethodCall call, Result result) {
        final String text = call.argument("text");
        final int scene = call.argument("scene");

        Log.d(TAG, "shareText2Wechat text = " + text
                +" scene = "+scene
        );

        if (iwxapi == null) {
            Log.e(TAG, "Error: iwxapi is null");
            return;
        }

        // TODO 待补充完整
    }

    private void shareWebpage2Wechat(MethodCall call, Result result) {
        final String webpageUrl = call.argument("webpageUrl");
        final String title = call.argument("title");
        final String description = call.argument("description");
        final String imageUrl = call.argument("imageUrl");
        final int scene = call.argument("scene");

        Log.d(TAG, "shareWebpage2Wechat webpageUrl = " + webpageUrl
                +" title = "+title
                +" description = "+description
                +" imageUrl = "+imageUrl
                +" scene = "+scene
        );

        if (iwxapi == null) {
            Log.e(TAG, "Error: iwxapi is null");
            return;
        }

        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = webpageUrl;

        WXMediaMessage wxMsg = new WXMediaMessage();
        wxMsg.mediaObject = webpageObject;
        wxMsg.title = title;
        wxMsg.description = description;
        wxMsg.thumbData = BitmapUtil.compressBitmap(BitmapUtil.getLocalOrNetBitmap(imageUrl), WX_THUMBNAIL_SIZE_LIMIT);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = wxMsg;
        req.scene = scene;
        iwxapi.sendReq(req);
    }

    private void shareImage2Wechat(MethodCall call, Result result) {
        final String imagePath = call.argument("imagePath");
        final int scene = call.argument("scene");

        Log.d(TAG, "shareImage2Wechat imagePath = " + imagePath
                +" scene = "+scene
        );

        if (iwxapi == null) {
            Log.e(TAG, "Error: iwxapi is null");
            return;
        }

        WXMediaMessage wxMsg = new WXMediaMessage();
        Bitmap bitmap = BitmapUtil.getLocalOrNetBitmap(imagePath);
        if (bitmap != null) {
            WXImageObject wxImageObject = new WXImageObject(bitmap);
            wxMsg.mediaObject = wxImageObject;
            wxMsg.thumbData = BitmapUtil.compressBitmap(bitmap, WX_THUMBNAIL_SIZE_LIMIT);

            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = String.valueOf(System.currentTimeMillis());
            req.message = wxMsg;
            req.scene = scene;
            iwxapi.sendReq(req);
        } else {
            Log.e(TAG, "Error: shareImage2Wechat bitmap is null");
            return;
        }
    }

    private void shareMusic2Wechat(MethodCall call, Result result) {
        final String imgUrl = call.argument("imgUrl");
        final String musicUrl = call.argument("musicUrl");
        final String title = call.argument("title");
        final String description = call.argument("description");
        final String musicDataUrl = call.argument("musicDataUrl");
        final String musicLowBandUrl = call.argument("musicLowBandUrl");
        final String musicLowBandDataUrl = call.argument("musicLowBandDataUrl");
        final int scene = call.argument("scene");

        Log.d(TAG, "shareMusic2Wechat imgUrl = " + imgUrl
                +" musicUrl = "+musicUrl
                +" title = "+title
                +" description = "+description
                +" musicDataUrl = "+musicDataUrl
                +" musicLowBandUrl = "+musicLowBandUrl
                +" musicLowBandDataUrl = "+musicLowBandDataUrl
                +" scene = "+scene
        );

        if (iwxapi == null) {
            Log.e(TAG, "Error: iwxapi is null");
            return;
        }
    }

    private void shareVideo2Wechat(MethodCall call, Result result) {
        final String imgUrl = call.argument("imgUrl");
        final String videoUrl = call.argument("videoUrl");
        final String title = call.argument("title");
        final String description = call.argument("description");
        final String videoLowBandUrl = call.argument("videoLowBandUrl");
        final int scene = call.argument("scene");

        Log.d(TAG, "shareVideo2Wechat imgUrl = " + imgUrl
                +" videoUrl = "+videoUrl
                +" title = "+title
                +" description = "+description
                +" videoLowBandUrl = "+videoLowBandUrl
                +" scene = "+scene
        );

        if (iwxapi == null) {
            Log.e(TAG, "Error: iwxapi is null");
            return;
        }
    }

    private void requestWechatPay(MethodCall call, Result result) {
        final String partnerId = call.argument("partnerId");
        final String prepayId = call.argument("prepayId");
        final String nonceStr = call.argument("nonceStr");
        final String timeStamp = call.argument("timeStamp");
        final String sign = call.argument("sign");
        final String packages = call.argument("packages");
        final String appid = call.argument("appid");

        Log.d(TAG, "requestWechatPay partnerId = " + partnerId
                +" prepayId = "+prepayId
                +" nonceStr = "+nonceStr
                +" timeStamp = "+timeStamp
                +" sign = "+sign
                +" packages = "+packages
                +" appid = "+appid
        );

        if (iwxapi == null) {
            Log.e(TAG, "Error: iwxapi is null");
            return;
        }
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

package com.yishao.wechat_plugin_example.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    // LOG TAG
    private static final String TAG = "WXEntryActivity";

    //
    private String wxAppId = "wx913f02182754ad93";
    private IWXAPI iwxapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        iwxapi = WXAPIFactory.createWXAPI(this, wxAppId);
        if (iwxapi != null) {
            iwxapi.handleIntent(getIntent(), this);
        }

        finish();
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Log.d(TAG, "onReq");
    }

    @Override
    public void onResp(BaseResp baseResp) {
        Log.d(TAG, "onResp");

        Intent intent = new Intent();
        if (baseResp instanceof SendAuth.Resp) {
            SendAuth.Resp resp = (SendAuth.Resp) (baseResp);
            intent.setAction("sendResp");
            intent.putExtra("resultCode", Integer.toString(resp.errCode));
            intent.putExtra("type", "SendAuthResp");
            intent.putExtra("code", resp.code);
            sendBroadcast(intent);
        } else {
            intent.setAction("sendResp");
            intent.putExtra("resultCode", Integer.toString(baseResp.errCode));
            intent.putExtra("type", "ShareResp");
            sendBroadcast(intent);
        }
    }
}

package com.example.tarena.bmobdemo.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.push.PushConstants;

/**
 * Created by tarena on 2017/6/30.
 */

public class MyREceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
        if(PushConstants.ACTION_MESSAGE.equals(action)){
            //收到了服务器推送过来的内容
            String message=intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING);
            Log.d("bmob", "客户端收到推送内容："+intent.getStringExtra("msg"));
            //{"tag":"newpost","content":"xxx发布了新帖"}
            try {
                JSONObject jsonObject=new JSONObject(message);
                if (jsonObject.equals("tag")){
                    String tag=jsonObject.getString("tag");
                    if ("newpost".equals(tag)){
                        //有人发布了新帖子
                        //让ShowActivity的右上角出现提示红点
                        Intent intent1=new Intent("COM.TARENA.JIN");
                        context.sendBroadcast(intent1);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}

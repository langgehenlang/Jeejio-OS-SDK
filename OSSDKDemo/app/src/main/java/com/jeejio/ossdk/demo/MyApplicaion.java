package com.jeejio.ossdk.demo;

import android.app.Application;
import android.util.Log;

import com.jeejio.cloudservice.sdk.HMessage;
import com.jeejio.cloudservice.sdk.ICloudMessageListener;
import com.jeejio.cloudservice.sdk.JeejioCloudService;

import java.lang.ref.WeakReference;

import jeejio.input.InputEventService;
import jeejio.input.InputEventService.OnInputEventListener;

public class MyApplicaion extends Application {
    final static String TAG = "MyApplicaion";

    CloudMessageListener mCloudMessageListener;
    WeakReference<OnInputEventListener> mEventListener;
    InputEventListener mInputEventListener;
    @Override
    public void onCreate() {
        super.onCreate();
        registerOSSDK();
        Log.e(TAG, JeejioCloudService.SDK_VERSION);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterOSSDK();
    }


    private void registerOSSDK(){
        if (mCloudMessageListener == null){
            mCloudMessageListener = new CloudMessageListener();
            //注册云服务
            JeejioCloudService.registerClient(this, mCloudMessageListener);
        }

        //PS:此服务需要的so库，已集成在jeejio设备中。如不需要此功能可屏蔽
        if (mEventListener == null){
            mInputEventListener = new InputEventListener();
            mEventListener = new WeakReference<OnInputEventListener>(mInputEventListener);
            //注册按键监听服务
            InputEventService.getInputEventService(this).registerEventListener(mEventListener);
        }

    }

    private void unregisterOSSDK(){
        //解除注册云服务
        if (mCloudMessageListener != null){
            JeejioCloudService.unregisterClient();
            mCloudMessageListener = null;
        }
        //解除注册按键监听服务
        if (mEventListener != null){
            InputEventService.getInputEventService(this).unRegisterEventListener(mEventListener);
            mEventListener = null;
        }
    }

    public static class CloudMessageListener implements ICloudMessageListener {
        @Override
        public HMessage onCloudMessage(HMessage message) {
            // Get parameter from cloud.
            String body = message.getBody();
            // Do something.
            Log.i(TAG, "Parameter from cloud: " + body);
            // Build response to cloud.
            return HMessage.createBodyMessage(body);
        }
    }

    public static class InputEventListener implements OnInputEventListener {

        @Override
        public void onKeyUp(int keyCode) {
            // Do something.
            Log.i(TAG, "key up Code: " + keyCode);
        }

        @Override
        public void onKeyDown(int keyCode) {
            // Do something.
            Log.i(TAG, "key Down Code: " + keyCode);
        }
    }
}

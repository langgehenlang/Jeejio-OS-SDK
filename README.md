# Jeejio OS SDk文档
---
###  什么是 Jeejio OS
Jeejio OS 是基于 Android 的 IoT(物联网) 设备操作系统，具有安全、易用等特点。  

除了提供 Android 原生的 API 外，还针对不同的物端设备提供了硬件控制的接口。如果您具有 Android APP 的开发经验，可以轻松上手开发基于 Jeejio OS 的 APP，并使其运行在任何搭载了 Jeejio OS 的物联网设备上。  

###  什么是 Jeejio OS SDK
是一个使您可以轻松开发物端 APP 的开发者工具包，提供了与控制端 H5 交互的功能，只需轻松3步即可使您的 APP 接入 Jeejio 云。  

###  开始使用 Jeejio OS SDK
+ SDK下载  
 前往 [Github](https://github.com/jeejio/Jeejio-OS-SDK/releases/tag/1.0.0) 下载。  
+ Android SDK导入  
 将下载下来的 `CloudserviceLib.jar` 放入 AndroidStudio 工程的 `libs` 目录下，并在 `app/build.gradle` 文件中加入 `implementation files('libs/CloudServiceLib.jar')`，即可完成导入。  

###  Jeejio OS SDK 主要 API 介绍
+ ICloudMessageListener：云服务事件监听器，可以通过该监听器收到来自云端的请求。
+ JeejioCloudService：云服务代理对象。您的APP可以通过此对象与 Jeejio 云服务建立链接。
+ OnInputEventListener：物端按键事件监听器，可以通过该监听器收到来物端按键的触发。

###  演示1
+ 将您的APP注册到云服务中  
```java
JeejioCloudService.registerClient(mContext, mCloudMessageListener);
```
+ 接收来自云端的请求  
```java
public class CloudMessageListener implements ICloudMessageListener {
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
```
+ 从云服务中解除注册  
```java
JeejioCloudService.unregisterClient();
```

### 演示2

+ 在您的APP注册物端按键监听

```java
InputEventService.getInputEventService(mContext).registerEventListener(mEventListener);
```

- 接收来自物端按键的响应

```java
public class InputEventListener implements OnInputEventListener {

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
```

- 解除注册

```java
InputEventService.getInputEventService(mContext).unRegisterEventListener(mEventListener);
```


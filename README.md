## 概述

### Jeejio OS

Jeejio OS 是一套基于 Android 的 IoT(物联网) 设备的操作系统，具有安全、易用等特点。

除了提供 Android 原生的 API 外，还针对不同的物端设备提供了硬件控制的接口。如果您具有 Android APP 的开发经验，可以轻松上手开发基于 Jeejio OS 的 APP，并使其运行在任何搭载了 Jeejio OS 的物联网设备上。

### Jeejio OS SDK

该SDK是一个使您可以轻松开发物端 APP 的开发者工具包，提供了与控制端 H5 交互和监听物端设备按键的功能，只需轻松几步即可使您的 APP 接入 Jeejio 云。



## 集成指南

### 主要 API 介绍

- ICloudMessageListener：云服务事件监听器，可以通过该监听器收到来自云端的请求。
- JeejioCloudService：云服务代理对象。您的APP可以通过此对象与 Jeejio 云服务建立链接。
- InputEventService：按键服务。您可以通过该服务来监听物端设备按键事件。
- OnInputEventListener：物端按键事件监听器，可以通过该监听器收到来物端按键的触发状态。

### 接入说明

- SDK下载
   前往 [Github](https://github.com/jeejio/Jeejio-OS-SDK/tags) 下载最新版本。

- Android SDK导入

  将下载下来的 `CloudserviceLib.jar` 放入 AndroidStudio 工程的 `libs` 目录下，并在 `app/build.gradle` 文件中加入 `implementation files('libs/CloudServiceLib.jar')`，即可完成导入。

- 混淆规则

  ```properties
  -keep class com.jeejio.** {*;}
  -keep class jeejio.** {*;}
  ```

### 示例演示

**1、与控制端 H5 交互**

- 将您的APP注册到云服务中

```java
JeejioCloudService.registerClient(mContext, mCloudMessageListener);
```

- 接收来自云端的请求

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

- 从云服务中解除注册

```java
JeejioCloudService.unregisterClient();
```

**2、监听物端设备按键**

​	此服务需要的so库，已集成在jeejio设备中，所以运行在非jeejio设备上会报so库问题。如不需要此功能，可不注册此服务，则无影响。

- 在您的APP注册物端按键监听

```java
mEventListener = new WeakReference<OnInputEventListener>(new InputEventListener());
InputEventService.getInputEventService(mContext).registerEventListener(mEventListener);
```

- 接收来自物端按键的响应

```java
public class InputEventListener implements OnInputEventListener {

        @Overridepublic class InputEventListener implements OnInputEventListener {

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

**3、获取基础信息**

```java
//获取sdk版本信息
Log.i(TAG, JeejioCloudService.SDK_VERSION);

//获取deviceId
String deviceid = JeejioCloudService.getDeviceId(this);
Log.i(TAG, "deviceId = " + deviceid);

//获取userId
String userId = JeejioCloudService.getUserId(this);
Log.i(TAG, "userId = " + userId);
```



## 更新日志

| 版本号 | 发布时间   | 更新内容                                   |
| ------ | ---------- | ------------------------------------------ |
| 1.2.1  | 2020.05.27 | ●增加获取userId、deviceId等基础信息接口    |
| 1.1.1  | 2020.05.15 | ●增加sdk版本信息接口                       |
| 1.0.1  | 2020.03.24 | ●增加设备按键监听<br>●修改参数和返回值类型 |
| 1.0.0  | 2019.09.27 | ●提供注册与云服务交互                      |



## 常见问题

1. 返回 "vpn_machine_call_error"

   > 查看onCloudMessage中返回的body参数，是否为JsonObject的字符串。如正常，可能是其他问题所致。

2. 提示 "Size should be less than 204800B"

   > 每次传递和返回的参数body字符串，不能超过200KB。

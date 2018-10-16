# Android开户SDK

## 1. 开发环境集成

[点击下载SDK](/document/#/download)

### 1.1 添加项目依赖

```text
implementation 'com.eastmoney.avemlivesdkandroid:avemlivesdkandroid:1.4.3.35'
implementation 'com.eastmoney.emlivesdkandroid:emlivesdkandroid:1.5.46.0'
implementation 'com.eastmoney.p2pchat:p2pchat:0.2.31'
implementation 'com.eastmoney.medialib:anychat-medialib:0.2.17'
implementation 'com.eastmoney.avfastimageprocessing:avfastimageprocessing:0.1.20'
```
依赖包含了native动态库，目前仅支持"armeabi"的CPU架构。

### 1.2 添加权限

```xml
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.CAMERA"/>
<uses-permission android:name="android.permission.RECORD_AUDIO"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
<uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS"/>
```

### 1.3 混淆

```text
-keep class com.medialivelib.** {*;}
-keep class project.android.imageprocessing.output.FastImageYUVOutput { *; }
-keep class eastmoney.p2pchat.** {*;}
-keep class com.dropcam.** {*;}
-keep class org.webrtc.** {*;}
```

### 1.4 SDK启动和退出
使用SDK前必须确保SDK初始化成功。

- 本地初始化

```java
KaihuSDK.init(okHttpClient, debug);
```
传入项目已有的OkHttpClient可以复用，否则传入null。打开debug标志位可以打印SDK调试日志。

- 连接服务器

开户时需保持与服务器的连接，已发消息和接受服务器指令。
```java
KaihuSDK.start(httpServerAddress, webSocketServerAddress);
```
参数分别传入HTTP服务器地址和WebSocket服务器地址。

- 退出

SDK使用完毕，需断开服务器连接。
```java
KaihuSDK.stop();
```

- 添加/移除服务器监听

```java
// 添加监听
MessageManager messageManager = new MessageManager(new SimpleMessageReceiver() {
    @Override
    public void onConnect() {
        // TODO
    }
    @Override
    public void onDisconnect() {
        // TODO
    }
});

// 移除监听
messageMananger.unRegister();
```

## 2. 业务集成

### 2.1 单录单向

无需客服实时参与。客户端打开摄像头，获取视频截图并上传，录制一段时间的视频后，上传视频到服务器。

- 录制

SDK提供视频录制和截图功能。
```java
// 初始化推流器
mRecorder = new AVEMLivePusher(context);
mRecordParam = new AVEMLivePushConfig();
mRecorder.setConfig(pushConfig);
mRecorder.setPushListener(new IAVEMLivePushListener() { // 推流事件
    @Override
    public void onPushEvent(int event, Bundle bundle) {
        switch (event) {
            case AVEMLiveConstants.PUSH_EVT_PUSH_BEGIN:
                ...
        }
    }

    @Override
    public void onNetStatus(Bundle bundle) { // 推流网络变化
    }

    @Override
    public void onPushStreamBitmap(Bitmap bitmap, int i, int i1) { // 截图回调
    }
});

// 开始推流
mRecorder.startPusher(path);

// 结束推流
mRecorder.stopPusher();

// 截图
mRecorder.takePhoto(0, 0, new IAVEMLiveTakePictureListener() {
    @Override
    public void onPictureTaked(Bitmap bitmap, int i, int i1) {
        ...
    }
});


// UI前后台切换
// 切后台
videoView.onStop();
mRecorder.stopCameraPreview(videoView);
// 切前台
videoView.onResume();
mRecorder.startCameraPreview(videoView);

// 销毁推流器
mRecorder.setPushListener(null);
mRecorder.destroy();
videoView.onDestroy();
```

- 视频预览

已录制的视频，可以本地预览。
```java
// 初始化播放器
mPlayer = new AVEMLivePlayer(Utils.getContext());
mPlayer.setPlayerView(videoView);
AVEMLivePlayConfig playConfig = new AVEMLivePlayConfig();
mPlayer.setConfig(playConfig);
mPlayer.setPlayListener(new IAVEMLivePlayListener() {
    @Override
    public void onPlayEvent(int i, Bundle bundle) { // 播放事件
        switch (i) {
            case AVEMLiveConstants.PLAY_EVT_PLAY_BEGIN:
            ...
        }
    }

    @Override
    public void onNetStatus(Bundle bundle) { // 播放网络事件
    }
});
mPlayer.startPlay(path, AVEMLivePlayer.PLAY_TYPE_LOCAL_VIDEO);

// UI前后台切换
// 切后台
mPlayer.pause();
videoView.onPause();
// 切前台
mPlayer.resume();
videoView.onResume();

// 销毁播放器
mPlayer.stopPlay(true);
mPlayer.setPlayListener(null);
mPlayer.setPlayerView(null);
mPlayer.destory();
videoView.onDestroy();
```

- 上传截图图片和视频

截图获取图片后上传到后台。视频录制完成后，也需上传到后台。
```java
// 上传图片
UploadManager.uploadImage(imagePath, new UploadListener() {
    @Override
    public void onUploadSuccess(String path, String url) {
        // TODO
    }
    @Override
    public void onUploadFail(String path) {
        // TODO
    }
});

// 上传视频，支持失败自动重试
UploadManager.uploadVideo(videoPath, new UploadListener() {
    @Override
    public void onUploadSuccess(String path, String url) {
        // TODO
    }
    @Override
    public void onUploadFail(String path) {
        // TODO
    }
}, 3);
```

### 2.2 单录双向

需要客服实时参与。客户端排队等待客服接通，接受并执行客服指令。接收签名指令签名，上传签名图片。在与客服通话过程中，持续上传录制的视频分片。

- 录制

单录双向录制的视频除基本操作外，还需进房间。
```java
// 初始化，指定推流类型
mRecorder.setOpenAccountStage(AVEMLiveConstants.EM_OPEN_ACCOUNT_II);
mRecorder.startWithP2p(true);

// 收到推流开始事件，进房间
mRecorder.setRoomServer(roomServer);
mRecorder.enterRoom(roomId, true);

// 销毁推流器，退出房间
mRecorder.leaveRoom();
```

- 收发消息

开户过程中与客服交互通过消息驱动。
```java
MessageManager messageManager = new MessageManager(new SimpleMessageReceiver() {
    @Override
    public void onConnect() {
        // TODO 服务器连接
    }
    @Override
    public void onDisconnect() {
        // TODO 服务器断开
    }
    @Override
    public void onReceiveLogin(LoginRespMsg message) {
        // TODO 处理登录消息
    }
    @Override
    public void onReceiveEnqueue(EnqueueRespMsg message) {
        // TODO 处理入队消息
    }
    @Override
    public void onReceiveQUpdate(QUpdateRespMsg message) {
        // TODO 处理队列更新消息
    }
    @Override
    public void onReceiveAnswer(AnswerRespMsg message) {
        // TODO 处理客服接通消息
    }
    @Override
    public void onReceiveCMD(CmdRespMsg message) {
        // TODO 处理客服指令消息，具体指令见附录指令表
    }
});
// 发送登录消息 uid token 为接入端的账号
messageManager.sendLoginMsg(uid, token);
// 发送进房间消息
messageManager.sendEnterRoomMsg(roomId, width, height);
// 发送签名上传成功消息
messageManager.sendSignUploadOKMsg(url, width, height);
// 发送视频上传成功消息
messageManager.sendVideoUploadOKMsg(url, width, height);
// 发送退出队列消息
messageManager.sendDequeueMsg(uid);
// 发送同意客服接通消息
messageManager.sendAnswerMsg(uid, answerID);
// 发送队列更新消息
messageManager.sendEnqueueMsg(uid, queueID, priority, booked, from, to);
// 发送文字消息
messageManager.sendTextMsg(to,message,timeStamp);
```

- 签名

收到签名指令时，使用自定义控件`SignView`实现签名板。
```java
OnSignListener onSignListener = new OnSignListener() {
    @Override
    public void onSignStart() {
        // 开始签名
    }
};
signView.setOnSignListener(onSignListener);
// 获取签名
Bitmap bitmap = signView.getSign();
// 重置签名板
signView.reset();
```

- 上传签名图片和视频

签名图片上传同单向单录图片上传。收到推流开始事件后初始化分片视频上传器，收到视频分片消息后上传文件。
```java
// 初始化分片视频上传器，支持失败自动重试
mVideoUploader = new ContinuesVideoUploader(new UploadListener() {
    @Override
    public void onUploadSuccess(String path, String url) {
    }
    @Override
    public void onUploadFail(String path) {
    }
}, 3);

// 上传视频分片
mVideoUploader.append(path);
```

### 2.3 双录单向

无需客服实时参与。后台配置好业务及其对应PPT资料（包含图片和语音）后，客户端选择业务并下载资料，边播放图片、语音，边录制用户摄像头视频到本地。待该业务下所有PPT播放完毕，客户签名并上传签名和的视频。

- 录制

除基本录制操作外，双录单向还需播放PPT图片和语音：
```java
// 推理开始播放PPT和语音，播放完成播放下一页
mRecorder.setBGMNotify(new IAVEMLiveOnBGMNotify() {
    @Override
    public void onBGMPlayProgress(long l) {
    }

    @Override
    public void onBGMPlayFinished() {
        // TODO 未播放完成播放下一页PPT，已播放完成开始签名
    }

    @Override
    public void onBGMPlayErrorOccured(Exception e) {
    }
});
mRecorder.setMicVolume(0);
mRecorder.setDoubleRecordBGImage(currentPPTBitmap);
mRecorder.playBGM(currentPPTAudio);

// 签名
mSignView.setSignListener(new OnSignListener() {
    @Override
    public void onSignStart() {
    }

    @Override
    public void onSignPathDown(float x, float y) {
        mRecorder.beginSignatureAtPoint(new PointF(x, y));
    }

    @Override
    public void onSignPathMove(float x, float y) {
        mRecorder.continueSignatureAtPoint(new PointF(x, y));
    }
});
```

- 获取业务列表

```java
DownloadManager.loadBusinessList(new DownloadListener<List<Business>>() {
    @Override
    public void onDownloadSuccess(List<Business> result) {
        // TODO
    }
    @Override
    public void onDownloadFail() {
        // TODO
    }
});
```

- 获取业务详情

```java
DownloadManager.loadBusinessInfo(businessId, new DownloadListener<BusinessInfoResp>() {
    @Override
    public void onDownloadSuccess(BusinessInfoResp businessInfoResp) {
        // TODO
    }
    @Override
    public void onDownloadFail() {
        // TODO
    }
});
```

- 加载PPT图片和语音资源

```java
DownloadManager.loadResources(businessInfoResp, fileDir, new DownloadListener<List<String>>() {
    @Override
    public void onDownloadSuccess(List<String> paths) {
        // TODO
    }
    @Override
    public void onDownloadFail() {
        // TODO
    }
});
```

- 上传签名图片和视频

```java
UploadManager.uploadImageAndVideo(imagePath, videoPath, new UploadProgressListener() {
    @Override
    public void onUploadSuccess() {
        // TODO
    }
    @Override
    public void onUploadFail() {
        // TODO
    }
    @Override
    public void onUploadProgress(int progress) {
        // TODO 上传进度0~100
    }
});
```

### 2.4 双录双向

需要客服实时参与。结合了双录单向和单录双向的功能，客户端播放PPT时由客服指令控制。客户端选择业务后排队等待客服接通。接通后进入视频通话，通话时通过发消息和接受指令播放PPT和语音、截图、签名等，完成开户。

- 录制

双录双向录制的视频与单录双向类似，需要进房间，不同的是房间类型为`EM_OPEN_ACCOUNT_IV`。
```java
mRecorder.setOpenAccountStage(AVEMLiveConstants.EM_OPEN_ACCOUNT_IV);
```

- 获取业务列表、获取业务详情、加载PPT图片和语音资源同双录单向

- 与客服交互、上传签名图片和视频同单录双向

## 3. 附录

### 3.1 指令表
指令         | 含义
---         | --- 
Text        | 文字推送
Call        | 呼叫用户
Cancel      | 取消呼叫
Rotate      | 旋转视频
Hung        | 挂断视频
Shot        | 开始抓拍
Sign        | 请求客户签名
ReSign      | 请求客户重签签名
Verified    | 签名确认
PlayPPT     | 开始播放PPT
NextStep    | 播放下一页PPT
PreviousStep| 播放上一页PPT
Mirror      | 镜像翻转
StartRecord | 开始录制
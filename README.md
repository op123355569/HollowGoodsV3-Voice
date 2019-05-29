# HollowGoodsV3-Voice
### LastVersion：
[![](https://jitpack.io/v/op123355569/HollowGoodsV3-Voice.svg)](https://jitpack.io/#op123355569/HollowGoodsV3-Voice)

```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

```
dependencies {
	implementation 'com.github.op123355569:HollowGoodsV3-Voice:LastVersion'
}
```

```
<!-- **** 语音识别模块 **** -->
<uses-permission android:name="android.permission.RECORD_AUDIO" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.CHANGE_NETWORK_STATE" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.READ_CONTACTS" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_SETTINGS" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
```

## 使用

### 初始化
```
VoiceUtils.appId = "Your App id";
VoiceUtils.init(Context);
```

### 开始识别
```
voiceUtils = new VoiceUtils(BaseActivity);
voiceUtils.setParam(boolean);

voiceUtils.setVoiceListener(VoiceListener);
// 注意6.0以上需要请求录音权限
voiceUtils.start();
```

### 释放资源
```
VoiceUtils.destroy();
```

## 声明
本库是为了本人使用方便，整合的科大讯飞api，源：[科大讯飞API](https://www.xfyun.cn/)
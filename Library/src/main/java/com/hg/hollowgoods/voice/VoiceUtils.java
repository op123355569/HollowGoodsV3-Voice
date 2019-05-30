package com.hg.hollowgoods.voice;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;

import com.google.gson.Gson;
import com.hg.hollowgoods.Constant.HGSystemConfig;
import com.hg.hollowgoods.UI.Base.BaseActivity;
import com.hg.hollowgoods.Util.APPUtils;
import com.hg.hollowgoods.Util.LogUtils;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.Setting;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;

/**
 * 语音听写工具类
 * Created by Hollow Goods on 2019-05-29.
 */
public class VoiceUtils {

    public static String appId = "";

    public static void init(Context context) {
        // 注意：此接口在非主进程调用会返回null对象，如需在非主进程使用语音功能，请增加参数：SpeechConstant.FORCE_LOGIN+"=true"
        // 参数间使用半角“,”分隔。
        // 设置你申请的应用appid,请勿在'='与appid之间添加空格及空转义符

        // 注意： appid 必须和下载的SDK保持一致，否则会出现10407错误
        StringBuilder sb = new StringBuilder();
        sb.append(SpeechConstant.APPID);
        sb.append("=");
        sb.append(appId);

        if (!APPUtils.isMainThread()) {
            sb.append(",");
            sb.append(SpeechConstant.FORCE_LOGIN);
            sb.append("=");
            sb.append(true);
        }

        // 此处调用与SpeechDemo中重复，两处只调用其一即可
        SpeechUtility.createUtility(context.getApplicationContext(), sb.toString());
        // 以下语句用于设置日志开关（默认开启），设置成false时关闭语音云SDK日志打印
        Setting.setShowLog(HGSystemConfig.IS_DEBUG_MODEL);
    }

    public static void destroy() {
        if (SpeechUtility.getUtility() != null) {
            SpeechUtility.getUtility().destroy();
        }
    }

    public final String[] permissions = {
            Manifest.permission.RECORD_AUDIO,
    };

    private BaseActivity baseActivity;
    private VoiceListener voiceListener;
    private SpeechRecognizer mIat;

    public VoiceUtils(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    public void setVoiceListener(VoiceListener voiceListener) {
        this.voiceListener = voiceListener;
    }

    /**
     * 设置参数
     *
     * @param needPunctuation 是否需要返回标点
     */
    public void setParam(boolean needPunctuation) {

        //初始化识别无UI识别对象
        //使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(baseActivity, code -> LogUtils.Log("SpeechRecognizer createRecognizer code", code));

        //设置语法ID和 SUBJECT 为空，以免因之前有语法调用而设置了此参数；或直接清空所有参数，具体可参考 DEMO 的示例。
        mIat.setParameter(SpeechConstant.CLOUD_GRAMMAR, null);
        mIat.setParameter(SpeechConstant.SUBJECT, null);
        //设置返回结果格式，目前支持json,xml以及plain 三种格式，其中plain为纯听写文本内容
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");
        //此处engineType为“cloud”
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        //设置语音输入语言，zh_cn为简体中文
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        //设置结果返回语言
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin");
        // 设置语音前端点:静音超时时间，单位ms，即用户多长时间不说话则当做超时处理
        //取值范围{1000～10000}
        mIat.setParameter(SpeechConstant.VAD_BOS, "4000");
        //设置语音后端点:后端点静音检测时间，单位ms，即用户停止说话多长时间内即认为不再输入，
        //自动停止录音，范围{0~10000}
        mIat.setParameter(SpeechConstant.VAD_EOS, "1000");
        //设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, needPunctuation ? "1" : "0");
    }

    public void start() {
        // 开始识别，并设置监听器
        mIat.startListening(new RecognizerListener() {
            @Override
            public void onBeginOfSpeech() {
                // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
//                LogUtils.Log("开始说话");
                if (voiceListener != null) {
                    voiceListener.onStart();
                }
            }

            @Override
            public void onError(SpeechError error) {
                // Tips：
                // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
                if (error.getErrorCode() == 14002) {
                    LogUtils.Log(error.getPlainDescription(true) + "\n请确认是否已开通翻译功能");
                } else {
                    LogUtils.Log(error.getPlainDescription(true));
                }
            }

            @Override
            public void onEndOfSpeech() {
                // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
//                LogUtils.Log("结束说话");
                if (voiceListener != null) {
                    voiceListener.onEnd();
                }
            }

            @Override
            public void onResult(RecognizerResult results, boolean isLast) {

//                LogUtils.Log(results.getResultString());

                VoiceResultInfo info = new Gson().fromJson(results.getResultString(), VoiceResultInfo.class);
                StringBuilder sb = new StringBuilder();

                if (info != null && info.getData() != null) {
                    for (VoiceResultInfoChild t : info.getData()) {
                        if (t.getData() != null) {
                            for (VoiceResult p : t.getData()) {
                                sb.append(p.getResult());
                            }
                        }
                    }
                }

                if (voiceListener != null) {
                    voiceListener.onResult(sb.toString());
                }
            }

            @Override
            public void onVolumeChanged(int volume, byte[] data) {
//                LogUtils.Log("当前正在说话，音量大小：" + volume, "返回音频数据：" + data.length);
            }

            @Override
            public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
                // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
                // 若使用本地能力，会话id为null
                //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
                //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
                //		Log.d(TAG, "session id =" + sid);
                //	}
            }
        });
    }

}

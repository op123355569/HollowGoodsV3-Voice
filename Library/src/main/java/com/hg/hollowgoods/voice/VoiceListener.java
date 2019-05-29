package com.hg.hollowgoods.voice;

/**
 * 语音听写监听
 * Created by Hollow Goods on 2019-05-29.
 */
public interface VoiceListener {

    void onStart();

    void onEnd();

    void onResult(String text);

}

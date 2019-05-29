package com.hg.hollowgoods.voice;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * 语音听写结果父类
 * Created by Hollow Goods on 2019-05-29.
 */
public class VoiceResultInfo {

    @SerializedName("sn")
    private int voiceNumber;

    @SerializedName("ls")
    private boolean isLastVoice;

    @SerializedName("ws")
    private ArrayList<VoiceResultInfoChild> data;

    public int getVoiceNumber() {
        return voiceNumber;
    }

    public void setVoiceNumber(int voiceNumber) {
        this.voiceNumber = voiceNumber;
    }

    public boolean isLastVoice() {
        return isLastVoice;
    }

    public void setLastVoice(boolean lastVoice) {
        isLastVoice = lastVoice;
    }

    ArrayList<VoiceResultInfoChild> getData() {
        return data;
    }

    public void setData(ArrayList<VoiceResultInfoChild> data) {
        this.data = data;
    }
}

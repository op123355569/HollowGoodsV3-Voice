package com.hg.hollowgoods.voice;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * 语音听写结果子类
 * Created by Hollow Goods on 2019-05-29.
 */
public class VoiceResultInfoChild {

    @SerializedName("cw")
    private ArrayList<VoiceResult> data;

    ArrayList<VoiceResult> getData() {
        return data;
    }

    public void setData(ArrayList<VoiceResult> data) {
        this.data = data;
    }
}

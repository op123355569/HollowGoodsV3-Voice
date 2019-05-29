package com.hg.hollowgoods.voice;

import com.google.gson.annotations.SerializedName;

/**
 * 语音听写最终结果
 * Created by Hollow Goods on 2019-05-29.
 */
public class VoiceResult {

    @SerializedName("w")
    private String result;

    String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

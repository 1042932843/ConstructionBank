package com.clpays.tianfugou.Utils;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

/**
 * Name: TextToSpeechUtil
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-09-18 17:32
 */

public class TextToSpeechUtil implements TextToSpeech.OnInitListener{

    public TextToSpeech textToSpeech;
    Context context;

    public TextToSpeechUtil(Context context){
        this.context=context;
        textToSpeech = new TextToSpeech(this.context, this);
    }

    public void Speech(String s){
        if (textToSpeech != null && !textToSpeech.isSpeaking()) {
            textToSpeech.setPitch(0.0f);// 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
            textToSpeech.speak(s,
                    TextToSpeech.QUEUE_FLUSH,
                    null);
        }
    }


    public void Stop(){
        textToSpeech.stop(); // 不管是否正在朗读TTS都被打断
        textToSpeech.shutdown(); // 关闭，释放资源
    }


    /**
     * 用来初始化TextToSpeech引擎
     * status:SUCCESS或ERROR这2个值
     * setLanguage设置语言，帮助文档里面写了有22种
     * TextToSpeech.LANG_MISSING_DATA：表示语言的数据丢失。
     * TextToSpeech.LANG_NOT_SUPPORTED:不支持
     */
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.CHINA);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
               ToastUtil.ShortToast("数据丢失或者手机不支持报读");
            }
        }
    }
}

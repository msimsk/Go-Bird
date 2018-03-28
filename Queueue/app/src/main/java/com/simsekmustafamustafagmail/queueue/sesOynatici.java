package com.simsekmustafamustafagmail.queueue;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

/**
 * Created by toshibasatellite on 31.10.2017.
 */

public class sesOynatici {

    private AudioAttributes audioAttributes;
    final int SES_HAVUZU_MAX = 2;
    private static SoundPool sesHavuzu;
    private static int hitSes;
    private static int overSes;
    private static int flowSes;

    //developer.android.com /reference/android/media/SoundPool.html
    public sesOynatici(Context context){

        //SundPool, API düzeyinde 21 lolipopta kullanımdan kaldırıldı
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            sesHavuzu = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)

                    .setMaxStreams(SES_HAVUZU_MAX)
                    .build();
        }else{
            //SoundPool (int maxStreams, int streamType, int srcQuality)
            sesHavuzu = new SoundPool(SES_HAVUZU_MAX, AudioManager.STREAM_MUSIC, 0);
        }






        hitSes = sesHavuzu.load(context, R.raw.hit, 1);
        overSes = sesHavuzu.load(context, R.raw.over, 1);
        //flowSes = sesHavuzu.load(context, R.raw.flow, 1);

    }

    public void hitSesOynat(){
        //play(int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)

        sesHavuzu.play(hitSes, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void overSesOynat(){
        sesHavuzu.play(overSes, 1.0f, 1.0f, 1, 0, 1.0f);

    }
    /*
    public void flowSesOynat(){
        sesHavuzu.play(flowSes, 1.0f, 1.0f, 1, 0, 1.0f);
    }
    */
}

package com.simsekmustafamustafagmail.queueue;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

public class basla extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basla);

        mediaPlayer = MediaPlayer.create(basla.this, R.raw.flow);
        mediaPlayer.start();

    }

    public void oyunaBasla(View view){
        startActivity(new Intent(getApplicationContext(), main.class));

    }

    //geri düğmesini devre sışı bırak
    @Override
    public boolean dispatchKeyEvent(KeyEvent event){
        if (event.getAction() == KeyEvent.ACTION_DOWN){
            switch (event.getKeyCode()){
                case KeyEvent.KEYCODE_BACK:
                    return true;

            }

        }
        return super.dispatchKeyEvent(event);

    }
}

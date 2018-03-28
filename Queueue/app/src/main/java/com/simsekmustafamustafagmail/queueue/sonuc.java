package com.simsekmustafamustafagmail.queueue;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.Queue;

public class sonuc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonuc);

        TextView skorEtiketi = (TextView)findViewById(R.id.skorEtiketi);
        TextView toplam = (TextView)findViewById(R.id.toplamSkorEtiketi);
        TextView yuksekSkorEtiketi = (TextView)findViewById(R.id.yuksekSkorEtiketii);

        int skor = getIntent().getIntExtra("SKOR", 0);
        skorEtiketi.setText(skor + "");

        SharedPreferences settings = getSharedPreferences("GAME_SCOR", Context.MODE_PRIVATE);
        int topScore = settings.getInt("TOP_SCORE", 0);


        Queue<Integer> queue = new LinkedList<>();
        queue.add(skor);
        while (!queue.isEmpty()){
            topScore += queue.poll();
        }

        toplam.setText("Seviye : " + (topScore / 500));

        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("TOP_SCORE", topScore);
        editor.commit();

        SharedPreferences set = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        int highScore = set.getInt("HIGH_SCORE", 0);

        if (skor > highScore){
            yuksekSkorEtiketi.setText("Yüksek Skor : " + skor);

            //kayıt
            SharedPreferences.Editor editor1 = set.edit();
            editor1.putInt("HIGH_SCORE", skor);
            editor1.commit();
        }else{
            yuksekSkorEtiketi.setText("Yüksek Skor : " +highScore);
        }



    }

    public void tekrarDeneyin(View view){
        startActivity(new Intent(getApplicationContext(),main.class));
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

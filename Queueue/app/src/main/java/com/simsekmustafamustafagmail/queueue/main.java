package com.simsekmustafamustafagmail.queueue;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class main extends AppCompatActivity {
    private TextView skorEtiketi;
    private TextView baslaEtiketi;
    private ImageView kus;
    private ImageView yem;
    private ImageView tas;
    private ImageView kirmiziYem;

    //boyut
    private int frameYukseklik;
    private int kusBoyutu;
    private int ekranGenisligi;
    private int ekranYukseklik;


    //pozisyon
    private int kusY;
    private int yemX;
    private int yemY;
    private int kirmiziYemX;
    private int kirmiziYemY;
    private int tasX;
    private int tasY;


    //hız
    private int kusHizi;
    private int yemHizi;
    private int kirmiziYemHizi;
    private int tasHizi;


    //skor
    private int skor = 0;

    //başlat sınıfı
    private Handler zamanlayici = new Handler();
    private Timer zaman = new Timer();
    private sesOynatici ses;

    //durum kontrolü
    private boolean action_flg = false;
    private boolean start_flg = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ses = new sesOynatici(this);

        skorEtiketi = (TextView) findViewById(R.id.skorEtiketi);
        baslaEtiketi = (TextView) findViewById(R.id.baslaEtiketi);
        kus = (ImageView) findViewById(R.id.kus);
        yem = (ImageView) findViewById(R.id.yem);
        tas = (ImageView) findViewById(R.id.tas);
        kirmiziYem = (ImageView) findViewById(R.id.kirmiziYem);






        //ekran boyutu al
        /*
        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);


        ekranGenisligi = size.x;
        ekranYukseklik = size.y;
        */
        ekranGenisligi = getWindowManager().getDefaultDisplay().getWidth();
        ekranYukseklik = getWindowManager().getDefaultDisplay().getHeight();

        //Gm 5 plus kullanıyorum ekran boyutu heigh 1280 width 720
        //hız kus:20 yem:12 kirmiziYem:20 tas:16

        kusHizi = Math.round(ekranYukseklik / 64F); //ekran boyu / 60
        yemHizi = Math.round(ekranGenisligi / 64F); //rkran genişliği / 60
        kirmiziYemHizi = Math.round(ekranGenisligi / 40F); //ekran genişliği / 36
        tasHizi = Math.round(ekranGenisligi / 50F);


        //ekrandan çıkmak
        /*koordinat düzleminde -y ve -x kısımları ekranın dışında bulundukları
        için -80x ve -80y başlangıçlarını ekranın dışından veriyoruz
         */

        yem.setX(-80);
        yem.setY(-80);
        kirmiziYem.setX(-80);
        kirmiziYem.setY(-80);
        tas.setX(-80);
        tas.setY(-80);

        skorEtiketi.setText("skor : 0");

    }

    public void pozisyonDegis() {
        carpma();

       // ses.flowSesOynat();
        //yem
        yemX -= yemHizi;
        if (yemX < 0) {
            yemX = ekranGenisligi + 20;//ekrandan kaç piksel uzaklığı buna göre daha geç gelir
            yemY = (int) Math.floor(Math.random() * (frameYukseklik - yem.getHeight()));
        }
        yem.setX(yemX);
        yem.setY(yemY);

        //tas
        tasX -= tasHizi;
        if (tasX < 0) {
            tasX = ekranGenisligi + 250;
            tasY = (int) Math.floor(Math.random() * (frameYukseklik - tas.getHeight()));
        }
        tas.setX(tasX);
        tas.setY(tasY);

        //kirmiziYem
        kirmiziYemX -= kirmiziYemHizi;
        if (kirmiziYemX < 0) {
            kirmiziYemX = ekranGenisligi + 5000;
            kirmiziYemY = (int) Math.floor(Math.random() * (frameYukseklik - kirmiziYem.getHeight()));
        }
        kirmiziYem.setX(kirmiziYemX);
        kirmiziYem.setY(kirmiziYemY);

        //kuş hareketi
        if (action_flg == true) {
            //dokunduğunda
            kusY -= kusHizi;
        } else {
            //serbest bırakıldığında
            kusY += kusHizi;
        }

        //ekrandan çıkmasını engelleme
        if (kusY < 0) {
            kusY = 0;
        }
        if (kusY > frameYukseklik - kusBoyutu) {
            kusY = frameYukseklik - kusBoyutu;
        }

        kus.setY(kusY);

        skorEtiketi.setText("SKOR : " + skor);
    }

    public void carpma() {
        //topun merkezi kus'de ise, vuruş sayılır
        //yem
        int yemMerkeziX = yemX + yem.getWidth() / 2;
        int yemMerkeziY = yemY + yem.getHeight() / 2;
        //0<=yemMerkeziX<=kusWith
        //kusY<=yemMerkeziY<=kusY+kusYüksekiği
        if (0 <= yemMerkeziX && yemMerkeziX <= kusBoyutu &&
                kusY <= yemMerkeziY && yemMerkeziY <= kusY + kusBoyutu) {
            skor += 10;
            yemX = -10;
            ses.hitSesOynat();
        }
        //kirmiziYem
        int kirmiziyemMerkeziX = kirmiziYemX + kirmiziYem.getWidth() / 2;
        int kirmiziyemMerkeziY = kirmiziYemY + kirmiziYem.getHeight() / 2;
        if (0 <= kirmiziyemMerkeziX && kirmiziyemMerkeziX <= kusBoyutu &&
                kusY <= kirmiziyemMerkeziY && kirmiziyemMerkeziY <= kusY + kusBoyutu) {
            skor += 30;
            kirmiziYemX = -10;
            ses.hitSesOynat();
        }
        //tas
        int tasMerkeziX = tasX + tas.getWidth() / 2;
        int tasMerkeziY = tasY + tas.getHeight() / 2;
        if (0 <= tasMerkeziX && tasMerkeziX <= kusBoyutu &&
                kusY <= tasMerkeziY && tasMerkeziY <= kusY + kusBoyutu) {
            //zamanlayıcıyı durdurulur
            zaman.cancel();
            zaman = null;

            ses.overSesOynat();
            //sonucu göster
            Intent intent = new Intent(getApplicationContext(), sonuc.class);
            intent.putExtra("SKOR", skor);
            startActivity(intent);


        }
    }

    //dokunma
    public boolean onTouchEvent(MotionEvent me) {
        if (start_flg == false) {

            start_flg = true;

            //neden frame yüksekliği ve kus yüksekliği burada olsun?
            //çünkü kullanıcı arayüzü OnCreate()'de ekranda ayarlanmadığı için
            FrameLayout frame = (FrameLayout) findViewById(R.id.frame);
            frameYukseklik = frame.getHeight();

            kusY = (int) kus.getY();

            //kuş bir karedir(yükseklik ve genişlik aynıdır)
            kusBoyutu = kus.getHeight();

            baslaEtiketi.setVisibility(View.GONE);

            zaman.schedule(new TimerTask() {
                @Override
                public void run() {
                    zamanlayici.post(new Runnable() {
                        @Override
                        public void run() {
                            pozisyonDegis();
                        }
                    });
                }
            }, 0, 20);

        } else {
            if (me.getAction() == MotionEvent.ACTION_DOWN) {
                action_flg = true;
            } else if (me.getAction() == MotionEvent.ACTION_UP) {
                action_flg = false;
            }
        }
        return true;
    }

    //geri düğmesini devre sışı bırak
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }


}

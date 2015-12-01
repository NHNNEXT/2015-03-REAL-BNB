package net.balbum.baby;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by hyes on 2015. 11. 30..
 */
public class TEMP extends AppCompatActivity {


InputStream is;
    Bitmap bit;
    ImageView iv;
    TextView tv;
    HttpURLConnection connection;
    Bitmap bmp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp);

//        if (android.os.Build.VERSION.SDK_INT > 9) {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//        }


        final String a = "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcQV80K-5-fjT_RKsYIKFQjpVKhmQRH5k-xkq5yLKe9JslT0zasP";
        final String c = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQYgdGMhHf6TaMiIwvslhKy-FfL77RLopOlYEAXOhyIwtBQbyZT";
        iv = (ImageView) findViewById(R.id.temp_img);
        tv = (TextView) findViewById(R.id.temp_tv);

        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                try {
                    url = new URL(c);
                    tv.setText(url.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                try {
                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    Log.d("test", "bmp: " + bmp.getConfig());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                handler.sendEmptyMessage(0);

                Log.d("test", "setImage1");
            }
        }).start();


    }

    Handler handler = new Handler(new Handler.Callback(){

        @Override
        public boolean handleMessage(Message msg) {
            if(msg != null){
                iv.setImageBitmap(bmp);

            }
            return false;
        }
    });



}
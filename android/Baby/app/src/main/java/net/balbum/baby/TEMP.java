package net.balbum.baby;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

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
    HttpURLConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        iv = (ImageView)findViewById(R.id.temp_img);


        Log.d("test", "setImage1");
//        iv.setImageBitmap(a);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                try {

                    String a = "http://192.168.1.146:8080/img/asdf.jpeg";
                    url = new URL(a);
                    try {
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setDoInput(true);
                        connection.connect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    try {
                        is= connection.getInputStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                    url = new URL(a);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                Bitmap bmp = null;
                try {
                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                iv.setImageBitmap(bmp);
                iv.setBackgroundColor(Color.parseColor("eeff85"));

                iv.setImageBitmap(bit);
                Log.d("test", "setImage2");

            }
        },3000);


    }



}

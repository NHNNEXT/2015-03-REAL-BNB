package net.balbum.baby;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
    static Bitmap bmp = null;
    Context context;
    String b, d;
    URL url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp);
        context = this;

//        if (android.os.Build.VERSION.SDK_INT > 9) {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//        }


        final String a = "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcQV80K-5-fjT_RKsYIKFQjpVKhmQRH5k-xkq5yLKe9JslT0zasP";
        final String c = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQYgdGMhHf6TaMiIwvslhKy-FfL77RLopOlYEAXOhyIwtBQbyZT";
        b = "http://192.168.0.14:8080/img/asdf.jpeg";
        d="http://192.168.0.14:8080/img/asdf2.jpg";

        iv = (ImageView) findViewById(R.id.temp_img);
        tv = (TextView) findViewById(R.id.temp_tv);

        new Thread(new Runnable() {
            @Override
            public void run() {
//                URL url = null;
                try {
                    url = new URL(d);
                    tv.setText(url.toString());



                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                    Log.d("test", "bmp: " + bmp.getConfig());

                }  catch (MalformedURLException e) {
                    e.printStackTrace();
                }  catch (IOException e) {
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

                Picasso.with(context)
                        .load(d)
                        .resize(250, 200)
                        .placeholder(R.mipmap.ic_launcher)
                        .into(iv);

                Log.d("test", "poicassosososo" + d);
//                iv.setImageBitmap(bmp);

            }
            return false;
        }
    });



}
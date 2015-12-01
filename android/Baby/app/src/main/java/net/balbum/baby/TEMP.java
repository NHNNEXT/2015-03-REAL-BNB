package net.balbum.baby;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
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

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        iv = (ImageView)findViewById(R.id.temp_img);
        tv = (TextView)findViewById(R.id.temp_tv);

        Handler handler = new Handler();

        Thread thread = new Thread()

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                try {
                    url = new URL("https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcQV80K-5-fjT_RKsYIKFQjpVKhmQRH5k-xkq5yLKe9JslT0zasP");
                    tv.setText(url.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                try {
                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    Log.d("test", "bmp: "+ bmp.getConfig());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                iv.setImageBitmap(bmp);

                Log.d("test", "setImage1");
            }
        }, 1000);

    }


}
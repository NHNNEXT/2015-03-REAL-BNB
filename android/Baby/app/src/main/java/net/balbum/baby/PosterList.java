package net.balbum.baby;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import net.balbum.baby.adapter.PosterAdapter;

import java.io.File;

/**
 * Created by hyes on 2016. 1. 4..
 */
public class PosterList extends AppCompatActivity {

    Bitmap[] map;
    RecyclerView recyclerView;
    PosterAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster_list);

        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            String path = Environment.getExternalStorageDirectory()+"/temp";
            File file = new File(path);
            String str;
            int num =0;
            int imgCount = file.listFiles().length;
            map = new Bitmap[imgCount];

            if(file.listFiles().length >0 ){
                for(File f : file.listFiles()){
                    str = f.getName();
                    map[num] = BitmapFactory.decodeFile(path + "/" + str);
                    num++;
                }
            }
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager glm = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(glm);

        adapter = new PosterAdapter(map, this);
        recyclerView.setAdapter(adapter);

//        adapter = new CardSelectingAdapter(cardGeneralModelList, this);
//        recyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.poster_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {


        }
        return super.onOptionsItemSelected(item);
    }
}


package net.balbum.baby;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import net.balbum.baby.Util.ActivityUtil;
import net.balbum.baby.VO.GeneralCardVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyes on 2015. 12. 16..
 */
public class PosterMakingActivity extends AppCompatActivity {

    List<Integer> posterImages = new ArrayList<Integer>();
    Bitmap[] map;
    Context context;
    List<GeneralCardVo> selectedCardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster_making2);
        context = this;

//        Intent intent = getIntent();
//        if(intent.getParcelableExtra("list") != null){
//            selectedCardList = Parcels.unwrap(this.getIntent().getParcelableExtra("list"));
//            Log.d("test", "size: " + selectedCardList.size());
//        }
       // initDummy();

//        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
//            String path = Environment.getExternalStorageDirectory()+"/temp";
//
//            File file = new File(path);
//            String str;
//            int num =0;
//
//            int imgCount = file.listFiles().length;
//            map = new Bitmap[imgCount];
//            if(file.listFiles().length >0 ){
//                for(File f : file.listFiles()){
//                    str = f.getName();
//                    map[num] = BitmapFactory.decodeFile(path + "/" + str);
//                    num++;
//                }
//            }
//        }
//        Log.d("test", "map size: " + map.length);
        ImageView p1 = (ImageView)findViewById(R.id.p1);
        ImageView p2 = (ImageView)findViewById(R.id.p2);
        ImageView p3 = (ImageView)findViewById(R.id.p3);
        ImageView p4 = (ImageView)findViewById(R.id.p4);
        ImageView p5 = (ImageView)findViewById(R.id.p5);
        ImageView p6 = (ImageView)findViewById(R.id.p6);
        ImageView p7 = (ImageView)findViewById(R.id.p7);
        ImageView p8 = (ImageView)findViewById(R.id.p8);
        ImageView p9 = (ImageView)findViewById(R.id.p9);

//        ImageView p10 = (ImageView)findViewById(R.id.p10);
//        ImageView p11 = (ImageView)findViewById(R.id.p11);
//        ImageView p12 = (ImageView)findViewById(R.id.p12);
//        ImageView p13 = (ImageView)findViewById(R.id.p13);

        p1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.goToActivity(context, PosterCardSelectingActivity.class);
            }
        });



//        p1.setImageBitmap(map[0]);
//        p2.setImageBitmap(map[1]);
//        p3.setImageBitmap(map[2]);
//        p4.setImageBitmap(map[3]);
//        p5.setImageBitmap(map[4]);
//        p6.setImageBitmap(map[5]);
//        p7.setImageBitmap(map[6]);
//        p8.setImageBitmap(map[7]);
//        p9.setImageBitmap(map[8]);
//        p10.setImageBitmap(map[9]);
//        p11.setImageBitmap(map[10]);
//        p12.setImageBitmap(map[11]);
//        p13.setImageBitmap(map[4]);

//        p1.setImageResource(posterImages.get(0));
//        p2.setImageResource(posterImages.get(1));
//        p3.setImageResource(posterImages.get(2));

    }

    private void initDummy() {
        posterImages.add(R.drawable.b1);
        posterImages.add(R.drawable.b2);
        posterImages.add(R.drawable.b3);
    }
}

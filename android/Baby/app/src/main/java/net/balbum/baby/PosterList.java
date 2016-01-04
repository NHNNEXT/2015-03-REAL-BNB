package net.balbum.baby;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by hyes on 2016. 1. 4..
 */
public class PosterList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster_list);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        GridLayoutManager glm = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(glm);

//        adapter = new CardSelectingAdapter(cardGeneralModelList, this);
//        recyclerView.setAdapter(adapter);
    }
}


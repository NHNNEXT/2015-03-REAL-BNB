package net.balbum.baby;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import net.balbum.baby.VO.CardListVo;
import net.balbum.baby.VO.GeneralCardVo;
import net.balbum.baby.adapter.CardSelectingAdapter;
import net.balbum.baby.lib.retrofit.ServiceGenerator;
import net.balbum.baby.lib.retrofit.TaskService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by hyes on 2015. 12. 22..
 */
public class PosterCardSelectingActivity extends AppCompatActivity{
    List<GeneralCardVo> cardList;
    //cId만 담아도 될 것 같은데 서버를...
    List<Long> selectedCardListLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster_card_selecting);

        selectedCardListLong = new ArrayList<Long>();

        getData();
    }

    private void getData() {
        TaskService taskService = ServiceGenerator.createService(TaskService.class);

        taskService.getCard("token", new Callback<CardListVo>() {
            @Override
            public void success(CardListVo cardListVo, Response response) {
                CardListVo cd = cardListVo;
                cardList = cd.cardList;
                initView(cardList);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("test", " taskService failure");
            }
        });
    }

    private void initView(List<GeneralCardVo> cardGeneralModelList) {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        GridLayoutManager glm = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(glm);

        CardSelectingAdapter adapter = new CardSelectingAdapter(cardGeneralModelList, this);
        recyclerView.setAdapter(adapter);

        adapter.SetOnItemClickListener(new CardSelectingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, List<GeneralCardVo> cards) {
                view.setAlpha(0.5f);
                selectedCardListLong.add(cards.get(position).cid);
//                selectedCardList.add(cards.get(position).cid);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.poster_select, menu);
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

            Intent intent = new Intent(PosterCardSelectingActivity.this, PosterMakingActivity.class);
           // intent.putExtra("list", Parcels.wrap(selectedCardList));
            intent.putExtra("cIds", (Serializable) selectedCardListLong);

            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


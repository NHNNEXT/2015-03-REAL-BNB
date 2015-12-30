package net.balbum.baby;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import net.balbum.baby.Util.ToastUtil;
import net.balbum.baby.Util.TokenUtil;
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
    List<Long> selectedCardListLong;
    Context context;
    CardSelectingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster_card_selecting);
        context = this;
        getData();
    }

    private void getData() {
        TaskService taskService = ServiceGenerator.createService(TaskService.class);

        TokenUtil tk = new TokenUtil(context);
        taskService.getCard(tk.getToken(), new Callback<CardListVo>() {
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

        adapter = new CardSelectingAdapter(cardGeneralModelList, this);
        recyclerView.setAdapter(adapter);

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

            selectedCardListLong = new ArrayList<Long>();
            selectedCardListLong = adapter.selectedCardListLong;

            int size = selectedCardListLong.size();

            if(size != 3){
                if(size < 3){
                    ToastUtil.show(context, "카드가 9개보다 적게 선택되었습니다.");
                }else if(size > 3){
                    ToastUtil.show(context, "카드가 9개보다 많이 선택되었습니다.");
                }
            }else{
                Intent intent = new Intent(PosterCardSelectingActivity.this, PosterMakingActivity.class);
                intent.putExtra("cIds", (Serializable) selectedCardListLong);
                startActivity(intent);
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


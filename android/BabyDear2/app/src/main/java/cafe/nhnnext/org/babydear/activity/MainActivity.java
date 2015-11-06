package cafe.nhnnext.org.babydear.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cafe.nhnnext.org.babydear.R;
import cafe.nhnnext.org.babydear.vo.CardGeneralModel;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private List<CardGeneralModel> cardGeneralModelList;
    private FloatingActionButton floatbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        initToolbar();
        initData();
        initFAB();
        initView();
    }

    private void initView() {
        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        rv.setLayoutManager(llm);

        cafe.nhnnext.org.babydear.adapter.RVAdapter adapter = new cafe.nhnnext.org.babydear.adapter.RVAdapter(cardGeneralModelList, context);
        rv.setAdapter(adapter);

//StaggeredGridLayout test  가로버전이 이느낌이면 괜찮을 것 같음.
//        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
//        rv.setLayoutManager(sglm);

//GridLayout test
//        GridLayoutManager glm = new GridLayoutManager(context, 2);
//        rv.setLayoutManager(glm);
    }

    private void initFAB() {
        floatbtn = (FloatingActionButton) findViewById(R.id.fab_btn);
        floatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, " 글 작성 창으로 이동~~~", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.app_name));
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    private Context initData(){
        cardGeneralModelList = new ArrayList<>();
        cardGeneralModelList.add(new CardGeneralModel("2015.10.10", R.drawable.img1, "륜이 12개월", "챙챙 12개월", "오늘은 하늘이 하늘하늘"));
        cardGeneralModelList.add(new CardGeneralModel("2015.10.22",  R.drawable.img2, "챙챙 12개월", "유림 12개월", "꺄르르 까궁!"));
        cardGeneralModelList.add(new CardGeneralModel("2015.10.28", R.drawable.img3, "유림 12개월", "륜이12개월", "우리아가들 잘도 잔다. 무럭무럭 건강하게만 자라다오(..아마 10년 뒤엔 공부하라고 하겠지?)"));
        cardGeneralModelList.add(new CardGeneralModel("2015.11.03", R.drawable.img6, "유림 13개월", "륜이13개월", "오늘도 맑음"));
        cardGeneralModelList.add(new CardGeneralModel("2015.11.04", R.drawable.img5, "유림 13개월", "오늘의 일과는 블라블라블라~~~~"));
        return null;
    }
}

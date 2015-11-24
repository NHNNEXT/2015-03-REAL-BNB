package net.balbum.baby;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.balbum.baby.VO.BabyTagVo;
import net.balbum.baby.VO.GeneralCardVo;
import net.balbum.baby.adapter.BabyTagAdapter;
import net.balbum.baby.lib.retrofit.TaskService;

import java.util.List;

/**
 * Created by hyes on 2015. 11. 10..
 */
public class GeneralCardFragment extends Fragment implements View.OnClickListener, OnGetCardListener {
    static final int PICTURE_EDIT_COMPLETE = 2;
    private boolean isDone = false;
    private RelativeLayout photo_layout;
    private TextView photo_tv;
    private ImageView camera_iv, photo_iv;
    private EditText memo_tv;
    Context context;
    private Intent pictureActionIntent = null;

    ImageView img_logo;
    LinearLayout childNames;
//    ListView childNames;

    List<BabyTagVo> babyTagNamesList;
    BabyTagAdapter adapter;

   // private CustomOnClickListener customListener;
    private OnGetCardListener getCardInfoListener;
    TaskService taskService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_general_fragment, container, false);
        context = this.getActivity();
      //  initData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!isDone){
            photo_layout = (RelativeLayout)getActivity().findViewById(R.id.photo_layout);
            photo_tv = (TextView)this.getActivity().findViewById(R.id.photo_tv);
            memo_tv = (EditText)this.getActivity().findViewById(R.id.memo_tv);
            camera_iv = (ImageView)this.getActivity().findViewById(R.id.camera_iv);
            photo_iv = (ImageView)this.getActivity().findViewById(R.id.photo_iv);

//            RecyclerView rv_baby = (RecyclerView)this.getActivity().findViewById(R.id.rv_baby);
//            StaggeredGridLayoutManager sgm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
//            rv_baby.setLayoutManager(sgm);
//
//            adapter = new BabyTagAdapter(babyTagNamesList, context);
//            rv_baby.setAdapter(adapter);

            photo_tv.setOnClickListener(this);
            memo_tv.setOnClickListener(this);
            camera_iv.setOnClickListener(this);
            isDone = true;
        }


    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera_iv:
                cardImageEdit();
               // startDialog();
                break;
        }
    }

    private void cardImageEdit() {
        Log.d("test", "cardImageEdit()");

        Intent intent = new Intent(context, CardImageEditActivity.class);
        startActivityForResult(intent, PICTURE_EDIT_COMPLETE);


    }


    @Override
    public GeneralCardVo getCardInfo() {

        EditText memo = (EditText)getActivity().findViewById(R.id.memo_tv);

        GeneralCardVo tempVo = new GeneralCardVo();
        tempVo.memo = memo.getText().toString();
        tempVo.names = adapter.getSelectedList();


        return tempVo;
    }

    public void setSelectedImage(Bitmap bitmap){
        Log.i("test", "나는 여기 프레그먼트에");
        photo_iv.setImageBitmap(bitmap);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("test", "Activity rerult");
        Log.d("test", "resultCode: " + resultCode + "," + Activity.RESULT_OK);
        Log.d("test", "requestCode: "+requestCode+ "," +  PICTURE_EDIT_COMPLETE);


        if (resultCode == Activity.RESULT_OK && requestCode == PICTURE_EDIT_COMPLETE) {
            Log.d("test", "activity for result!");

            this.setSelectedImage(CardImageEditActivity.croppedBitmap);

        }

    }

    //
//    public interface CustomOnClickListener{
//        public void onClicked(int id);
//    }
//
//        @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        customListener = (CustomOnClickListener)activity;
//    }
//
//    View.OnClickListener onClickListener = new View.OnClickListener(){
//
//        @Override
//        public void onClick(View v) {
//            customListener.onClicked(v.getId());
//        }
//    };
//
//
//    public String getItem(){
//        return "item";
//    }

}




//    // Activity 로 데이터를 전달할 커스텀 리스너
//    private CustomOnClickListener customListener;
//
//    // Activity 로 데이터를 전달할 커스텀 리스너의 인터페이스
//    public interface CustomOnClickListener{
//        public void onClicked(int id);
//    }
//
//    // 버튼에 설정한 OnClickListener의 구현, 버튼이 클릭 될 때마다 Activity의 커스텀 리스너를 호출함

//
//    // Activity 로 데이터를 전달할 커스텀 리스너를 연결
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        customListener = (CustomOnClickListener)activity;
//    }
//}

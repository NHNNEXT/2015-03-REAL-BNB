package net.balbum.baby;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.balbum.baby.Util.ConvertBitmapToFileUtil;
import net.balbum.baby.VO.BabyTagVo;
import net.balbum.baby.VO.GeneralCardVo;
import net.balbum.baby.adapter.BabyTagAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyes on 2015. 11. 10..
 */
public class EventCardFragment extends Fragment implements OnGetCardListener{

    boolean isDone = false;
    RelativeLayout photo_layout;
    TextView photo_tv;
    List<BabyTagVo> babyTagNamesList;
    Context context;
    BabyTagAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_event_fragment, container, false);
        context = this.getActivity();
        return view;
    }

    @Override
    public void onResume() {

        super.onResume();
        if(!isDone){
            photo_layout = (RelativeLayout)this.getActivity().findViewById(R.id.photo_layout);
            photo_tv = (TextView)this.getActivity().findViewById(R.id.photo_tv);
            isDone = true;
            initData();
        }

    }

    private void initData(){


        babyTagNamesList = new ArrayList<>();

        Bitmap img1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.b1);
        Bitmap img2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.b2);
        Bitmap img3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.b3);

        File a = ConvertBitmapToFileUtil.convertFile(img1);
        File b = ConvertBitmapToFileUtil.convertFile(img2);
        File c = ConvertBitmapToFileUtil.convertFile(img3);

        BabyTagVo baby1 = new BabyTagVo(a, "산체");
        BabyTagVo baby2 = new BabyTagVo(b, "연두");
        BabyTagVo baby3 = new BabyTagVo(c, "벌이");

        babyTagNamesList.add(baby1);
        babyTagNamesList.add(baby2);
        babyTagNamesList.add(baby3);

    }


    @Override
    public GeneralCardVo getCardInfo() {

        EditText memo = (EditText)getActivity().findViewById(R.id.memo_tv_event);

        GeneralCardVo tempVo = new GeneralCardVo();
        tempVo.memo = memo.getText().toString();
        tempVo.names = adapter.getSelectedNames();
        Log.i("test", "selected : " + tempVo.names.get(0)+ " size: " + tempVo.names.size());
        return tempVo;
    }
    //    protected Dialog onCreateDialog(int id){
//        if(id == DIALOG_ID){
//            return new DatePickerDialog(this.getActivity(), dpickerListener, year_x, month_x, day_x);
//        }
//        return null;
//    }
//
//
//    public void showDialogOnEditTextOnClick(){
//        editText = (EditText)getActivity().findViewById(R.id.editText);
//        editText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDialog(DIALOG_ID);
//
//            }
//        });
//
//    }

//    // Activity 로 데이터를 전달할 커스텀 리스너
//    private CustomOnClickListener customListener;
//
//    // Activity 로 데이터를 전달할 커스텀 리스너의 인터페이스
//    public interface CustomOnClickListener{
//        public void onClicked(int id);
//    }
//
//    // 버튼에 설정한 OnClickListener의 구현, 버튼이 클릭 될 때마다 Activity의 커스텀 리스너를 호출함
//    View.OnClickListener onClickListener = new View.OnClickListener(){
//
//        @Override
//        public void onClick(View v) {
//            customListener.onClicked(v.getId());
//        }
//    };
//
//    // Activity 로 데이터를 전달할 커스텀 리스너를 연결
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        customListener = (CustomOnClickListener)activity;
//    }
}



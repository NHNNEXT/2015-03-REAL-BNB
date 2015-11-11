package net.balbum.baby;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by hyes on 2015. 11. 10..
 */
public class EventCardFragment extends Fragment {

    private boolean isDone = false;
    private RelativeLayout photo_layout;
    private TextView photo_tv;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_event_fragment, container, false);
        return view;
    }

    @Override
    public void onResume() {
        if(!isDone){
            photo_layout = (RelativeLayout)this.getActivity().findViewById(R.id.photo_layout);
            photo_tv = (TextView)this.getActivity().findViewById(R.id.photo_tv);

//            photo_layout.setOnClickListener(onClickListener);
//            photo_tv.setOnClickListener(onClickListener);
            isDone = true;
        }
        super.onResume();
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



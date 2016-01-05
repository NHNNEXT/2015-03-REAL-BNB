package net.balbum.baby;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import net.balbum.baby.Util.Define;
import net.balbum.baby.VO.BabyTagVo;
import net.balbum.baby.VO.GeneralCardVo;
import net.balbum.baby.adapter.BabyTagAdapter;
import net.balbum.baby.lib.retrofit.ServiceGenerator;
import net.balbum.baby.lib.retrofit.TaskService;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by hyes on 2015. 11. 10..
 */
public class EventCardFragment extends Fragment implements OnGetCardListener{

    RelativeLayout event_container;
    EditText date_et_event;
    EditText memo_tv_event;
    List<BabyTagVo> babyTagNamesList;
    Context context;
    BabyTagAdapter adapter;
    GeneralCardVo eventCardVo;
    View view;
    Button bg1;
    Button bg2;
    Button bg3;
    String drawable;
    ImageView event_bg;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.card_event_fragment, container, false);
        context = this.getActivity();

        Bundle bundle = getActivity().getIntent().getExtras();

        if (bundle != null) {
            long card_id = bundle.getLong("cId");
            TaskService taskService = ServiceGenerator.createService(TaskService.class);
            taskService.getOneCard(card_id, new Callback<GeneralCardVo>() {
                @Override
                public void success(GeneralCardVo generalCardVo, Response response) {
                    eventCardVo = (GeneralCardVo) generalCardVo;

                    if(eventCardVo.getType().equals("EVENT")) {
                        memo_tv_event.setText(eventCardVo.content);
                        date_et_event.setText(eventCardVo.modifiedDate);
                        //event_container.setBackground(null);
                        event_bg.setVisibility(View.VISIBLE);
                        Picasso.with(context).load(Define.URL + eventCardVo.cardImg).fit().into(event_bg);

                    }else{
                        event_bg.setVisibility(View.GONE);
                    }
                }
                @Override
                public void failure(RetrofitError error) {

                }
            });
        }
        return view;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        event_container = (RelativeLayout) view.findViewById(R.id.event_container);
        memo_tv_event = (EditText) view.findViewById(R.id.memo_tv_event);
        date_et_event = (EditText) view.findViewById(R.id.date_et_event);
        bg1 = (Button) view.findViewById(R.id.bg1);
        bg2 = (Button) view.findViewById(R.id.bg2);
        bg3 = (Button) view.findViewById(R.id.bg3);
        event_bg = (ImageView)view.findViewById(R.id.event_bg);

//        initData();

        bg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackground(v, Define.EVENT_CARD_BG1);
                drawable = "bg1";
            }
        });
        bg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackground(v, Define.EVENT_CARD_BG2);
                drawable = "bg2";
            }
        });
        bg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackground(v, Define.EVENT_CARD_BG3);
                drawable = "bg3";
            }
        });

        date_et_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyDate(v);
            }
        });
    }
    private void modifyDate(View v) {
        DialogHandler pickerDialog = new DialogHandler(v);
        pickerDialog.show(getFragmentManager(), "date_picker");
    }

    private void setBackground(View button, String type) {
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.event_background);
        button.startAnimation(anim);
        checkBackground();
        if(type == Define.EVENT_CARD_BG1){
            event_container.setBackground(context.getResources().getDrawable(R.drawable.bg1));
        }else if(type == Define.EVENT_CARD_BG2){
            event_container.setBackground(context.getResources().getDrawable(R.drawable.bg2));
        }else if(type == Define.EVENT_CARD_BG3) {
            event_container.setBackground(context.getResources().getDrawable(R.drawable.bg3));
        }
    }

    private void checkBackground() {
        if(event_bg.getVisibility() == View.VISIBLE){
            event_bg.setVisibility(View.GONE);
        }
    }


//    private void initData(){
//
//        babyTagNamesList = new ArrayList<>();
//
//        Bitmap img1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.b1);
//        Bitmap img2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.b2);
//        Bitmap img3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.b3);
//
//        File a = ImageUtil.ConvertBitmapToFile(img1);
//        File b = ImageUtil.ConvertBitmapToFile(img2);
//        File c = ImageUtil.ConvertBitmapToFile(img3);
//
//        BabyTagVo baby1 = new BabyTagVo(a, "산체");
//        BabyTagVo baby2 = new BabyTagVo(b, "연두");
//        BabyTagVo baby3 = new BabyTagVo(c, "벌이");
//
//        babyTagNamesList.add(baby1);
//        babyTagNamesList.add(baby2);
//        babyTagNamesList.add(baby3);
//    }

    @Override
    public GeneralCardVo getCardInfo() {

       // EditText memo = (EditText)getActivity().findViewById(R.id.memo_tv_event);

        GeneralCardVo tempVo = new GeneralCardVo();

        adapter = new BabyTagAdapter(babyTagNamesList, context);

        tempVo.content = memo_tv_event.getText().toString();
        //tempVo.names = adapter.getSelectedNames();
        tempVo.modifiedDate = date_et_event.getText().toString();

        if(drawable == null){
            tempVo.cardImg = "bg1";
        }else{
            tempVo.cardImg = drawable;
        }

        return tempVo;
    }
}



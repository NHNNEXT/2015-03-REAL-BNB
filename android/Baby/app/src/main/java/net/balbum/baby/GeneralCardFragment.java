package net.balbum.baby;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.balbum.baby.Util.Define;
import net.balbum.baby.Util.TimeUtil;
import net.balbum.baby.VO.BabyTagVo;
import net.balbum.baby.VO.GeneralCardVo;
import net.balbum.baby.adapter.BabyTagAdapter;
import net.balbum.baby.lib.retrofit.ServiceGenerator;
import net.balbum.baby.lib.retrofit.TaskService;

import java.io.ByteArrayOutputStream;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by hyes on 2015. 11. 10..
 */
public class GeneralCardFragment extends Fragment implements View.OnClickListener, OnGetCardListener, OnSetCardListener {
    static final int PICTURE_EDIT_COMPLETE = 2;
    private RelativeLayout photo_layout;
    private TextView photo_tv;
    private ImageView camera_iv, photo_iv;
    public EditText memo_tv;
    RecyclerView baby_list;
    Context context;
    EditText card_date_et;
    BabyTagAdapter adapter;
    List<BabyTagVo> babyTagNamesList;
    View view;
    GeneralCardVo card;
    TaskService taskService;
    String card_img;
    int status = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.card_general_fragment, container, false);
        context = this.getActivity();

        Bundle bundle = getActivity().getIntent().getExtras();

        if(bundle == null){
        }

        if (bundle != null) {
            long card_id = bundle.getLong("cId");
            card_img = bundle.getString("cImg");
            status = 1;
            TaskService taskService = ServiceGenerator.createService(TaskService.class);
            taskService.getOneCard(card_id, new Callback<GeneralCardVo>() {
                @Override
                public void success(GeneralCardVo generalCardVo, Response response) {
                    card = (GeneralCardVo) generalCardVo;

                    if(card.getType().equals("NORMAL")) {

                        if (card != null) {
                            memo_tv.setText(card.content);
                            Picasso.with(context)
                                    .load((Define.URL + card.cardImg))
                                    .placeholder(R.mipmap.ic_launcher)
                                    .into(photo_iv);
                            card_date_et.setText(card.modifiedDate);
                        }
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
    public void onResume() {
        super.onResume();
        photo_layout = (RelativeLayout)getActivity().findViewById(R.id.photo_layout);
        photo_tv = (TextView)this.getActivity().findViewById(R.id.photo_tv);
        memo_tv = (EditText)this.getActivity().findViewById(R.id.memo_tv);
        camera_iv = (ImageView)this.getActivity().findViewById(R.id.camera_iv);
        photo_iv = (ImageView)this.getActivity().findViewById(R.id.photo_iv);
        baby_list = (RecyclerView)this.getActivity().findViewById(R.id.rv_baby_list);
        card_date_et = (EditText)this.getActivity().findViewById(R.id.card_date);

        card_date_et.setText(TimeUtil.getRecordedMoment());

        photo_tv.setOnClickListener(this);
        memo_tv.setOnClickListener(this);
        camera_iv.setOnClickListener(this);
        card_date_et.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera_iv:
                cardImageEdit();
                break;
            case R.id.card_date:
                modifyDate(v);
        }
    }

    private void modifyDate(View v) {
        DialogHandler pickerDialog = new DialogHandler(v);
        pickerDialog.show(getFragmentManager(), "date_picker");
    }

    private void cardImageEdit() {
        Log.d("test", "cardImageEdit()");
        Intent intent = new Intent(context, CardImageEditActivity.class);
        startActivityForResult(intent, PICTURE_EDIT_COMPLETE);
    }


    @Override
    public GeneralCardVo getCardInfo() {

        //EditText memo = (EditText)findViewById(R.id.memo_tv);
        //미리 저장된 아기 정보 서버에서 가져오기 또는 내부에 저장해두고 불러오거나
//        getActivity().
   //     adapter = new BabyTagAdapter(babyTagNamesList, context);
        GeneralCardVo tempVo = new GeneralCardVo();

      //  tempVo.names = adapter.getSelectedNames();
//        Log.d("test", "names: " +tempVo.names.size());
        tempVo.modifiedDate = card_date_et.getText().toString();
        tempVo.content = memo_tv.getText().toString();
        if(status != 1) {
            Log.d("test", "0?:  " + status);
            Uri tempUri = getImageUri(context, CardImageEditActivity.croppedBitmap);
            String filePath = getRealPathFromURI(tempUri);
            tempVo.cardImg = filePath;
        }else{
            Log.d("test", "1?:  " + status);
            tempVo.cardImg = Define.URL + card_img;
            status = 0;
        }
        return tempVo;
    }

    public void setSelectedImage(Bitmap bitmap){
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

    @Override
    public void setCardInfo(GeneralCardVo card) {
        Log.d("test", "------------------onSetCardInfo");
      //  Log.d("test", "memo~~~~" + card.content.toString());

//        memo_tv = (EditText)getActivity().findViewById(R.id.memo_tv);
//        EditText memo_tv = (EditText)view.findViewById(R.id.memo_tv);
        memo_tv.setText("testtesttest");


//        EditText memo_tv = (EditText)getActivity().findViewById(R.id.memo_tv);
//        ImageView photo_iv = (ImageView)getActivity().findViewById(R.id.photo_iv);

//        memo_tv.setText(card.content);
        Picasso.with(context)
                .load((card.cardImg))
                .placeholder(R.mipmap.ic_launcher)
                .into(photo_iv);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

}


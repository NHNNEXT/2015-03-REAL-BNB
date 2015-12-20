package net.balbum.baby;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import net.balbum.baby.Util.BitmapUtil;
import net.balbum.baby.Util.Config;
import net.balbum.baby.Util.ToastUtil;
import net.balbum.baby.VO.BabyVo;
import net.balbum.baby.VO.ResponseVo;
import net.balbum.baby.adapter.BabyListAdapter;
import net.balbum.baby.lib.retrofit.ServiceGenerator;
import net.balbum.baby.lib.retrofit.TaskService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

import static net.balbum.baby.Util.ActivityUtil.goToActivity;

/**
 * Created by hyes on 2015. 12. 11..
 */
public class AddBabyFragment extends Fragment {
    Context context;

    EditText add_baby_name;
    EditText add_baby_birthday;
    RecyclerView recyclerView;
    RadioGroup radioGroup;
    ImageView add_baby_image;
    int temp_gender;
    View view;
    BabyListAdapter adapter;

    List<BabyVo> babyVoList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_baby_fragment, container, false);
        context = getActivity();


        babyVoList.add(new BabyVo("까꿍", "2013.12.12", BabyVo.Gender.BOY, "123.img", new Long(1)));

//        if(babyVoList.size() > 0){
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//            recyclerView.setLayoutManager(linearLayoutManager);
//
////            BabyListAdapter adapter = new BabyListAdapter(babyVoList, context);
////            recyclerView.setAdapter(adapter);
//            Log.d("test", "new~ start: " + babyVoList.size());
//        }




        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button ok_btn = (Button) view.findViewById(R.id.add_baby_btn);
        TextView register_later = (TextView) view.findViewById(R.id.register_later);
        add_baby_name = (EditText) view.findViewById(R.id.add_baby_name);
        add_baby_birthday = (EditText) view.findViewById(R.id.add_baby_birthday);
        add_baby_image = (ImageView) view.findViewById(R.id.add_baby_photo);
        radioGroup = (RadioGroup) view.findViewById(R.id.add_baby_radiogroup);
//        recyclerView = (RecyclerView) view.findViewById(R.id.list);

        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        adapter = new BabyListAdapter(babyVoList);
        recyclerView.setAdapter(adapter);

        add_baby_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent pictureActionIntent = null;

            pictureActionIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pictureActionIntent, Config.GALLERY_PICTURE);
            }
        });

        add_baby_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            DialogHandler pickerDialog = new DialogHandler(v);
            pickerDialog.show(getFragmentManager(), "date_picker");
            }
        });

        register_later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(context, MainActivity.class);
            }
        });

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createBabyInfo();


            }
        });

    }

    private void createBabyInfo() {
        BabyVo babyVo = new BabyVo();

        if(add_baby_birthday.getText().toString().matches("")){
            ToastUtil.show(context, "아기 이름을 입력해주세요. ");
        }else if(add_baby_name.getText().toString().matches("")){
            ToastUtil.show(context, "아기 생일(예정일)을 입력해주세요. ");
        }else{
            babyVo.babyBirth = add_baby_birthday.getText().toString();
            babyVo.babyName = add_baby_name.getText().toString();
        }

        temp_gender = radioGroup.getCheckedRadioButtonId();

        if (temp_gender == R.id.radio0) {
            babyVo.babyGender = BabyVo.Gender.GIRL;
        } else if (temp_gender == R.id.radio1) {
            babyVo.babyGender = BabyVo.Gender.BOY;
        } else if (temp_gender == R.id.radio2) {
            babyVo.babyGender = BabyVo.Gender.PREGNANCY;
        }

        Bitmap img = BitmapFactory.decodeResource(context.getResources(), R.drawable.img5);
        File file = BitmapUtil.ConvertBitmapToFile(img);
        TypedFile typedFile = new TypedFile("multipart/form-data", file);

        if (babyVo.babyGender == null) {
            babyVo.babyGender = BabyVo.Gender.UNDEFINED;
        }

        babyVoList.add(babyVo);
        Log.d("test", "after add: " + babyVoList.size());

        TaskService taskService = ServiceGenerator.createService(TaskService.class);
        taskService.createBabyInfo(typedFile, babyVo.babyName, babyVo.babyBirth, babyVo.babyGender.getValue(), new Callback<ResponseVo>() {
            @Override
            public void success(ResponseVo responseVo, Response response) {
                Log.d("test", "baby post success");
                Fragment fragment = new AddBabyFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
//                goToActivity(context, MainActivity.class);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("test", "baby post fail");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK && requestCode == Config.GALLERY_PICTURE) {
            if (data != null) {

                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor cursor = context.getContentResolver().query(selectedImage, filePath,
                        null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePath[0]);
                String selectedImagePath = cursor.getString(columnIndex);
                cursor.close();

                //                if (selectedImagePath != null) {
                //                    txt_image_path.setText(selectedImagePath);
                //                }

                Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath); // load
                // preview cardImg
                // bitmap = Bitmap.createScaledBitmap(bitmap, 800, 800, false);

                bitmap = BitmapUtil.GetRotatedBitmap(bitmap, BitmapUtil.GetExifOrientation(selectedImagePath));
                add_baby_image.setImageBitmap(bitmap);

            } else {
                ToastUtil.cancle(context);
            }
        }
    }

}

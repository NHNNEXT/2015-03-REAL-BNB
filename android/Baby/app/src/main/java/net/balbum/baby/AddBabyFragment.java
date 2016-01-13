package net.balbum.baby;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.Toast;

import net.balbum.baby.Util.Define;
import net.balbum.baby.Util.ImageUtil;
import net.balbum.baby.Util.ToastUtil;
import net.balbum.baby.Util.TokenUtil;
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
    String finalFilePath;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_baby_fragment, container, false);
        context = getActivity();
        return view;
    }

    private void initData() {
        TokenUtil tu = new TokenUtil(context);
        Log.d("test", "token"+tu.getToken());
        TaskService taskService = ServiceGenerator.createService(TaskService.class);
        taskService.getBabies(tu.getToken(), new Callback<ArrayList<BabyVo>>() {
            @Override
            public void success(ArrayList<BabyVo> babyVos, Response response) {
                babyVoList = babyVos;
                Log.d("test", "size: " + babyVoList.size());
                initBabyList();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("test", "getBabies fail");
            }
        });
    }

    private void initBabyList() {
        adapter = new BabyListAdapter(babyVoList, context, R.layout.baby_list_row);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button add_btn = (Button) view.findViewById(R.id.add_baby_btn);
        Button complete_btn = (Button) view.findViewById(R.id.add_complete_btn);
        TextView register_later = (TextView) view.findViewById(R.id.register_later);
        add_baby_name = (EditText) view.findViewById(R.id.add_baby_name);
        add_baby_birthday = (EditText) view.findViewById(R.id.add_baby_birthday);
        add_baby_image = (ImageView) view.findViewById(R.id.add_baby_photo);
        radioGroup = (RadioGroup) view.findViewById(R.id.add_baby_radiogroup);
        TextView add_profile_camera = (TextView) view.findViewById(R.id.profile_camera);
        TextView add_profile_gallery = (TextView) view.findViewById(R.id.profile_gallery);

        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        initData();

        add_profile_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pictureActionIntent = null;

                pictureActionIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                pictureActionIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                startActivityForResult(pictureActionIntent, Define.CAMERA_REQUEST);
            }
        });

        add_baby_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pictureActionIntent = null;

                pictureActionIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                pictureActionIntent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(f));
                startActivityForResult( pictureActionIntent,Define.CAMERA_REQUEST);

            }
        });

        add_profile_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent pictureActionIntent = null;
                pictureActionIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pictureActionIntent, Define.GALLERY_PICTURE);

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

        complete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(context, MainActivity.class);
            }
        });

        add_btn.setOnClickListener(new View.OnClickListener() {
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
            babyVo.babyGender = "GIRL";
        } else if (temp_gender == R.id.radio1) {
            babyVo.babyGender = "BOY";
        } else if (temp_gender == R.id.radio2) {
            babyVo.babyGender = "PREGNANCY";
        }

        File file = new File(finalFilePath);
        TypedFile typedFile = new TypedFile("multipart/form-data", file);

        if (babyVo.babyGender == null) {
            babyVo.babyGender = "UNDEFINED";
        }

        TokenUtil tu = new TokenUtil(context);
        Log.d("test", "token"+tu.getToken());
        TaskService taskService = ServiceGenerator.createService(TaskService.class);
        taskService.createBabyInfo(tu.getToken(), typedFile, babyVo.babyName, babyVo.babyBirth, babyVo.babyGender, new Callback<ResponseVo>() {
            @Override
            public void success(ResponseVo responseVo, Response response) {
                Log.d("test", "baby post success");

                Fragment fragment = new AddBabyFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
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

        if (resultCode == getActivity().RESULT_OK && requestCode == Define.CAMERA_REQUEST) {

            File f = new File(Environment.getExternalStorageDirectory().toString());

            for (File temp : f.listFiles()) {
                if (temp.getName().equals("temp.jpg")) {
                    f = temp;
                    break;
                }
            }

            if (!f.exists()) {

                Toast.makeText(context,

                        "Error while capturing cardImg", Toast.LENGTH_LONG)

                        .show();

                return;

            }

            try {

                // bitmap = Bitmap.createScaledBitmap(bitmap, 800, 800, true);

                int rotate = 0;
                try {
                    ExifInterface exif = new ExifInterface(f.getAbsolutePath());
                    int orientation = exif.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_NORMAL);

                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            rotate = 270;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            rotate = 180;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            rotate = 90;
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;
                Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), options); // load

                Bitmap resized = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

               // bitmap = ImageUtil.GetRotatedBitmap(resized, ImageUtil.GetExifOrientation(f.getAbsolutePath()));

                Matrix matrix = new Matrix();
                matrix.postRotate(rotate);
                bitmap = Bitmap.createBitmap(resized, 0, 0, resized.getWidth(),
                        resized.getHeight(), matrix, true);

                add_baby_image.setImageBitmap(bitmap);
                //storeImageTosdCard(bitmap);

                Uri tempUri = ImageUtil.getImageUri(context, bitmap);

                finalFilePath = ImageUtil.getRealPathFromURI(context, tempUri);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }else if (resultCode == getActivity().RESULT_OK && requestCode == Define.GALLERY_PICTURE) {
            if (data != null) {

                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};

                Cursor cursor = context.getContentResolver().query(selectedImage, filePath,
                        null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePath[0]);
                String selectedImagePath = cursor.getString(columnIndex);
                cursor.close();

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;
                Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath, options); // load

                Bitmap resized = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

                bitmap = ImageUtil.GetRotatedBitmap(resized, ImageUtil.GetExifOrientation(selectedImagePath));
                add_baby_image.setImageBitmap(bitmap);

                Uri tempUri = ImageUtil.getImageUri(context, bitmap);
//                Uri temp = Uri.parse(selectedImagePath);
                finalFilePath = ImageUtil.getRealPathFromURI(context, tempUri);


            } else {
                ToastUtil.cancle(context);
            }
        }
    }

}

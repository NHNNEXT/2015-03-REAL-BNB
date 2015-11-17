package net.balbum.baby;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.balbum.baby.Util.ConvertBitmapToFileUtil;
import net.balbum.baby.VO.BabyTagVo;
import net.balbum.baby.adapter.BabyTagAdapter;
import net.balbum.baby.lib.retrofit.TaskService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyes on 2015. 11. 10..
 */
public class GeneralCardFragment extends Fragment implements View.OnClickListener {
    static final int CAMERA_REQUEST = 0;
    static final int GALLERY_PICTURE = 1;
    private boolean isDone = false;
    private RelativeLayout photo_layout;
    private TextView photo_tv;
    private ImageView camera_iv, photo_iv;
    private EditText memo_tv;
    Context context;
    private Intent pictureActionIntent = null;
    Bitmap bitmap;
    ImageView img_logo;
    LinearLayout childNames;
//    ListView childNames;
    String selectedImagePath;
    List<BabyTagVo> babyTagNamesList;

    private CustomOnClickListener customListener;
    TaskService taskService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_general_fragment, container, false);
        context = this.getActivity();
        initData();
        return view;
    }

    @Override
    public void onResume() {
        if(!isDone){
            photo_layout = (RelativeLayout)getActivity().findViewById(R.id.photo_layout);
            photo_tv = (TextView)this.getActivity().findViewById(R.id.photo_tv);
            memo_tv = (EditText)this.getActivity().findViewById(R.id.memo_tv);
            camera_iv = (ImageView)this.getActivity().findViewById(R.id.camera_iv);
            photo_iv = (ImageView)this.getActivity().findViewById(R.id.photo_iv);

            RecyclerView rv_baby = (RecyclerView)this.getActivity().findViewById(R.id.rv_baby);
            StaggeredGridLayoutManager sgm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
            rv_baby.setLayoutManager(sgm);

            BabyTagAdapter adapter = new BabyTagAdapter(babyTagNamesList, context);
            rv_baby.setAdapter(adapter);

            photo_tv.setOnClickListener(this);
            memo_tv.setOnClickListener(this);
            camera_iv.setOnClickListener(this);
            isDone = true;
        }
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera_iv:
                startDialog();
                break;
        }
    }

    private Context initData(){


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

        return null;

    }

    private void startDialog() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(context);
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your picture?");

        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent pictureActionIntent = null;

                        pictureActionIntent = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(
                                pictureActionIntent,
                                GALLERY_PICTURE);

                    }
                });

        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent intent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(android.os.Environment
                                .getExternalStorageDirectory(), "temp.jpg");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(f));

                        startActivityForResult(intent,
                                CAMERA_REQUEST);

                    }
                });
        myAlertDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        bitmap = null;
        selectedImagePath = null;

        if (resultCode == getActivity().RESULT_OK && requestCode == CAMERA_REQUEST) {

            File f = new File(Environment.getExternalStorageDirectory().toString());

            for (File temp : f.listFiles()) {
                if (temp.getName().equals("temp.jpg")) {
                    f = temp;
                    break;
                }
            }

            if (!f.exists()) {

                Toast.makeText(context,

                        "Error while capturing image", Toast.LENGTH_LONG)

                        .show();

                return;

            }

            try {

                bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());

                bitmap = Bitmap.createScaledBitmap(bitmap, 800, 800, true);

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
                Matrix matrix = new Matrix();
                matrix.postRotate(rotate);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                        bitmap.getHeight(), matrix, true);

                photo_iv.setImageBitmap(bitmap);


                //storeImageTosdCard(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else if (resultCode == getActivity().RESULT_OK && requestCode == GALLERY_PICTURE) {
            if (data != null) {

                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getActivity().getContentResolver().query(selectedImage, filePath,
                        null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                selectedImagePath = c.getString(columnIndex);
                c.close();

//                if (selectedImagePath != null) {
//                    txt_image_path.setText(selectedImagePath);
//                }

                bitmap = BitmapFactory.decodeFile(selectedImagePath); // load
                // preview image
                bitmap = Bitmap.createScaledBitmap(bitmap, 800, 800, false);


                photo_iv.setImageBitmap(bitmap);

            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Cancelled",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_share) {
//            return true;
//        }
//
//        if (id == R.id.action_save) {
//            Intent intent = new Intent(getActivity(), MainActivity.class);
//            Bitmap img1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.b1);
//            File a = ConvertBitmapToFileUtil.convertFile(img1);
//
//            TypedFile typedFile = new TypedFile("multipart/form-data", a);
//
//            taskService.createCard(typedFile, new CardFormVo("card test!!!"), new Callback<ResponseVo>() {
//                @Override
//                public void success(ResponseVo responseVo, Response response) {
//                    Log.i("test", "g card success" + responseVo.state + ", error: " + responseVo.error);
//                }
//
//                @Override
//                public void failure(RetrofitError error) {
//                    Log.i("test", "g card error: " + error);
//                }
//            });
//
//            startActivity(intent);
//
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    public interface CustomOnClickListener{
        public void onClicked(int id);
    }

        @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        customListener = (CustomOnClickListener)activity;
    }

    View.OnClickListener onClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            customListener.onClicked(v.getId());
        }
    };






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

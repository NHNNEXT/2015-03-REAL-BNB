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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.isseiaoki.simplecropview.CropImageView;

import net.balbum.baby.Util.ConvertBitmapToFileUtil;

import java.io.File;
import java.io.IOException;

/**
 * Created by hyes on 2015. 11. 20..
 */
public class CardImageEditActivity extends AppCompatActivity implements View.OnClickListener {
    static final int CAMERA_REQUEST = 0;
    static final int GALLERY_PICTURE = 1;
     Bitmap bitmap;
    String selectedImagePath;
    CropImageView cropImageView;
    ImageView cropButton;
    Context context;
    ImageView croppedImageView;
    static Bitmap croppedBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_edit);
        context = this;


        cropImageView = (CropImageView) findViewById(R.id.cropImageView);
        cropButton = (ImageView) findViewById(R.id.crop_button);
        croppedImageView = (ImageView) findViewById(R.id.croppedImageView);


        ImageView camera_btn = (ImageView) findViewById(R.id.camera_btn);
        ImageView gallery_btn = (ImageView) findViewById(R.id.gallery_btn);
        ImageView confirm_btn = (ImageView) findViewById(R.id.confirm_button);

        cropButton.setOnClickListener(this);
        camera_btn.setOnClickListener(this);
        gallery_btn.setOnClickListener(this);
        confirm_btn.setOnClickListener(this);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        bitmap = null;
        selectedImagePath = null;

        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {

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

                bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());

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

                Matrix matrix = new Matrix();
                matrix.postRotate(rotate);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                        bitmap.getHeight(), matrix, true);

                cropImageView.setImageBitmap(bitmap);
                //storeImageTosdCard(bitmap);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else if (resultCode == RESULT_OK && requestCode == GALLERY_PICTURE) {
            if (data != null) {

                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage, filePath,
                        null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                selectedImagePath = c.getString(columnIndex);
                c.close();

//                if (selectedImagePath != null) {
//                    txt_image_path.setText(selectedImagePath);
//                }

                bitmap = BitmapFactory.decodeFile(selectedImagePath); // load
                // preview cardImg
               // bitmap = Bitmap.createScaledBitmap(bitmap, 800, 800, false);

                bitmap = GetRotatedBitmap(bitmap, GetExifOrientation(selectedImagePath));
                cropImageView.setImageBitmap(bitmap);

            } else {
                Toast.makeText(context, "Cancelled",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

    /*
  * 해당 각도만큼 회전시킨 bitmap을 return
  */
    public synchronized static Bitmap GetRotatedBitmap(Bitmap bitmap, int degrees)
    {
        if ( degrees != 0 && bitmap != null )
        {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) bitmap.getWidth(), (float) bitmap.getHeight());
            try
            {
                Bitmap b2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
                if (bitmap != b2)
                {
                    bitmap.recycle();
                    bitmap = b2;
                }
            }
            catch (OutOfMemoryError ex)
            {
                // We have no memory to rotate. Return the original bitmap.
            }
        }

        return bitmap;
    }

    /*
     * 해당 파일의 exif정보로 회전각도 가져오기
     */
    public synchronized static int GetExifOrientation(String filepath)
    {
        int degree = 0;
        ExifInterface exif = null;

        try
        {
            exif = new ExifInterface(filepath);
        }
        catch (IOException e)
        {
            Log.e("TAG", "cannot read exif");
            e.printStackTrace();
        }

        if (exif != null)
        {
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);

            if (orientation != -1)
            {
                // We only recognize a subset of orientation tag values.
                switch(orientation)
                {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }

            }
        }

        return degree;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch(id){
            case R.id.camera_btn:
                Intent intent = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                File f = new File(android.os.Environment
                        .getExternalStorageDirectory(), "temp.jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(f));

                startActivityForResult(intent,
                        CAMERA_REQUEST);
                break;

            case R.id.gallery_btn:
                Intent pictureActionIntent = null;

                pictureActionIntent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(
                        pictureActionIntent,
                        GALLERY_PICTURE);
                break;

            case R.id.crop_button:
                croppedBitmap = cropImageView.getCroppedBitmap();
                croppedImageView.setImageBitmap(croppedBitmap);
                saveBitmap(croppedBitmap);
                Log.d("TEST", "croppedBitmap " + croppedBitmap.getByteCount());
                break;

            case R.id.confirm_button:
                Log.d("test", "눌림");

                Intent intent1 = getIntent();
                setResult(RESULT_OK, intent1);
                Log.d("test", "눌림after setResult");
                finish();


//                //서버에 저장하는 코드..면 안되겠구나
//                Intent intent1 = new Intent(CardImageEditActivity.this, CardWritingActivity.class);
//                intent1.putExtra("bitmap", (Bitmap)croppedBitmap);
//                setResult(intent1, PICTURE_EDIT_COMPLETE);

                break;

        }
    }

    private void saveBitmap(Bitmap croppedBitmap) {
        File a = ConvertBitmapToFileUtil.convertFile(croppedBitmap);

    }


}


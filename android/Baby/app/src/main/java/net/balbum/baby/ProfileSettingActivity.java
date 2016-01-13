package net.balbum.baby;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.balbum.baby.Util.ActivityUtil;
import net.balbum.baby.Util.Define;
import net.balbum.baby.Util.ImageUtil;
import net.balbum.baby.Util.ToastUtil;

import java.io.File;

/**
 * Created by hyes on 2016. 1. 8..
 */
public class ProfileSettingActivity extends AppCompatActivity {
    Context context;
    ImageView add_profile_photo;
    String finalFilePath;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);
        context = this;

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        TextView add_profile_camera = (TextView) findViewById(R.id.profile_camera);
        TextView add_profile_gallery = (TextView) findViewById(R.id.profile_gallery);
        add_profile_photo = (ImageView) findViewById(R.id.add_profile_photo);
        final EditText signRole = (EditText) findViewById(R.id.sign_role);
        final Button complete = (Button)findViewById(R.id.setting_complete);
        TextView name = (TextView) findViewById(R.id.sign_name_tv);
        TextView email = (TextView) findViewById(R.id.sign_email_tv);

        if (sharedPreferences.contains("profileName")) {
            String profileName = sharedPreferences.getString("profileName", "");
            name.setText(profileName);
        }

        if (sharedPreferences.contains("profileImage")) {
            String profileImage = sharedPreferences.getString("profileImage", "");
            Log.d("test", "profileImage있음 " + profileImage);
            Picasso.with(context).load(profileImage).into(add_profile_photo);
        }

        if (sharedPreferences.contains("profileRole")) {
            String profileRole = sharedPreferences.getString("profileRole", "");
            signRole.setText(profileRole);
        }

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

        add_profile_photo.setOnClickListener(new View.OnClickListener() {
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

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String profileRole = signRole.getText().toString();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("profileRole", profileRole);
//                File file = new File(finalFilePath);
//                TypedFile typedFile = new TypedFile("multipart/form-data", file);
                editor.putString("profileImage", finalFilePath);
                editor.commit();
                ActivityUtil.goToActivity(context, MainActivity.class);

                //서버로 update API필요
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == Define.CAMERA_REQUEST) {

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

                add_profile_photo.setImageBitmap(bitmap);
                //storeImageTosdCard(bitmap);

                Uri tempUri = ImageUtil.getImageUri(context, bitmap);

                finalFilePath = ImageUtil.getRealPathFromURI(context, tempUri);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }else if (resultCode == RESULT_OK && requestCode == Define.GALLERY_PICTURE) {
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
                add_profile_photo.setImageBitmap(bitmap);

                Uri tempUri = ImageUtil.getImageUri(context, bitmap);
//                Uri temp = Uri.parse(selectedImagePath);
                finalFilePath = ImageUtil.getRealPathFromURI(context, tempUri);


            } else {
                ToastUtil.cancle(context);
            }
        }
    }
}

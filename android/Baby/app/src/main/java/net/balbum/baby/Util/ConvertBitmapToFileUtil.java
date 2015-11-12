package net.balbum.baby.Util;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hyes on 2015. 11. 11..
 */
public class ConvertBitmapToFileUtil {

    public static File convertFile(Bitmap bitmap) {


        String filename;
        Date date = new Date(0);
        SimpleDateFormat sdf = new SimpleDateFormat ("yyyyMMddHHmmss");
        filename =  sdf.format(date);
        File file=null;

        try{
            String path = Environment.getExternalStorageDirectory().toString();
            OutputStream fOut = null;
            file = new File(path, "/BALBUM_"+bitmap.toString()+ "_"+ filename+".jpg");
            fOut = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            fOut.flush();
            fOut.close();

//            MediaStore.Images.Media.insertImage(getContentResolver()
//                    ,file.getAbsolutePath(),file.getName(),file.getName());

        }catch (Exception e) {
            e.printStackTrace();
        }

        return file;
    }
}

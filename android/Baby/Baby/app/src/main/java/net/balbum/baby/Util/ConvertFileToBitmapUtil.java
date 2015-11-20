package net.balbum.baby.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

/**
 * Created by hyes on 2015. 11. 11..
 */
public class ConvertFileToBitmapUtil {

    public static Bitmap convertBitmap(File file){
//        Log.i("test", "FILE__________" + file);
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        return bitmap;
    }
}

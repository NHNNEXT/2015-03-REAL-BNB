package net.balbum.baby.Util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by hyes on 2015. 12. 15..
 */
public class ToastUtil {

    public static void show(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void cancle(Context context){
        Toast.makeText(context, "취소", Toast.LENGTH_SHORT).show();
    }

    public static void check(Context context){
        Toast.makeText(context, "확인", Toast.LENGTH_SHORT).show();
    }
}

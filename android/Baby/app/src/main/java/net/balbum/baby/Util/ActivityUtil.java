package net.balbum.baby.Util;

import android.content.Context;
import android.content.Intent;

/**
 * Created by hyes on 2015. 12. 15..
 */
public class ActivityUtil {

    public static void goToActivity(Context context, Class cls){
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }
}

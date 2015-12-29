package net.balbum.baby.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by hyes on 2015. 12. 30..
 */
public class TokenUtil {

    Context context;
    private static String token;


    public TokenUtil(Context context) {
        this.context = context;
        getToken();
    }

    public String getToken() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        if (token == null) {
            if(sharedPreferences.contains("tokenB")) {
                token = sharedPreferences.getString("tokenB", "");
                Log.d("test", "getToken~~" + token);
            }
        }
        return token;
    }
}

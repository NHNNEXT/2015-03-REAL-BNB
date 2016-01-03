package net.balbum.baby.Util;

/**
 * Created by hyes on 2016. 1. 1..
 */
public class EmailUtil {

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}

package net.balbum.baby.Util;

import android.content.Context;
import android.content.res.TypedArray;

import net.balbum.baby.R;

/**
 * Created by hyes on 2015. 11. 11..
 */
public class ToolbarUtil {

    public static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        return toolbarHeight;
    }
}

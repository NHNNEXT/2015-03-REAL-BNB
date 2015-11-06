package cafe.nhnnext.org.babydear;

import android.content.Context;
import android.content.res.TypedArray;

/**
 * Created by hyes on 2015. 11. 4..
 */
public class Utils {

    public static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        return toolbarHeight;
    }


}

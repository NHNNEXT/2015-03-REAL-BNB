package net.balbum.baby;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.Toast;

/**
 * Created by hyes on 2015. 12. 11..
 */
public class DateSettingUtil implements DatePickerDialog.OnDateSetListener {

    Context context;
    public DateSettingUtil (Context context){
        this.context=context;

    }
    @Override
    public void onDateSet(DatePicker view, int year, int dateSetting, int dayOfMonth) {
        Toast.makeText(context, "selected date:" + dateSetting + "/" + dayOfMonth + "/" + year, Toast.LENGTH_LONG).show();
    }
}

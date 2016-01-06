package net.balbum.baby.Util;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

/**
 * Created by hyes on 2015. 12. 11..
 */
public class DateSettingUtil implements DatePickerDialog.OnDateSetListener {

    Context context;
    View editText;

    public DateSettingUtil (Context context, View view){
        this.context=context;
        this.editText =view;

    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        month = month + 1;
        String month_s = (month<10) ? "0"+month : ""+month;
        String day_s = (day<10)?"0"+day:""+day;

        ((EditText) editText).setText(year + "-" + month_s + "-" + day_s);

    }
}

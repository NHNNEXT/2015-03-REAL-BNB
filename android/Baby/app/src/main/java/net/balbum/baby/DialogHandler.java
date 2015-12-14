package net.balbum.baby;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;

import java.util.Calendar;

/**
 * Created by hyes on 2015. 12. 11..
 */
public class DialogHandler extends DialogFragment {

    View view;
    public DialogHandler(View view) {
        this.view = view;
    }

    @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            DateSettingUtil dateSetting=new DateSettingUtil(getActivity(), view);
            Calendar calendar= Calendar.getInstance();
            int year= calendar.get(calendar.YEAR);
            int month=calendar.get(calendar.MONTH);
            int day=calendar.get(calendar.DAY_OF_MONTH);
            DatePickerDialog dialog;
            dialog=new DatePickerDialog(getActivity(),R.style.DialogTheme, dateSetting,year,month,day);
            return dialog;
        }
    }


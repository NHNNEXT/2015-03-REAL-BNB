package net.balbum.baby;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by hyes on 2015. 12. 11..
 */
public class DialogHandler extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            DateSettingUtil dateSetting=new DateSettingUtil(getActivity());
            Calendar calendar= Calendar.getInstance();
            int year= calendar.get(calendar.YEAR);
            int month=calendar.get(calendar.MONTH);
            int day=calendar.get(calendar.DAY_OF_MONTH);
            DatePickerDialog dialog;
            dialog=new DatePickerDialog(getActivity(),dateSetting,year,month,day);
            return dialog;
        }
    }


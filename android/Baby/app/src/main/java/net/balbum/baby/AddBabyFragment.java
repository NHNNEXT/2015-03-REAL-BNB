package net.balbum.baby;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by hyes on 2015. 12. 11..
 */
public class AddBabyFragment extends Fragment {
    Context context;
    Button ok_btn;
    TextView register_later;
    EditText add_baby_name;
    EditText add_baby_birthday;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.add_baby_fragment, container, false);
        context = this.getActivity();
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        ok_btn = (Button)this.getActivity().findViewById(R.id.add_baby_btn);
        register_later = (TextView)this.getActivity().findViewById(R.id.register_later);
        add_baby_name = (EditText)this.getActivity().findViewById(R.id.add_baby_name);
        add_baby_birthday = (EditText)this.getActivity().findViewById(R.id.add_baby_birthday);


        add_baby_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test", "datepicker");
                DialogHandler pickerDialog = new DialogHandler();
                pickerDialog.show(getFragmentManager(),"date_picker");
            }
        });

        register_later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
            }
        });
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
            }
        });


    }

    private void goToMainActivity() {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }
}

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import net.balbum.baby.VO.BabyVo;

/**
 * Created by hyes on 2015. 12. 11..
 */
public class AddBabyFragment extends Fragment {
    Context context;
    Button ok_btn;
    TextView register_later;
    EditText add_baby_name;
    EditText add_baby_birthday;
    ListView listView;
    RadioGroup radioGroup;
    ImageView add_baby_image;
    BabyVo.Gender baby_gender;
    int temp_gender;

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
        add_baby_image = (ImageView)this.getActivity().findViewById(R.id.add_baby_photo);
        radioGroup = (RadioGroup)this.getActivity().findViewById(R.id.add_baby_radiogroup);
        listView = (ListView)this.getActivity().findViewById(R.id.list);

        add_baby_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogHandler pickerDialog = new DialogHandler(v);
                pickerDialog.show(getFragmentManager(), "date_picker");
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

                createBabyInfo();
                goToMainActivity();
            }
        });

    }

    private void createBabyInfo() {
        Log.d("test", "createBabyInfo");
        add_baby_birthday.getText().toString();
        add_baby_name.getText().toString();

        BabyVo babyVo;
        temp_gender =radioGroup.getCheckedRadioButtonId();
        if(temp_gender == R.id.radio0){
            babyVo = new BabyVo(add_baby_birthday.getText().toString(), add_baby_name.getText().toString(), BabyVo.Gender.GIRL, null);
        }else if(temp_gender == R.id.radio1){
            babyVo = new BabyVo(add_baby_birthday.getText().toString(), add_baby_name.getText().toString(), BabyVo.Gender.BOY, null);
        }else{
            babyVo = new BabyVo(add_baby_birthday.getText().toString(), add_baby_name.getText().toString(), BabyVo.Gender.UNDEFINED, null);
        }


    }

    private void goToMainActivity() {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }
}

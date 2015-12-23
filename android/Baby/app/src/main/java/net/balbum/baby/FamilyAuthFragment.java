package net.balbum.baby;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import net.balbum.baby.Util.ActivityUtil;
import net.balbum.baby.Util.ToastUtil;

/**
 * Created by hyes on 2015. 12. 11..
 */
public class FamilyAuthFragment extends Fragment {
    Context context;
    Button ok_btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.family_auth_fragment, container, false);
        context = this.getActivity();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button ok_btn = (Button)view.findViewById(R.id.add_baby_btn);
        final EditText email = (EditText) view.findViewById(R.id.add_family_email);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().matches("")){
                    ToastUtil.show(context, "email을 입력하세요. ");
                }else{
                    ActivityUtil.goToActivity(context, MainActivity.class);
                }
            }
        });

    }
}

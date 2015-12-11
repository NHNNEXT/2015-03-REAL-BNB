package net.balbum.baby;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by hyes on 2015. 12. 11..
 */
public class FamilyAuthFragment extends Fragment {
    Context context;
    Button ok_btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.family_auth_fragment, container, false);
        context = this.getActivity();
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        ok_btn = (Button)this.getActivity().findViewById(R.id.add_baby_btn);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}

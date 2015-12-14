package net.balbum.baby;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by hyes on 2015. 12. 11..
 */
public class InitialSettingActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    FrameLayout fragment_container;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_setting);
        context = this;

        findViewById(R.id.add_baby).setOnClickListener(this);
        findViewById(R.id.family_email_auth).setOnClickListener(this);
        fragment_container = (FrameLayout)findViewById(R.id.fragment_container);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        prepareFragment();
        switch(id){
            case R.id.add_baby:
                Fragment fragment = new AddBabyFragment();
                if(fragment_container.getVisibility() == View.GONE){
                    fragment_container.setVisibility(View.VISIBLE);
                    setFragment(fragment);
                }else if(fragment_container.getVisibility() == View.VISIBLE){
                    replaceFragment(fragment);
                }

                break;

            case R.id.family_email_auth:
                Fragment fragment1 = new FamilyAuthFragment();
                if(fragment_container.getVisibility() == View.GONE){
                    fragment_container.setVisibility(View.VISIBLE);
                    setFragment(fragment1);
                }else if(fragment_container.getVisibility() == View.VISIBLE){
                    replaceFragment(fragment1);
                }
        }
    }


    private void replaceFragment(Fragment fragment) {
        fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
    }

    private void setFragment(Fragment fragment) {
        fragmentTransaction.add(R.id.fragment_container, fragment).commit();
    }

    private void prepareFragment(){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
    }

}

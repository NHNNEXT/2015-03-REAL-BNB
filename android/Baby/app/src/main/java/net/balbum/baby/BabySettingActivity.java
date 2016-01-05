package net.balbum.baby;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by hyes on 2016. 1. 5..
 */
public class BabySettingActivity extends AppCompatActivity {

    //Context context;
    FrameLayout fragment_container;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_setting);

        fragment_container = (FrameLayout) findViewById(R.id.fragment_container);
        prepareFragment();
        Fragment fragment = new AddBabyFragment();

        if (fragment_container.getVisibility() == View.GONE) {
            fragment_container.setVisibility(View.VISIBLE);
            setFragment(fragment);
        } else if (fragment_container.getVisibility() == View.VISIBLE) {
            replaceFragment(fragment);
        }

    }
    private void replaceFragment(Fragment fragment) {
        fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
    }

    private void setFragment(Fragment fragment) {
        fragmentTransaction.add(R.id.fragment_container, fragment).commit();
    }

    private void prepareFragment() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
    }
}

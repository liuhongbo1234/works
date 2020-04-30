package com.xiaomi.mi_deepclean;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActionBar;
import android.os.Bundle;

import com.xiaomi.mi_deepclean.base.Apps;
import com.xiaomi.mi_deepclean.base.BaseActivity;


public class MainActivity extends BaseActivity {
    ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment mainFragment = new MainFragment();
        if (mainFragment != null) {
            transaction.hide(mainFragment);
        }
        transaction.add(R.id.container, mainFragment);
        transaction.show(mainFragment);
        transaction.commit();
        Apps.set(this);
    }

}

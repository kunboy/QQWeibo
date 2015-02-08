package com.kunboy.qweibo.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.kunboy.qweibo.R;
import com.kunboy.qweibo.debug.DebugLog;
import com.kunboy.qweibo.fragment.MainFragment;

/**
 * Created by sunhongkun on 2015/2/8.
 */
public class MainActivity extends FragmentActivity {

    private final String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DebugLog.d(TAG, "onCreate");
        if (getSupportFragmentManager().findFragmentByTag(MainFragment.class.getName()) == null) {
            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(android.R.id.content, MainFragment.getNewInstance(), MainFragment.class.getName());
            ft.commit();
        }

    }


}

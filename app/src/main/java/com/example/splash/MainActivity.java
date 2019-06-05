package com.example.splash;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.splash.fragment.SplashFragment;
import com.example.splash.transformer.ScaleTransformer;

public class MainActivity extends AppCompatActivity {

    private ViewPager mVpMain;

    private int[] mResIds = new int[]{
            R.drawable.bac4,
            R.drawable.bac5,
            R.drawable.bac6,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVpMain = findViewById(R.id.vp_main);


        mVpMain.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return SplashFragment.newInstance(mResIds[i]);
            }

            @Override
            public int getCount() {
                return mResIds.length;
            }
        });

        mVpMain.setPageTransformer(true,new ScaleTransformer());
    }
}

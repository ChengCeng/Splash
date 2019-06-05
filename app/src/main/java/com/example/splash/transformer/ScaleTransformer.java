package com.example.splash.transformer;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.splash.utils.L;


public class ScaleTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.5f;
    private static final float MIN_ALPHA = 0.5f;
    @Override
    //在这个方法体内完成动画的编写
    //要操作两个View，一个是当前滑动的View，一个是即将出现的那个View
    public void transformPage(@NonNull View view, float position) {

        //通过打印日志寻找规律
        L.d(view.getTag() + " , pos = " + position);

        //a->b
        //a , positiom : (0,-1)
        //b , postition : (1,0)

        //b->a
        //a , positiom : (-1,0)
        //b , postition : (0,1)

        if (position <= -1){
            view.setScaleX(MIN_SCALE);
            view.setScaleY(MIN_SCALE);
            view.setAlpha(MIN_ALPHA);

        }else if (position <= 1){
            if (position < 0){//左边这个界面
                //a->b position : (0,1)     把(0，1)变成[1,0.75]
                float scaleA = MIN_SCALE + (1 - MIN_SCALE) * (1 + position);
                view.setScaleX(scaleA);
                view.setScaleY(scaleA);
                //设置滑动的透明度
                float alphaA = MIN_ALPHA + (1-MIN_ALPHA) * (1+position);
                view.setAlpha(alphaA);

            }else {//右边的这个界面
                //a->b
                //b , postition : (1,0)   [0.75,1]
                float scaleB = MIN_SCALE + (1 - MIN_SCALE) * (1 - position);
                view.setScaleX(scaleB);
                view.setScaleY(scaleB);
                //设置滑动的透明度
                float alphaB = MIN_ALPHA + (1-MIN_ALPHA) * (1+position);
                view.setAlpha(alphaB);
            }
        }else {
            view.setScaleX(MIN_SCALE);
            view.setScaleY(MIN_SCALE);
            view.setAlpha(MIN_ALPHA);

        }
    }
}

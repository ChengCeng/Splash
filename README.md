# Splash
基于Android的ViewPager动画特效实现页面左右滑动效果(实现缩放和透明效果)

在上一个项目的基础上做修改，项目包截图如下：

在这里插入图片描述

ScaleTransformer方法在开发中ViewPager是经常用到的，自从Andriod3.0以后增加了动画的效果，让我们的App页面展示更加的生动，当然对于Viewpager来说，也增加了切换的动画。我们平时使用的是ViewPager的默认切换效果，ViewPager自带了一个setPageTransformer用于设置切换动画。
(这里我实现的是左右滑动页缩放效果，页面的不透明度也会随之变化)

新建一个transformer文件夹，里面新建一个ScaleTransformer.java文件，实现他的接口

public class ScaleTransformer implements ViewPager.PageTransformer
重写他的方法
在这个方法体内完成动画的编写，要操作两个View，一个是当前滑动的View，一个是即将出现的那个View，这个方法传递两个参数，一个是view，一个是position：
一个view怎么实现两个view，这里就通过Log日记来找他们的规律(这里就不详细讲解)

    
    public void transformPage(@NonNull View view, float position) {
    }
通过打印日志文件找出一下规律：
从a->b
a页面的position的变化是从0,1
b页面的position的变化是从1,0
从b->a
a页面的position的变化是从-1,0
b页面的position的变化是从0,1

我们定义一个常数为0.75f，通过这个数字变化实现页面变化
所以就有了，如果我们从a->b，把a页面的position(0，1)变成[1,0.75]，把b页面的position(1,0)变成[0.75，1]
如果我们从b->a，把a页面的postition : (1,0) [1,0.75]，把b页面的position(0,1)变成[0.75，1]
这种方式就实现了页面的缩放

这里缩放效果实现了，我们也可以设置页面的透明度，根据缩放效果来设置透明的效果
这里同样是设置一个常数变量，通过变量数值的变化来改变页面
这里根据刚才的规律，把 MIN_ALPHA + (1-MIN_ALPHA) * (1+position);的数值赋值给常熟变量
通过view.setAlpha();的方式来实现。
具体实现代码如下：

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

最后到MainActivity.java中去调用ViewPager的setPageTransformer()来实现动画效果

mVpMain.setPageTransformer(true,new ScaleTransformer());

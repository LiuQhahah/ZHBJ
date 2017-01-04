package edu.njupt.liuq.zhbj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import edu.njupt.liuq.zhbj.utils.PrefUtils;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        //旋转动画
        RelativeLayout rlRoot = (RelativeLayout) findViewById(R.id.rl_root);
        RotateAnimation animation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        animation.setDuration(1000); //动画时间
        animation.setFillAfter(true);//保持动画结束状态

        //缩放动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1000);
        scaleAnimation.setFillAfter(true);

        //渐变动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setFillAfter(true);

        //动画集合
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(animation);
        set.addAnimation(scaleAnimation);
        set.addAnimation(alphaAnimation);

        //启动动画
        rlRoot.startAnimation(set);

        //监听动画
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //动画结束，跳转页面
                //如果是第一次，跳新手页面
                //否则跳转到主页面

                boolean isFirstEnter = PrefUtils.getBoolean(
                        SplashActivity.this, "is_first_enter", true);
                Intent intent;
                if (isFirstEnter) {
                    //新手引导
                  intent = new Intent(SplashActivity.this,GuideActivity.class);

                } else {
                    //主页面
                   intent = new Intent(SplashActivity.this,MainActivity.class);
                   PrefUtils.SetBoolean(SplashActivity.this,"is_first_enter",false);
                }
                startActivity(intent);
                finish();//结束当前页面
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}

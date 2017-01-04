package edu.njupt.liuq.zhbj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);




        System.out.println(2);

        RelativeLayout rlRoot = (RelativeLayout) findViewById(R.id.rl_root);
    }
}

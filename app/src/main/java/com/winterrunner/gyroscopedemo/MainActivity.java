package com.winterrunner.gyroscopedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.winterrunner.xgyroscope.GyroscopeManager;
import com.winterrunner.xgyroscope.XImageView;

/**
 * author: L.K.X
 * created on: 2017/7/20 下午3:20
 * description:
 */


public class MainActivity extends AppCompatActivity {

    private GyroscopeManager gyroscopeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gyroscopeManager = new GyroscopeManager();


        XImageView imageView1 = (XImageView) findViewById(R.id.iv1);
        XImageView imageView2 = (XImageView) findViewById(R.id.iv2);
        XImageView imageView3 = (XImageView) findViewById(R.id.iv3);


        imageView1.setGyroscopeManager(gyroscopeManager);
        imageView2.setGyroscopeManager(gyroscopeManager);
        imageView3.setGyroscopeManager(gyroscopeManager);
    }


    @Override
    protected void onResume() {
        gyroscopeManager.register(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        gyroscopeManager.unregister();
        super.onPause();
    }
}

package me.xujichang.androidframework;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import me.xujichang.xframework.bean.FrameworkConst;
import me.xujichang.xframework.interfaces.BaiduMapBase;
import me.xujichang.xframework.module.map.FrameworkMapLocationActivity;

/**
 * @author xjc
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void start(View view) {
        Intent intent = new Intent(this, TestActivity.class);
        intent.putExtra(FrameworkConst.Intent.LOCATION_MODE, BaiduMapBase.MAP_MODE_CENTER);
        startActivity(intent);
    }
}

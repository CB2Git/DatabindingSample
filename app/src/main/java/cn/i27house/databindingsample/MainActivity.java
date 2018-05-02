package cn.i27house.databindingsample;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.i27house.databindingsample.databinding.ActivityMainBinding;
import cn.i27house.databindingsample.sample1.Sample1Activity;
import cn.i27house.databindingsample.sample2.Sample2Activity;
import cn.i27house.databindingsample.sample2.User;
import cn.i27house.databindingsample.sample3.Sample3Activity;
import cn.i27house.databindingsample.sample4.Sample4Activity;
import cn.i27house.databindingsample.sample5.Sample5Activity;
import cn.i27house.databindingsample.sample6.Sample6Activity;
import cn.i27house.databindingsample.sample7.Sample7Activity;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setHandler(this);
    }

    public void jump(View v) {
        String tag = (String) v.getTag();
        Intent intent = new Intent();
        if ("sample1".equals(tag)) {
            intent.setClass(this, Sample1Activity.class);
        } else if ("sample2".equals(tag)) {
            intent.setClass(this, Sample2Activity.class);
        } else if ("sample3".equals(tag)) {
            intent.setClass(this, Sample3Activity.class);
        } else if ("sample4".equals(tag)) {
            intent.setClass(this, Sample4Activity.class);
        } else if ("sample5".equals(tag)) {
            intent.setClass(this, Sample5Activity.class);
        } else if ("sample6".equals(tag)) {
            intent.setClass(this, Sample6Activity.class);
        } else if ("sample7".equals(tag)) {
            intent.setClass(this, Sample7Activity.class);
        }
        startActivity(intent);
    }
}

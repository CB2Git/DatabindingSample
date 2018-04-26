package cn.i27house.databindingsample.sample6;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Date;

import cn.i27house.databindingsample.R;
import cn.i27house.databindingsample.databinding.ActivitySample6Binding;

public class Sample6Activity extends AppCompatActivity {

    private ActivitySample6Binding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sample6);
        mBinding.setDate(new Date());
    }
}

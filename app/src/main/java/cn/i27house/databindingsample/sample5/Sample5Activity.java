package cn.i27house.databindingsample.sample5;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.i27house.databindingsample.R;
import cn.i27house.databindingsample.databinding.ActivitySample5Binding;

public class Sample5Activity extends AppCompatActivity {

    private ActivitySample5Binding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sample5);
    }
}

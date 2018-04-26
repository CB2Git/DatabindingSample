package cn.i27house.databindingsample.sample1;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.i27house.databindingsample.R;
import cn.i27house.databindingsample.databinding.ActivitySample1Binding;

public class Sample1Activity extends AppCompatActivity {

    private ActivitySample1Binding mBinding;

    private User mUser = new User("Cool", null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sample1);
        mBinding.setUser(mUser);
        mBinding.viewStub.getViewStub().inflate();
    }
}

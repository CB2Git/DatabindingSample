package cn.i27house.databindingsample.sample7;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import cn.i27house.databindingsample.R;
import cn.i27house.databindingsample.databinding.ActivitySample1Binding;
import cn.i27house.databindingsample.databinding.ActivitySample7Binding;

public class Sample7Activity extends AppCompatActivity {

    private ActivitySample7Binding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sample7);
        mBinding.setLoginbean(new LoginBean());
        mBinding.setHandler(this);
    }

    public void login(LoginBean bean){
        Toast.makeText(this, bean.toString(), Toast.LENGTH_SHORT).show();
    }
}

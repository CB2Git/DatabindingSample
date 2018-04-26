package cn.i27house.databindingsample.sample2;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import cn.i27house.databindingsample.R;
import cn.i27house.databindingsample.databinding.ActivitySample2Binding;

public class Sample2Activity extends AppCompatActivity {

    private ActivitySample2Binding mBinding;

    private User mUser = new User("123", "456");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sample2);
        mBinding.setHandler(this);
        mBinding.setUser(mUser);
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mUser.setFirstName(s.toString());
    }
}

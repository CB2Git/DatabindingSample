package cn.i27house.databindingsample.sample3;

import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import cn.i27house.databindingsample.R;
import cn.i27house.databindingsample.databinding.ActivitySample3Binding;

public class Sample3Activity extends AppCompatActivity {

    private User mUser = new User("sample3", "456");

    private ActivitySample3Binding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sample3);
        mBinding.setHandler(this);
        mBinding.setUser(mUser);
        mUser.firstName.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                Log.i("123", "onPropertyChanged: " + mUser.firstName.get());
            }
        });
    }

    public void change(User user) {
        mUser.setFirstName("听说有人被点击了"+user);
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mUser.setFirstName(s.toString());
    }
}

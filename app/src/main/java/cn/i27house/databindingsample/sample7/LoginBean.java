package cn.i27house.databindingsample.sample7;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import cn.i27house.databindingsample.BR;

public class LoginBean extends BaseObservable {

    private String mUserName;

    private String mUserPwd;

    public LoginBean() {
    }

    @Bindable
    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
        notifyPropertyChanged(BR.userName);
    }

    @Bindable
    public String getUserPwd() {
        return mUserPwd;
    }

    public void setUserPwd(String userPwd) {
        mUserPwd = userPwd;
        notifyPropertyChanged(BR.userPwd);
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "mUserName='" + mUserName + '\'' +
                ", mUserPwd='" + mUserPwd + '\'' +
                '}';
    }
}

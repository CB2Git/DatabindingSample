package cn.i27house.databindingsample.sample3;

import android.databinding.ObservableField;

/**
 * 不能有set  不然无效
 */
public class User {

    public ObservableField<String> firstName;

    public ObservableField<String> lastName;

    public User(String firstName, String lastName) {
        this.firstName = new ObservableField<>(firstName);
        this.lastName = new ObservableField<>(lastName);
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName=" + firstName.get() +
                ", lastName=" + lastName.get() +
                '}';
    }
}

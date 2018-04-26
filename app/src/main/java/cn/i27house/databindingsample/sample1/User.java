package cn.i27house.databindingsample.sample1;

import android.databinding.Observable;
import android.databinding.ObservableField;

public class User {

    String firstName;

    String lastName;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String xml() {
        return "xml"+firstName;
    }
}

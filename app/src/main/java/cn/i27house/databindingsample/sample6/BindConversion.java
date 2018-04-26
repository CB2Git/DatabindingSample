package cn.i27house.databindingsample.sample6;

import android.databinding.BindingConversion;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BindConversion {

    @BindingConversion
    public static String conversion(Date date){
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
        return time.format(date);
    }
}

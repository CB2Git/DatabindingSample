package cn.i27house.databindingsample.sample5;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class BindAdapter {

    @BindingAdapter({"bind:netImage"})
    public static void loadUrl(ImageView img, String oldUrl, String newUrl) {
        if (!newUrl.equals(oldUrl)) {
            Glide.with(img.getContext()).load(newUrl).into(img);
        }
    }
}

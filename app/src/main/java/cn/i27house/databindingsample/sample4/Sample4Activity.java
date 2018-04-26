package cn.i27house.databindingsample.sample4;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import cn.i27house.databindingsample.BR;
import cn.i27house.databindingsample.databinding.ActivitySample4Binding;

import cn.i27house.databindingsample.R;

public class Sample4Activity extends AppCompatActivity {

    private ActivitySample4Binding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sample4);
        mBinding.setHandler(new Presenter());
        mBinding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recycleView.setAdapter(new MyAdapter());
    }

    public class Presenter {

        public void onClick(View v) {
            Toast.makeText(Sample4Activity.this, "" + v.getTag(), Toast.LENGTH_SHORT).show();

        }
    }

    private class MyAdapter extends RecyclerView.Adapter<BindingViewHolder> {

        private static final int ITEM_TYPE_TYPE_MAM = 1;

        private static final int ITEM_TYPE_TYPE_WOMEN = 2;

        private List<User> mUsers = new ArrayList<>();

        public MyAdapter() {
            mUsers.add(new User(10, "10", 0));
            mUsers.add(new User(12, "t0", 1));
            mUsers.add(new User(10, "10", 1));
            mUsers.add(new User(13, "13", 1));
            mUsers.add(new User(13, "10", 0));
            mUsers.add(new User(14, "h0", 0));
            mUsers.add(new User(10, "h0", 0));
            mUsers.add(new User(11, "10", 0));
        }

        @Override
        public int getItemViewType(int position) {
            User user = mUsers.get(position);
            if (user.getSex() == 0) {
                return ITEM_TYPE_TYPE_MAM;
            } else {
                return ITEM_TYPE_TYPE_WOMEN;
            }
        }

        @NonNull
        @Override
        public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            int layoutId = 0;
            if (viewType == ITEM_TYPE_TYPE_MAM) {
                layoutId = R.layout.activity_sample4_man;
            } else if (viewType == ITEM_TYPE_TYPE_WOMEN) {
                layoutId = R.layout.activity_sample4_woman;
            }

            ViewDataBinding inflate = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), layoutId, parent, false);
            BindingViewHolder holder = new BindingViewHolder(inflate);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
            User user = mUsers.get(position);
            holder.getBinding().setVariable(BR.item, user);
            holder.getBinding().executePendingBindings();
        }

        @Override
        public int getItemCount() {
            return mUsers.size();
        }
    }
}

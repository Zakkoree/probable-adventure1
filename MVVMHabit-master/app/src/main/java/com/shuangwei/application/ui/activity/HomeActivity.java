package com.shuangwei.application.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shuangwei.application.R;
import com.shuangwei.application.BR;
import com.shuangwei.application.databinding.ActivityHomeBinding;
import com.shuangwei.application.ui.vm.HomeViewModel;


import me.goldze.mvvmhabit.base.BaseActivity;


public class HomeActivity extends BaseActivity<ActivityHomeBinding, HomeViewModel> {



    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_home;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public HomeViewModel initViewModel() {
        return new HomeViewModel(this);
    }

    @Override
    public void initViewObservable() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}

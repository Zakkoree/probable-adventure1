package com.shuangwei.application.ui.activity;

import android.os.Bundle;

import com.shuangwei.application.BR;
import com.shuangwei.application.R;
import com.shuangwei.application.databinding.ActivityDemoBinding;
import com.shuangwei.application.ui.vm.DemoViewModel;

import me.goldze.mvvmhabit.base.BaseActivity;


public class DemoActivity extends BaseActivity<ActivityDemoBinding, DemoViewModel> {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_demo;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public DemoViewModel initViewModel() {
        return new DemoViewModel(this);
    }
}

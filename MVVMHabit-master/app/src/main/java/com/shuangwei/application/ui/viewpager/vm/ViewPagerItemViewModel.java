package com.shuangwei.application.ui.viewpager.vm;

import android.content.Context;

import me.goldze.mvvmhabit.base.BaseViewModel;


public class ViewPagerItemViewModel extends BaseViewModel {
    public String text;

    public ViewPagerItemViewModel(Context context, String text) {
        super(context);
        this.text = text;
    }
}

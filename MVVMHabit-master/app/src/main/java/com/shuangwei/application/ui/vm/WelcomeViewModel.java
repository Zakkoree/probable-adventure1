package com.shuangwei.application.ui.vm;

import android.content.Context;

import io.reactivex.Completable;
import me.goldze.mvvmhabit.base.BaseViewModel;

public class WelcomeViewModel extends BaseViewModel {
    public WelcomeViewModel(Context context) {
        //要使用父类的context相关方法,记得加上这一句
        super(context);
    }

}

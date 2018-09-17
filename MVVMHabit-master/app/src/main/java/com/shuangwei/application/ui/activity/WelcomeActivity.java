package com.shuangwei.application.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.shuangwei.application.R;
import com.shuangwei.application.BR;
import com.shuangwei.application.databinding.ActivityHomeBinding;
import com.shuangwei.application.ui.tab_bar.activity.TabBarActivity;
import com.shuangwei.application.ui.vm.WelcomeViewModel;


import me.goldze.mvvmhabit.base.BaseActivity;


public class WelcomeActivity extends BaseActivity<ActivityHomeBinding, WelcomeViewModel> {
    @Override
    public int initContentView(Bundle savedInstanceState) {      return R.layout.activity_welcome;    }

    @Override
    public int initVariableId() {   return BR.viewModel;    }

    @Override
    public WelcomeViewModel initViewModel() {
        //View持有ViewModel的引用 (这里暂时没有用Dagger2解耦)
        return new WelcomeViewModel(this);
    }

    @Override
    public void initViewObservable() {
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void initView(){
        Thread myThread=new Thread(){//创建子线程
            @Override
            public void run() {
                try{
                    sleep(5000);//使程序休眠五秒
                    Intent intent  = new Intent(WelcomeActivity.this, TabBarActivity.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        myThread.start();//启动线程
    }

}

package com.shuangwei.application.ui.tab_bar.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.shuangwei.application.BR;
import com.shuangwei.application.R;
import com.shuangwei.application.databinding.ActivityTabBarBinding;
import com.shuangwei.application.ui.tab_bar.fragment.TabBar1Fragment;
import com.shuangwei.application.ui.tab_bar.fragment.TabBar2Fragment;
import com.shuangwei.application.ui.tab_bar.fragment.TabBar3Fragment;
import com.shuangwei.application.ui.tab_bar.fragment.TabBar4Fragment;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

/**
 * 底部tab按钮的例子
 * 所有例子仅做参考,理解如何使用才最重要。
 */

public class TabBarActivity extends BaseActivity<ActivityTabBarBinding, BaseViewModel> {

    private List<Fragment> mFragments;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_tab_bar;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public BaseViewModel initViewModel() {
        return new BaseViewModel();
    }

    @Override
    public void initData() {

        initFragment();//初始化Fragment

        initBottomTab();//初始化底部Button
    }

    private static final long BACK_WAIT_TIME = 2000;
    private long mTouchTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if((System.currentTimeMillis() - mTouchTime) >= BACK_WAIT_TIME){
                mTouchTime = System.currentTimeMillis();
                ToastUtils.showShort("再次点击退应用");
            }else{
                mTouchTime = 0;
                TabBarActivity.this.finish();
                //退出时如需kill进程
                System.exit(0);
                //or
                //android.os.Process.killProcess(android.os.Process.myPid());

            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(new TabBar1Fragment());
        mFragments.add(new TabBar2Fragment());
        mFragments.add(new TabBar3Fragment());
        mFragments.add(new TabBar4Fragment());
        //默认选中第一个
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frameLayout, mFragments.get(0));
        transaction.commitAllowingStateLoss();
    }
    private void initBottomTab() {
        NavigationController navigationController = binding.pagerBottomTab.material()
                .addItem(R.mipmap.yingyong, "教")
                .addItem(R.mipmap.huanzhe, "育")
                .addItem(R.mipmap.xiaoxi_select, "无")
                .addItem(R.mipmap.wode_select, "忧")
                .setDefaultColor(ContextCompat.getColor(this, R.color.textColorVice))
                .build();
        //底部按钮的点击事件监听
        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (index==0){
                    Log.d("sds", "-------------------------------------onSelected: "+index);
                    transaction.replace(R.id.frameLayout, mFragments.get(index));
                    transaction.commitAllowingStateLoss();
                }else {
                    transaction.replace(R.id.frameLayout, mFragments.get(index));
                    transaction.commitAllowingStateLoss();
                }
            }

            @Override
            public void onRepeat(int index) {

            }
        });
    }

}
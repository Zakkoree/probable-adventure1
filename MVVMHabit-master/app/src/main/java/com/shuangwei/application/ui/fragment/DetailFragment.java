package com.shuangwei.application.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.shuangwei.application.BR;
import com.shuangwei.application.R;
import com.shuangwei.application.entity.DemoEntity;
import com.shuangwei.application.ui.vm.DetailViewModel;

import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * 详情界面
 */
public class DetailFragment extends BaseFragment{

    private DemoEntity.ItemsEntity entity;

    @Override
    public void initParam() {
        //获取列表传入的实体
        Bundle mBundle = getArguments();
        if (mBundle != null) {
            entity = mBundle.getParcelable("entity");
        }
    }

    @Override
    public int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_detail;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public DetailViewModel initViewModel() {
        return new DetailViewModel(entity);
    }
}

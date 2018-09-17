package com.shuangwei.application.ui.vm;

import com.shuangwei.application.entity.DemoEntity;

import me.goldze.mvvmhabit.base.BaseViewModel;



public class DetailViewModel extends BaseViewModel {
    public DemoEntity.ItemsEntity entity;

    public DetailViewModel(DemoEntity.ItemsEntity entity) {
        this.entity = entity;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        entity = null;
    }
}

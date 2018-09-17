package com.shuangwei.application.ui.viewpager.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.shuangwei.application.BR;
import com.shuangwei.application.R;
import com.shuangwei.application.databinding.FragmentViewpagerBinding;
import com.shuangwei.application.ui.viewpager.vm.ViewPagerViewModel;

import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * ViewPager绑定的例子, 更多绑定方式，请参考 https://github.com/evant/binding-collection-adapter
 */

public class ViewPagerFragment extends BaseFragment<FragmentViewpagerBinding, ViewPagerViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_viewpager;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public ViewPagerViewModel initViewModel() {
        return new ViewPagerViewModel(getContext());
    }

    @Override
    public void initData() {
        // 使用 TabLayout 和 ViewPager 相关联
        binding.tabs.setupWithViewPager(binding.viewPager);
        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabs));
    }
}

package com.shuangwei.application.ui.tab_bar.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.shuangwei.application.BR;
import com.shuangwei.application.R;

import java.math.BigDecimal;
import java.util.HashMap;

import me.crosswall.lib.coverflow.core.CoverTransformer;
import me.crosswall.lib.coverflow.core.PageItemClickListener;
import me.crosswall.lib.coverflow.core.PagerContainer;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;


public class TabBar1Fragment extends BaseFragment{
    private int[] covers = {R.mipmap.splash,R.mipmap.splash,R.mipmap.splash,R.mipmap.splash,R.mipmap.splash};
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_tab_bar_1;
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
    public void initData(){        // 初始化布局 View视图
        init();
        coverflow();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    private SliderLayout mDemoSlider;
    private void init(){
        mDemoSlider = (SliderLayout)binding.getRoot().findViewById(R.id.slider);
        HashMap<String,String> urlMaps = new HashMap<>();
        urlMaps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        urlMaps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        urlMaps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        for(String name : urlMaps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this.getActivity());
            textSliderView
                    .description(name)//描述
                    .image(urlMaps.get(name))//image方法可以传入图片url、资源id、File
                    .setScaleType(BaseSliderView.ScaleType.Fit)//图片缩放类型
                    .setOnSliderClickListener(onSliderClickListener);//图片点击
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra",name);//传入参数
            mDemoSlider.addSlider(textSliderView);//添加一个滑动页面
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);//滑动动画
//      mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);//默认指示器样式
        mDemoSlider.setCustomIndicator((PagerIndicator)binding.getRoot().findViewById(R.id.custom_indicator2));//自定义指示器
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());//设置图片描述显示动画
        mDemoSlider.setDuration(4000);//设置滚动时间，也是计时器时间
    }
    private ViewPager pager;
    private void coverflow(){
        PagerContainer container = (PagerContainer) binding.getRoot().findViewById(R.id.pager_container);
        pager = container.getViewPager();
        PagerAdapter adapter = new MyPagerAdapter();
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(adapter.getCount());
        pager.setClipChildren(false);
        pager.setPageTransformer(false, new CoverTransformer(0.3f, 0f, 0f, 0f));
        pager.setFocusable(false);
        BigDecimal decimal1 = new BigDecimal(covers.length);
        BigDecimal decimal2 = new BigDecimal(2);
        BigDecimal decimal = decimal1.divideToIntegralValue(decimal2);
        adapter.notifyDataSetChanged();// 通过数据修改
        pager.setCurrentItem(decimal.intValue());// 切换到指定页面
        container.setPageItemClickListener(new PageItemClickListener(){

            @Override
            public void onItemClick(View view, int i) {

                Log.d("asdasd","-----------------------------------------------------------"+i+"|"+pager.getCurrentItem());
            }
        });

    }



    private BaseSliderView.OnSliderClickListener onSliderClickListener=new BaseSliderView.OnSliderClickListener() {
        @Override
        public void onSliderClick(BaseSliderView slider) {
            //图片点击事件

        }
    };
    //页面改变监听
    private ViewPagerEx.OnPageChangeListener onPageChangeListener=new ViewPagerEx.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            Log.d("ansen", "Page Changed: " + position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    boolean DOWN ;

    private ImageView.OnTouchListener imgOnTouch = new ImageView.OnTouchListener(){
//        MotionEvent
//        public static final int ACTION_DOWN             = 0;单点触摸动作
//        public static final int ACTION_UP               = 1;单点触摸离开动作
//        public static final int ACTION_MOVE             = 2;触摸点移动动作
//        public static final int ACTION_CANCEL           = 3;触摸动作取消
//        public static final int ACTION_OUTSIDE         = 4;触摸动作超出边界
//        public static final int ACTION_POINTER_DOWN     = 5;多点触摸动作
//        public static final int ACTION_POINTER_UP       = 6;多点离开动作
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction()==0){
                int[] location = new int[2];
                v.getLocationOnScreen(location);//在其整个屏幕上的坐标位置
                //v.getLocationInWindow(location);//在其父窗口中的坐标位置
                //MotionEvent.getRawY   在其整个屏幕上的坐标位置
                //MotionEvent.getY      在该窗口中的坐标位置
                int x = location[0];
                int y = location[1];
                if(event.getRawX() >= x && event.getRawY() >= y && event.getRawX() <= x+v.getWidth() && event.getRawY() <= y+v.getHeight()) {
                    DOWN = true; }  else { DOWN = false; }
            }
            if (event.getAction()==1){  if (DOWN){
                    Log.d("ACTION_UP","-----------------------------------------------------------"+DOWN);
                }
            }
            return true;
        }
    };

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(TabBar1Fragment.this.getActivity()).inflate(R.layout.image_cover,null);
            ImageView imageView = (ImageView) view.findViewById(R.id.image_cover);
            imageView.setTag(position);
            view.setTag(position);
            imageView.setImageDrawable(getResources().getDrawable(covers[position]));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setOnTouchListener(imgOnTouch);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }

        @Override
        public int getCount() {
            return covers.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }

    }
}

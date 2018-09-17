package com.shuangwei.application.ui.activity;

import android.databinding.Observable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;

import com.shuangwei.application.BR;
import com.shuangwei.application.R;
import com.shuangwei.application.databinding.ActivityLoginBinding;
import com.shuangwei.application.entity.DemoEntity;
import com.shuangwei.application.service.DemoApiService;
import com.shuangwei.application.ui.vm.LoginViewModel;
import com.shuangwei.application.utils.RetrofitClient;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.goldze.mvvmhabit.http.ResponseThrowable;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * 一个MVVM模式的登陆界面
 */
public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> {
    //ActivityLoginBinding类是databinding框架自定生成的,对应activity_login.xml
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public LoginViewModel initViewModel() {
        //View持有ViewModel的引用 (这里暂时没有用Dagger2解耦)
        return new LoginViewModel(this);
    }

    private final int charMaxNum = 15; // 允许输入的字数
    private CharSequence temp;    // 监听前的文本
    private int editStart;      // 光标开始位置
    private int editEnd;        // 光标结束位置
    private Handler handler = new Handler();
    @Override
    public void initViewObservable() {

        binding.btnLogin.setEnabled(false);
        //监听ViewModel中pSwitchObservable的变化, 当ViewModel中执行【uc.pSwitchObservable.set(!uc.pSwitchObservable.get());】时会回调该方法
        viewModel.uc.pSwitchObservable.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                //pSwitchObservable是boolean类型的观察者,所以可以直接使用它的值改变密码开关的图标
                if (viewModel.uc.pSwitchObservable.get()) {
                    //密码可见
                    //在xml中定义id后,使用binding可以直接拿到这个view的引用,不再需要findViewById去找控件了
                    binding.ivSwichPasswrod.setImageResource(R.mipmap.show_psw_press);
                    binding.etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //密码不可见
                    binding.ivSwichPasswrod.setImageResource(R.mipmap.show_psw);
                    binding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        //账号实时验证
        binding.etAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //之前
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                 //temp = s   用于记录当前正在输入文本的个数
                temp = s;
            }

            @Override
            public void afterTextChanged(Editable s) {//之后
                if(binding.etAccount!=null) {
                    editStart = binding.etAccount.getSelectionStart();
                    editEnd = binding.etAccount.getSelectionEnd();
                    //account.setText("" + temp.length());  //把输入temp中记录的字符个数显示在TextView上
                    if (temp.length() > charMaxNum) {
                        s.delete(editStart - 1, editEnd);
                        int tempSelection = editStart;
                        binding.etAccount.setText(s);
                        binding.etAccount.setSelection(tempSelection);
                    }
                    if(temp.length()<=5){
                        binding.etAccount.setTextColor(getResources().getColor(R.color.colorAccent));
                        binding.btnLogin.setEnabled(false);
                        return;
                    }else {

                        //每次editText有变化的时候，则移除上次发出的延迟线程
                        if(handler!=null)
                            handler.removeCallbacks(checkAct);
                        handler.postDelayed(checkAct, 1000);
                    }
                }
            }
        });
    }

    /**
     * 账号延时验证
     */
    Runnable checkAct = new Runnable() {
        @Override
        public void run() {
            checkNetWork();

        }
    };

    /**
     * 验证账号信息
     */
    private void checkNetWork() {
        RetrofitClient.getInstance().create(DemoApiService.class)
                .demoPost(binding.etAccount.getText().toString())
                .compose(RxUtils.bindToLifecycle(this)) //请求与View周期同步
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        //加载
                    }
                })
                .subscribe(new Consumer<BaseResponse<DemoEntity>>() {
                    @Override
                    public void accept(BaseResponse<DemoEntity> response) throws Exception {
                        //请求成功
                        if (response.getCode() == 1) {
                            //response.getResult().getItems();
                            //将实体赋给全局变量，双向绑定动态添加Item
                            binding.etAccount.setTextColor(getResources().getColor(R.color.textColor));
                            binding.btnLogin.setEnabled(true);
                        } else {
                            binding.etAccount.setTextColor(getResources().getColor(R.color.colorAccent));
                            binding.btnLogin.setEnabled(false);

                            //code错误时也可以定义Observable回调到View层去处理
                            //ToastUtils.showShort("错误");
                        }
                    }
                }, new Consumer<ResponseThrowable>() {
                    @Override
                    public void accept(ResponseThrowable throwable) throws Exception {
                        //ToastUtils.showShort(throwable.message);
                        throwable.printStackTrace();

                    }
                });
    }
}

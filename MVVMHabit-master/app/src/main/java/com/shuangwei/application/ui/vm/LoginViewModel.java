package com.shuangwei.application.ui.vm;

import android.app.Activity;
import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.text.TextUtils;
import android.view.View;

import com.shuangwei.application.entity.DemoEntity;
import com.shuangwei.application.entity.UserEntity;
import com.shuangwei.application.service.DemoApiService;
import com.shuangwei.application.ui.activity.DemoActivity;
import com.shuangwei.application.ui.activity.HomeActivity;
import com.shuangwei.application.utils.EncryptUtils;
import com.shuangwei.application.utils.RetrofitClient;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.goldze.mvvmhabit.http.ResponseThrowable;
import me.goldze.mvvmhabit.http.cookie.CookieJarImpl;
import me.goldze.mvvmhabit.http.cookie.store.PersistentCookieStore;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;


public class LoginViewModel extends BaseViewModel {

    private int itemIndex = 0;

    private String Encryptpassword = null;

    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();
    //用户名的绑定
    public ObservableField<String> userName = new ObservableField<>("");
    //密码的绑定
    public ObservableField<String> password = new ObservableField<>("");
    //用户名清除按钮的显示隐藏绑定
    public ObservableInt clearBtnVisibility = new ObservableInt();

    public class UIChangeObservable {
        public ObservableBoolean pSwitchObservable = new ObservableBoolean(false);//密码开关观察者
    }

    public LoginViewModel(Context context) {    super(context);     }

    //清除用户名的点击事件, 逻辑从View层转换到ViewModel层
    public BindingCommand clearUserNameOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            userName.set("");
        }
    });

    //密码显示开关  (你可以尝试着狂按这个按钮,会发现它有防多次点击的功能)
    public BindingCommand passwordShowSwitchOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //让观察者的数据改变,逻辑从ViewModel层转到View层，在View层的监听则会被调用
            uc.pSwitchObservable.set(!uc.pSwitchObservable.get());
        }
    });

    //用户名输入框焦点改变的回调事件
    public BindingCommand<Boolean> onFocusChangeCommand = new BindingCommand<>(new BindingConsumer<Boolean>() {
        @Override
        public void call(Boolean hasFocus) {
            if (hasFocus) {
                clearBtnVisibility.set(View.VISIBLE);
            } else {
                clearBtnVisibility.set(View.INVISIBLE);
            }
        }
    });

    //登录按钮的点击事件
    public BindingCommand loginOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call()  {   login();   }


    });

    private void login() {
        if (TextUtils.isEmpty(password.get())){
            ToastUtils.showShort("密码错误！");
            return;
        }
        if (password.get().length()<6){
            ToastUtils.showShort("密码错误！");
            return;
        }
        //Encryptpassword=EncryptUtils.MD5(password.get());
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(password.get().getBytes());
            Encryptpassword=new BigInteger(1,md5.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(!TextUtils.isEmpty(Encryptpassword)) {
            loginNetWork();
        }
    }

    public ObservableList<LoginViewModel> observableList = new ObservableArrayList<>();
    public void loginNetWork() {
        RetrofitClient.getInstance().create(DemoApiService.class)
                .loginPost(userName.get(),Encryptpassword)
                .compose(RxUtils.bindToLifecycle(context)) //请求与View周期同步
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                })
                .subscribe(new Consumer<BaseResponse<UserEntity>>() {
                    @Override
                    public void accept(BaseResponse<UserEntity> response) throws Exception {
                        dismissDialog();//关闭对话框
                        if (response.getCode() == 1) {//请求成功
                            if (response.getData().getToken()!=null) {
                                //token的保存
                                Cookie.Builder builder = new Cookie.Builder();
                                builder.name("token");
                                builder.value(response.getData().getToken());
                                builder.domain("sw.spacemea.com");
                                builder.path("/");
                                Cookie cookie = builder.build();
                                new PersistentCookieStore(context).saveCookie(HttpUrl.get(new URL("http://sw.spacemea.com/staff_api/login/Login")), cookie, cookie.name() + "@" + cookie.domain());
                            }
                            //ToastUtils.showShort(response.getMessage());
                            startActivity(HomeActivity.class);//进入DemoActivity页面
                            ((Activity) context).finish();//关闭页面
                        } else {
                            //code错误时也可以定义Observable回调到View层去处理
                            ToastUtils.showShort("密码错误！");
                        }
                    }
                }, new Consumer<ResponseThrowable>() {
                    @Override
                    public void accept(ResponseThrowable throwable) throws Exception {
                        dismissDialog();
                        ToastUtils.showShort(throwable.message);
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //资源回收释放
        /*if (loadMoreHandler != null) {
            //界面销毁时移除Runnable，实际网络请求时不需要手动取消请求，在请求时加入.compose(RxUtils.bindToLifecycle(context))绑定生命周期，在界面销毁时会自动取消网络访问，避免界面销毁时子线程还存在而引发的逻辑异常
            loadMoreHandler.removeCallbacks(loadMoreRunnable);
        }*/
    }
}

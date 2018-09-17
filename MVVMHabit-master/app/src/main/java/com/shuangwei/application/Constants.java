package com.shuangwei.application;

/**
 * author:
 * description: Constants${DES} 常量类
 */
public class Constants {

    public static String baseUrl ;
    public static String HTML_URL ;
    static {
        if (BuildConfig.DEBUG_APK) {  /*如果是开发   模式，则使用测试环境*/
            baseUrl = "http://sw.spacemea.com/";
        } else {                      /*如果上线模式，则使用正式环境  */
//            baseUrl = "http://hpyapp.avaryholding.com/pdzp/public/api/";
            baseUrl = "http://sw.spacemea.com/";
        }
    }

    public static final String URL_CHAT = "";
}

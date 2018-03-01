package com.itheima.mvp.view;

/**
 * 作者： itheima
 * 时间：2016-10-14 17:12
 * 网址：http://www.itheima.com
 */

public interface LoginView {
    void onPrelogin();
    void onAfterLogin(boolean isSuccess,String msg);
}

package com.itheima.mvp.presenter;

import com.itheima.mvp.view.LoginView;

/**
 * 作者： itheima
 * 时间：2016-10-14 17:12
 * 网址：http://www.itheima.com
 */

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView mLoginView;

    public LoginPresenterImpl(LoginView loginView) {
        mLoginView = loginView;
    }

    @Override
    public void login(String username, String name) {

        mLoginView.onPrelogin();

        //TODO 登录逻辑
        new Thread(new Runnable() {
            @Override
            public void run() {
                //TODO 登录完了

                mLoginView.onAfterLogin(true,"OK");
            }
        }).start();

    }
}

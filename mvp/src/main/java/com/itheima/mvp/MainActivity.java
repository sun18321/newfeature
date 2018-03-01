package com.itheima.mvp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.itheima.mvp.presenter.LoginPresenter;
import com.itheima.mvp.presenter.LoginPresenterImpl;
import com.itheima.mvp.view.LoginView;

public class MainActivity extends AppCompatActivity implements LoginView {

    private EditText mUsername;
    private EditText mPwd;
    private LoginPresenter mLoginPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUsername = (EditText) findViewById(R.id.et_username);
        mPwd = (EditText) findViewById(R.id.et_pwd);

        mLoginPresenter = new LoginPresenterImpl(this);
    }

    public void login(View view) {
        String username = mUsername.getText().toString().trim();
        String pwd = mPwd.getText().toString().trim();

        mLoginPresenter.login(username,pwd);
    }

    @Override
    public void onPrelogin() {
        Toast.makeText(this, "要登录了，显示进度条对话框", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAfterLogin(boolean isSuccess, String msg) {
        if (isSuccess){
            Toast.makeText(this, "登录成了，我要跳转到主界面了", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "失败了："+msg, Toast.LENGTH_SHORT).show();
        }
    }
}

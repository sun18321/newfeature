package com.itheima.newfeature;

import android.Manifest;
import android.animation.AnimatorInflater;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.MemoryFile;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, TextView.OnEditorActionListener {

    private static final int REQUEST_STORAGE = 1;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private TextInputLayout mTextInputLayout;
    private EditText mEditText;
    private EditText mEditText1;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //TODO
        }
    };
    private Handler mSubHandler;
    private Looper mLooper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");

        /**
         *  默认系统会添加ActionBar，需要将ActionBar替换为Toolbar
         */
        setSupportActionBar(mToolbar);
        //        mToolbar.setSubtitle("子标题");
        //给Toolbar添加导航图标
        //        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //
        //            }
        //        });
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name);
        //同步mDrawerLayout和mToolbar的状态
        actionBarDrawerToggle.syncState();
        //给DrawerLayout设置监听器，监听器用于修改菜单打开和关闭时的图标
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        mNavigationView = (NavigationView) findViewById(R.id.navigationView);
        mNavigationView.setNavigationItemSelectedListener(this);
        mTextInputLayout = (TextInputLayout) findViewById(R.id.til);
        mEditText = (EditText) findViewById(R.id.et_username);
        mEditText1 = (EditText) findViewById(R.id.et_pwd);
        mEditText.setOnEditorActionListener(this);
        mEditText1.setOnEditorActionListener(this);
        /**
         * 为了测试Hanlder开辟的子线程
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();

                mLooper = Looper.myLooper();

                mSubHandler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        Toast.makeText(MainActivity.this, "收到消息了", Toast.LENGTH_SHORT).show();
                    }
                };
                Looper.loop();
                Log.d("tag","跳出循环了");
            }
        }).start();
    }
    public void send(View view) {
        mSubHandler.sendEmptyMessage(1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放线程资源
        mLooper.quit();//跳出循环

    }

    public boolean click(View view) throws IOException {
        /**
         * 1. 检查权限
         * 2. 如果没有得到授权则申请权限
         * 3. 当用户授权成功后再执行当前的方法
         */
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED) {
            //没有被授权，申请权限
            /**
             * 参数2：一次性可以请求多个权限，把这些权限的字符串放到数组里
             */
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE);
            return false;
        }
        String username = mEditText.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            mTextInputLayout.setErrorEnabled(true);
            mTextInputLayout.setError("用户名不能为空！");
            return false;
        } else {
            mTextInputLayout.setErrorEnabled(false);
        }
        String pwd = mEditText1.getText().toString().trim();
        File file = new File(Environment.getExternalStorageDirectory(), "user.txt");
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(username + "-" + pwd);
        fileWriter.close();
        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        return true;

    }

    /**
     * 当用户对申请的权限做批示后的回调
     *
     * @param requestCode  用户申请权限时的请求码
     * @param permissions  用户申请的权限
     * @param grantResults 权限被授权的结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_STORAGE) {
            if (grantResults[0] == PermissionChecker.PERMISSION_GRANTED) {
                Toast.makeText(this, "用户同意了您的权限申请", Toast.LENGTH_SHORT).show();
                try {
                    click(null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "用户拒绝了您的权限申请", Toast.LENGTH_SHORT).show();
            }
        }

    }

    /**
     * 用于给Activity绑定菜单
     *
     * @param menu
     * @return true 会显示菜单，返回false不会显示菜单
     * 1. 编写菜单的布局文件
     * 2. 使用菜单填充器填充菜单
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        //        menu.add("动态添加的");
        return true;
    }

    /**
     * 当展示菜单的时候调用
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //        Toast.makeText(this, "onPrepareOptionsMenu被调用了", Toast.LENGTH_SHORT).show();
        if (menu instanceof MenuBuilder) {
            MenuBuilder menuBuilder = (MenuBuilder) menu;
            menuBuilder.setOptionalIconsVisible(true);
        }
        return true;
    }

    /**
     * 当点击Menu条目时回调
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                Toast.makeText(this, "添加好友", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_share:
                Toast.makeText(this, "分享好友", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_about:
                Toast.makeText(this, "关于我们", Toast.LENGTH_SHORT).show();
                break;
            //            case android.R.id.home:
            //                Toast.makeText(this, "点击了导航条目", Toast.LENGTH_SHORT).show();
            //                break;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_left_recyclerView:
                startActivity(new Intent(this, RecyclerViewActivity.class));
                break;
            case R.id.menu_left_tabLayout:
                startActivity(new Intent(this, TabLayoutActivity.class));
                break;
            case R.id.menu_left_appBarLayout:
                startActivity(new Intent(this, AppBarLayoutActivity.class));
                break;
            case R.id.menu_left_collaspingtoolbarlayout:
                startActivity(new Intent(this, CollapsingToolbarLayoutActivity.class));
                break;
        }
        //关闭DrawerLayout
        mDrawerLayout.closeDrawer(mNavigationView);
        return true;
    }

    /**
     * 当Action键被点击时
     *
     * @param v        EditText
     * @param actionId android:imeOptions="actionDone" 的id
     * @param event
     * @return
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (v.getId()) {
            case R.id.et_username:
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    try {
                        if (click(null)) {
                            mEditText1.requestFocus();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.et_pwd:
                Toast.makeText(this, "点击了done", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

}

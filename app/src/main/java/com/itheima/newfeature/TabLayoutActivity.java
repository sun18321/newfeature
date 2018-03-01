package com.itheima.newfeature;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class TabLayoutActivity extends AppCompatActivity implements View.OnClickListener {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Handler mHandler = new Handler();

    private int[] mStraggeredIcons = new int[]{R.mipmap.p1, R.mipmap.p2, R.mipmap.p3, R
            .mipmap.p4, R.mipmap.p5, R.mipmap.p6, R.mipmap.p7, R.mipmap.p8, R.mipmap.p9, R
            .mipmap.p10, R.mipmap.p11, R.mipmap.p12, R.mipmap.p13, R.mipmap.p14, R.mipmap
            .p15, R.mipmap.p16, R.mipmap.p17, R.mipmap.p18, R.mipmap.p19, R.mipmap.p20, R
            .mipmap.p21, R.mipmap.p22, R.mipmap.p23, R.mipmap.p24, R.mipmap.p25, R.mipmap
            .p26, R.mipmap.p27, R.mipmap.p28, R.mipmap.p29, R.mipmap.p30, R.mipmap.p31, R
            .mipmap.p32, R.mipmap.p33, R.mipmap.p34, R.mipmap.p35, R.mipmap.p36, R.mipmap
            .p37, R.mipmap.p38, R.mipmap.p39, R.mipmap.p40, R.mipmap.p41, R.mipmap.p42, R
            .mipmap.p43, R.mipmap.p44};
    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                //传递进入主线程中绑定的Looper
//                mHandler = new Handler(Looper.getMainLooper());
//            }
//        }).start();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        //让TabLayout跟ViewPager关联起来
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        mViewPager.setAdapter(new PagerAdapter() {

            @Override
            public int getCount() {
                return mStraggeredIcons.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(TabLayoutActivity.this);

                //由于ImageView是手动new出来的，因此默认没有layoutParams
                ViewPager.LayoutParams layoutParams = new ViewPager.LayoutParams();
                layoutParams.width = ViewPager.LayoutParams.MATCH_PARENT;
                layoutParams.height = ViewPager.LayoutParams.MATCH_PARENT;
                imageView.setLayoutParams(layoutParams);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setImageResource(mStraggeredIcons[position]);
                //一定记得添加到ViewPager中
                container.addView(imageView);

                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
//                super.destroyItem(container, position, object);
                container.removeView((View) object);
            }

            /**
             * 返回的标题用于显示在TabLayout上
             * @param position
             * @return
             */
            @Override
            public CharSequence getPageTitle(int position) {
                return "美图"+position;
            }
        });
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(this);

    }
    private boolean isStart;
    @Override
    public void onClick(View v) {
        ObjectAnimator.ofFloat(mFloatingActionButton,"rotation",0,360).setDuration(1000).start();

        Snackbar.make(mFloatingActionButton,isStart?"要停止播放吗?":"要开始播放吗？",Snackbar.LENGTH_LONG)
                .setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isStart){
                            //停止播放ViewPager
                            stopPlay();
                        }else {
                            //开始播放ViewPager
//                            mViewPager.setCurrentItem(1+1);
                            startPlay();
                        }
                        isStart=!isStart;
                    }
                }).show();
    }

    private void startPlay() {
        /**
         * 立即将任务发送出去，只不过这个任务会在2s后被执行
         */
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int index = (mViewPager.getCurrentItem()+1)%mStraggeredIcons.length;
                mViewPager.setCurrentItem(index,true);
                mHandler.postDelayed(this,2000);
            }
        },2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    private void stopPlay() {
        mHandler.removeCallbacksAndMessages(null);
    }
}

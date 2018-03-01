package com.itheima.newfeature;

import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private Handler mHandler = new Handler();
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;

    private int[] mStraggeredIcons = new int[]{R.mipmap.p1, R.mipmap.p2, R.mipmap.p3, R
            .mipmap.p4, R.mipmap.p5, R.mipmap.p6, R.mipmap.p7, R.mipmap.p8, R.mipmap.p9, R
            .mipmap.p10, R.mipmap.p11, R.mipmap.p12, R.mipmap.p13, R.mipmap.p14, R.mipmap
            .p15, R.mipmap.p16, R.mipmap.p17, R.mipmap.p18, R.mipmap.p19, R.mipmap.p20, R
            .mipmap.p21, R.mipmap.p22, R.mipmap.p23, R.mipmap.p24, R.mipmap.p25, R.mipmap
            .p26, R.mipmap.p27, R.mipmap.p28, R.mipmap.p29, R.mipmap.p30, R.mipmap.p31, R
            .mipmap.p32, R.mipmap.p33, R.mipmap.p34, R.mipmap.p35, R.mipmap.p36, R.mipmap
            .p37, R.mipmap.p38, R.mipmap.p39, R.mipmap.p40, R.mipmap.p41, R.mipmap.p42, R
            .mipmap.p43, R.mipmap.p44};
    private List<DataBean> mDataBeanList = new ArrayList<>();
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mToolbar.setTitle("RecyclerView的使用");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initData();
        mRecyclerViewAdapter = new RecyclerViewAdapter(mDataBeanList, this);

        mRecyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String title, int position) {
                Toast.makeText(RecyclerViewActivity.this, title, Toast.LENGTH_SHORT).show();
            }
        });
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        /**
         * 不能设置颜色的资源id，只能设置颜色的真正的值
         */
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent),getResources().getColor(R.color.colorPrimary),Color.GREEN);

        mSwipeRefreshLayout.setOnRefreshListener(this);

    }

    private void initData() {
        for (int i = 0; i < mStraggeredIcons.length; i++) {
            DataBean dataBean = new DataBean();
            dataBean.imageId = mStraggeredIcons[i];
            dataBean.title = "美女"+i;
            mDataBeanList.add(dataBean);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recyclerview,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_recycler_listview_vertical:
                /**
                 * 参数3：是否倒置ListView方向
                 */
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
                mRecyclerView.setAdapter(mRecyclerViewAdapter);
                break;
            case R.id.menu_recycler_listview_horizontal:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
                mRecyclerView.setAdapter(new RecyclerViewAdapter(mDataBeanList,this));
                break;
            case R.id.menu_recycler_gridview_vertical:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this,2,LinearLayoutManager.VERTICAL,false));
                mRecyclerView.setAdapter(new RecyclerViewAdapter(mDataBeanList,this));
                break;
            case R.id.menu_recycler_gridview_horizontal:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this,2,LinearLayoutManager.HORIZONTAL,false));
                mRecyclerView.setAdapter(new RecyclerViewAdapter(mDataBeanList,this));
                break;
            case R.id.menu_recycler_stagger_vertical:
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                mRecyclerView.setAdapter(new RecyclerViewAdapter(mDataBeanList,this));
                break;
            case R.id.menu_recycler_stagger_horizontal:
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.HORIZONTAL));
                mRecyclerView.setAdapter(new RecyclerViewAdapter(mDataBeanList,this));
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onRefresh() {
        //更新数据
        //更新完了，隐藏刷新
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        },3000);

    }
}

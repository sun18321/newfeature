package com.itheima.newfeature;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 作者： itheima
 * 时间：2016-10-13 15:11
 * 网址：http://www.itheima.com
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<DataBean> mDataBeanList;
    private Context mContext;

    public RecyclerViewAdapter(List<DataBean> dataBeen, Context context) {
        mDataBeanList = dataBeen;
        mContext = context;
    }

    @Override
    public int getItemCount() {
        return mDataBeanList ==null?0: mDataBeanList.size();
    }

    /**
     *
     * @param parent 其实就是绑定的RecyclerView对象
     * @param viewType
     * @return
     * 调用的次数：一般界面最多能显示多少个条目就调用多少次
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //需要创建ViewHolder，VIewHolder里面包含了ItemView
        /**
         * 参数3：true : 直接将list_item_recycler添加到parent对象上，并且返回parent
         *        false:仅仅将list_item_recycler转换为View然后返回view
         *
         */
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_recycler, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    /**
     * @param holder
     * @param position
     *  没出来一个新的条目就调用一次，用于给ViewHolder更新数据数据
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final DataBean dataBean = mDataBeanList.get(position);
        holder.mTv.setText(dataBean.title);
//        holder.mIv.setImageResource(dataBean.imageId);
        Glide.with(mContext).load(dataBean.imageId).crossFade().into(holder.mIv);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener!=null){
                    mOnItemClickListener.onItemClick(dataBean.title,position);
                }
            }
        });
    }

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(String title,int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }



    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView mIv;
        TextView mTv;
        public MyViewHolder(View itemView) {
            super(itemView);
            //需要将itemView的子控件找出来，作为MyViewHolder的成员变量
            mIv = (ImageView) itemView.findViewById(R.id.iv);
            mTv = (TextView) itemView.findViewById(R.id.tv);
        }
    }



}


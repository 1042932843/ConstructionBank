package com.clpays.tianfugou.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.clpays.tianfugou.Adapter.helper.OnItemListener;
import com.clpays.tianfugou.App.app;
import com.clpays.tianfugou.Entity.Pay.payItem;
import com.clpays.tianfugou.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dsy on 2017/12/27.
 */
public class RvItemAdapter extends RecyclerView.Adapter<RvItemAdapter.MyViewHolder> {

    private Context mContext;
    private List<payItem> mDataList;
    private LayoutInflater mInflater;
    private OnItemListener mOnItemListener;

    public RvItemAdapter(Context context, List<payItem> dataList) {
        this.mContext = context;
        this.mDataList = dataList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = mInflater.inflate(R.layout.activity_paylist_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(rootView);
        return myViewHolder;
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.setIsRecyclable(false);//强制关闭复用
        payItem item = mDataList.get(position);
        holder.fee.setText(item.getFee());
        holder.comment.setText(item.getComment());
        holder.month.setText(item.getMonth());
        if(item.isPaid()){
            holder.Paied.setHint("已支付");
        }else{
            holder.Paied.setText("待支付");
            holder.Paied.setTextColor(ContextCompat.getColor(mContext,R.color.theme_color_primary));
        }
        switch (item.getType()){
            case "房租费":
                Glide.with(mContext).load(R.drawable.s_fangzu).into(holder.img);
                break;
            case "物业费":
                Glide.with(mContext).load(R.drawable.s_wuye).into(holder.img);
                break;
            case "电费":
                Glide.with(mContext).load(R.drawable.s_dianfei).into(holder.img);
                break;
            case "水费":
                Glide.with(mContext).load(R.drawable.s_shuifei).into(holder.img);
                break;
            case "网费":
                Glide.with(mContext).load(R.drawable.s_wangfei).into(holder.img);
                break;
            case "气费":
                Glide.with(mContext).load(R.drawable.s_qifei).into(holder.img);
                break;
            case "其他费用":
                Glide.with(mContext).load(R.drawable.s_qita).into(holder.img);
                break;
        }


        holder.cbSelect.setChecked(item.isSelect);
        holder.cbSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.isSelect = holder.cbSelect.isChecked();
                mOnItemListener.checkBoxClick(position);
            }
        });

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemListener.onItemClick(view,position);
            }
        });


    }

    public void setOnItemListener(OnItemListener onItemListener) {
        this.mOnItemListener = onItemListener;
    }

    /**
     * ViewHolder
     */
    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout)
        RelativeLayout layout;

        @BindView(R.id.img)
        ImageView img;

        @BindView(R.id.num)
        TextView fee;
        @BindView(R.id.time)
        TextView month;
        @BindView(R.id.userid)
        TextView comment;

        @BindView(R.id.type)
        TextView Paied;

        @BindView(R.id.cb_select)
        CheckBox cbSelect;



        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public void remove(payItem itemModel){
        mDataList.remove(itemModel);
    }

    public payItem getItem(int pos){
        return mDataList.get(pos);
    }
}

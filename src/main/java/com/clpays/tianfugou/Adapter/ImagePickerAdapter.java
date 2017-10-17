package com.clpays.tianfugou.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.clpays.tianfugou.R;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;

import java.util.ArrayList;
import java.util.List;

import com.clpays.tianfugou.Module.Major.Authentication.Fragment.CertificateInfoFragment;
/**
 * Name: ImagePickerAdapter
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //多选图片时使用
 * Date: 2017-09-22 13:14
 */

public class ImagePickerAdapter extends RecyclerView.Adapter<ImagePickerAdapter.SelectedPicViewHolder> {

    private Context mContext;
    private List<ImageItem> mData;
    private LayoutInflater mInflater;
    private OnRecyclerViewItemClickListener listener;
    private boolean isAdded;   //是否额外添加了最后一个图片

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int clickPosition,int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    public void setImages(List<ImageItem> data) {
        mData = new ArrayList<>(data);
        notifyDataSetChanged();
    }

    public ArrayList<ImageItem> getImages(int choice) {
        ArrayList<ImageItem> imageItems=new ArrayList<>();
        imageItems.add(mData.get(choice));
      return imageItems;
    }

    public ImagePickerAdapter(Context mContext, List<ImageItem> data) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        setImages(data);
    }

    @Override
    public SelectedPicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SelectedPicViewHolder(mInflater.inflate(R.layout.list_imagepicker_bg, parent, false));
    }

    @Override
    public void onBindViewHolder(SelectedPicViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class SelectedPicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView iv_img;
        private TextView Type;
        private int clickPosition;
        private int Position;

        public SelectedPicViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            Type=(TextView)itemView.findViewById(R.id.type);
        }

        public void bind(int position) {
            //设置条目的点击事件
            itemView.setOnClickListener(this);
            //根据条目位置设置图片

            if(mData.size()>0){
                    ImageItem item = mData.get(position);
                    if(!item.path.isEmpty()){
                        ImagePicker.getInstance().getImageLoader().displayImage((Activity) mContext, item.path, iv_img, 0, 0);
                        clickPosition = position;
                        Position=position;
                    }else{
                        iv_img.setImageResource(android.R.drawable.ic_menu_add);
                        Type.setText(item.type);
                        clickPosition = CertificateInfoFragment.IMAGE_ITEM_ADD;
                        Position=position;
                    }
            }

        }

        @Override
        public void onClick(View v) {
            if (listener != null) listener.onItemClick(v, clickPosition,Position);
        }
    }
}
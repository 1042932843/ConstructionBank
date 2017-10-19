package com.clpays.tianfugou.Adapter.PackagesAdapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.clpays.tianfugou.Entity.PackageChoice.SecondBean;
import com.clpays.tianfugou.R;

import java.util.ArrayList;


/**
 * Name: ChildAdapter
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //二级菜单适配器
 * Date: 2017-10-13 02:51
 */
public class ChildAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private ArrayList<SecondBean> mDatas;

    final Html.ImageGetter imageGetter = new Html.ImageGetter() {

        public Drawable getDrawable(String source) {
            Drawable drawable=null;
            int rId=Integer.parseInt(source);
            drawable=mContext.getResources().getDrawable(rId);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            return drawable;};
    };

    int mPosition;
    public ChildAdapter(Context context, ArrayList<SecondBean> data, int pos){
        this.mContext = context;
        this.mDatas = data;
        this.mPosition = pos;
    }
    @Override
    public int getGroupCount() {
        return mDatas!= null?mDatas.size():0;
    }

    @Override
    public int getChildrenCount(int childPosition) {
        return mDatas.get(childPosition).getSecondBean()!=null
                ?mDatas.get(childPosition).getSecondBean().size():0;
    }

    @Override
    public Object getGroup(int parentPosition) {
        return mDatas.get(parentPosition);
    }

    @Override
    public Object getChild(int parentPosition, int childPosition) {
        return mDatas.get(parentPosition).getSecondBean().get(childPosition);
    }

    @Override
    public long getGroupId(int parentPosition) {
        return parentPosition;
    }

    @Override
    public long getChildId(int parentPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * 主菜单布局
     * @param parentPosition
     * @param isExpanded  是否展开
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getGroupView(int parentPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.layout_list_package_header2,null);
            holder = new ViewHolder();
            holder.title = (TextView)view.findViewById(R.id.title);
            holder.arrow = (ImageView)view.findViewById(R.id.arrow);

            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        //区分箭头往上还是
        if(isExpanded){
            holder.arrow.setImageResource(R.drawable.up_hui);
        }else{
            holder.arrow.setImageResource(R.drawable.down_hui);
        }

        holder.title.setText(mDatas.get(parentPosition).getTitle());
        return view;
    }
    class ViewHolder {

        private TextView title;
        private ImageView arrow;

    }
    /**
     * 子菜单布局
     * @param parentPosition
     * @param childPosition
     * @param isExpandabled
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getChildView(int parentPosition, int childPosition, boolean isExpandabled, View view, ViewGroup viewGroup) {
        ChildHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(
                    R.layout.layout_list_package_child, null);
            holder = new ChildHolder();
            holder.childChildTV = (TextView) view.findViewById(R.id.text);

                    view.setTag(holder);
        } else {
            holder = (ChildHolder) view.getTag();
        }
        holder.childChildTV.setText(Html.fromHtml(mDatas.get(parentPosition).getSecondBean().get(childPosition).getTitle(), imageGetter, null));
        return view;
    }
    class ChildHolder {

        private TextView childChildTV;
    }
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

}

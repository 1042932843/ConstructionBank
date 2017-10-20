package com.clpays.tianfugou.Adapter.PackagesAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;


import com.clpays.tianfugou.Design.myExpandableListview.CustomExpandableListView;
import com.clpays.tianfugou.Design.myScrollView.myScrollView;
import com.clpays.tianfugou.Entity.PackageChoice.FirstBean;
import com.clpays.tianfugou.Entity.PackageChoice.SecondBean;
import com.clpays.tianfugou.R;

import java.util.ArrayList;

import butterknife.OnClick;

/**
 * Name: MainAdapter
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //主适配器
 * Date: 2017-10-13 02:52
 */
public class MainAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private ArrayList<FirstBean> mData;
    ViewHolder holder = null;
    myScrollView scrollView;

    public int getPackage() {
        return Package;
    }

    int Package;
    public MainAdapter(Context context, ArrayList<FirstBean> data,myScrollView scrollView){
        this.mContext = context;
        this.mData = data;
        this.scrollView=scrollView;
    }
    @Override
    public int getGroupCount() {
        return mData != null?mData.size():0;
    }

    @Override
    public int getChildrenCount(int parentPosition) {
        return mData.get(parentPosition).getFirstData().size();
    }

    @Override
    public Object getGroup(int parentPosition) {
        return mData.get(parentPosition);
    }

    @Override
    public SecondBean getChild(int parentPosition, int childPosition) {
        return mData.get(parentPosition).getFirstData().get(childPosition);
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
     *  第一级菜单适配器布局
     * @param parentPosition
     * @param isExpanded
     *
     * @param convertView
     * @param viewGroup
     * @return
     */
    @Override
    public View getGroupView(int parentPosition, boolean isExpanded, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.layout_list_package_header, null);
            holder = new ViewHolder();
            holder.choice = (RadioButton) convertView.findViewById(R.id.RadioButton);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(isExpanded){
            holder.choice.setChecked(true);
        }else{
            holder.choice.setChecked(false);
        }

        holder.title.setText(mData.get(parentPosition).getTitle());


        return convertView;
    }



    class ViewHolder{
        private TextView title;
        private RadioButton choice;
    }

    public CustomExpandableListView getExpandableListView() {
        CustomExpandableListView mExpandableListView = new CustomExpandableListView(
                mContext);
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mExpandableListView.setLayoutParams(lp);
        mExpandableListView.setDividerHeight(0);// 取消group项的分割线
        mExpandableListView.setChildDivider(null);// 取消child项的分割线
        mExpandableListView.setGroupIndicator(null);// 取消展开折叠的指示图标
        return mExpandableListView;
    }
    /**
     * 第二级菜单适配
     * @param parentPosition
     * @param childPosition
     * @param isExpanded
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getChildView(final int parentPosition, final int childPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
        final CustomExpandableListView childListView = getExpandableListView();
        //获取子菜单的数据
        final ArrayList<SecondBean> childData = new ArrayList<SecondBean>();
        final SecondBean bean = getChild(parentPosition,childPosition);
        childData.add(bean);
        ChildAdapter adapter = new ChildAdapter(mContext,childData,parentPosition);
        childListView.setAdapter(adapter);

        /**
         * 点击最小级菜单，调用该方法
         * */
        childListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView arg0, View arg1,
                                        int groupIndex, int childIndex, long arg4) {
                if(mListener != null){
                    mListener.onclick(parentPosition,childPosition, childIndex);

                }
                return false;
            }
        });

        //滑动冲突的解决方案
        childListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_MOVE) {
                    int top = childListView.getChildAt(0).getTop();
                    float nowY = event.getY();
                    scrollView.requestDisallowInterceptTouchEvent(true);
                }

                return false;
            }
        });


        // 监听listview滚到最底部
        childListView.setOnScrollListener(new ExpandableListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    // 当不滚动时
                    case ExpandableListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 判断滚动到底部

                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
            }
        });
        /**
         * 在这里对二级菜单的点击事件进行操作
         */
        childListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int Position, long id) {
//                if(isClick){
//                    holder.mUpImg.setImageResource(R.drawable.dowm);
//                    isClick = false;
//                }else{
//                    holder.mUpImg.setImageResource(R.drawable.up);
//                    isClick = true;
//                }
                Log.e("Xxx","恭喜你,点击了"+parentPosition+"childpos>>>"+childPosition);
                return false;
            }
        });
        return childListView;
    }
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    /*接口回调*/
    public interface OnExpandClickListener{
        void onclick(int parentPosition, int childPosition, int childIndex);
    }


    OnExpandClickListener mListener;
    public void setOnChildListener(OnExpandClickListener listener){
        this.mListener = listener;
    }
}

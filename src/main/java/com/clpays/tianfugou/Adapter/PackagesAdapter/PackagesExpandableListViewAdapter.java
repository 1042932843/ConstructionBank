package com.clpays.tianfugou.Adapter.PackagesAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

import com.clpays.tianfugou.Entity.PackageChoice.NewPackagesBean;
import com.clpays.tianfugou.Entity.RegionalChoice.Title;
import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.ToastUtil;

/**
 * Name: PackagesExpandableListViewAdapter
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //多层展开PackagesExpandableListViewAdapter
 * Date: 2017-10-31 16:05
 */

public class PackagesExpandableListViewAdapter extends BaseExpandableListAdapter {
    private List<NewPackagesBean> dataTitleGroups;
    private Context mcontext;
    public PackagesExpandableListViewAdapter(List<NewPackagesBean> data, Context context) {
        dataTitleGroups=data;
        mcontext=context;
    }

    @Override
    public int getGroupCount() {
        if (dataTitleGroups == null||dataTitleGroups.size()<=0) {
            ToastUtil.ShortToast("木有数据");
            return 0;
        }
        return dataTitleGroups.size();//父项的数量
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(dataTitleGroups.get(groupPosition).getBeenList().size()<=0){
            ToastUtil.ShortToast("木有数据");
            return 0;
        }
        return dataTitleGroups.get(groupPosition).getBeenList().size();//  获得某个父项的子项数目
    }

    @Override
    public Object getGroup(int groupPosition) {
        return dataTitleGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return dataTitleGroups.get(groupPosition).getBeenList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mcontext);
            convertView = inflater.inflate(R.layout.layout_list_regionalchoiceheader, null);
        }
        convertView.setTag(R.layout.layout_list_regionalchoiceheader, groupPosition);
        TextView title = (TextView) convertView.findViewById(R.id.title);

        title.setText(dataTitleGroups.get(groupPosition).getTitle());
        return convertView;//  获得父项显示的view
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mcontext);
            convertView = inflater.inflate(R.layout.layout_list_regionalchoiceitem, null);
        }
        convertView.setTag(R.layout.layout_list_timeitem, groupPosition);

        if("".equals(dataTitleGroups.get(groupPosition).getBeenList().get(childPosition).getTitle())){

        }else{

        }


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

package com.clpays.tianfugou.Adapter.PackagesAdapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import com.clpays.tianfugou.Design.textViewHtml.MImageGetter;
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
            //ToastUtil.ShortToast("木有数据");
            return 0;
        }
        return dataTitleGroups.size();//父项的数量
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(dataTitleGroups.get(groupPosition).getBeenList().size()<=0){
            //ToastUtil.ShortToast("木有数据");
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
            convertView = inflater.inflate(R.layout.package_title_layout, null);
        }
        convertView.setTag(R.layout.package_title_layout, groupPosition);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        CheckBox checkBox=(CheckBox) convertView.findViewById(R.id.checkbox);
        boolean isc=dataTitleGroups.get(groupPosition).isChoice();
        checkBox.setChecked(isc);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //ToastUtil.ShortToast(isChecked+"");
                //对列表本身进行操作
                if(!buttonView.isPressed())return;  //加这一条，否则当我setChecked()时会触发此listener
                dataTitleGroups.get(groupPosition).setChoice(isChecked);
                String r=dataTitleGroups.get(groupPosition).getRelated();
                if(!r.isEmpty()){
                    int size=dataTitleGroups.size();
                    int a=Integer.parseInt(dataTitleGroups.get(groupPosition).getRelated());
                    dataTitleGroups.get(a-1).setChoice(isChecked);
                    notifyDataSetChanged();

                }
            }
        });
        title.setText(dataTitleGroups.get(groupPosition).getTitle());
        return convertView;//  获得父项显示的view
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mcontext);
            convertView = inflater.inflate(R.layout.package_item_layout, null);
        }
        convertView.setTag(R.layout.package_item_layout, groupPosition);
        TextView content = (TextView) convertView.findViewById(R.id.content);
        String html=dataTitleGroups.get(groupPosition).getBeenList().get(childPosition).getTitle();
        content.setText(Html.fromHtml(html, new MImageGetter(content, mcontext), null));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

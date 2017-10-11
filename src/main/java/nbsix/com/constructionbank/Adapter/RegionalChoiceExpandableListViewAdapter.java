package nbsix.com.constructionbank.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import nbsix.com.constructionbank.App.app;
import nbsix.com.constructionbank.Entity.GeneralJournal.dataDayGroup;
import nbsix.com.constructionbank.Entity.RegionalChoice.Title;
import nbsix.com.constructionbank.R;
import nbsix.com.constructionbank.Utils.ToastUtil;

/**
 * Name: RunningAccountDetailsExpandableListViewAdapter
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //多层展开adapter
 * Date: 2017-09-13 14:51
 */

public class RegionalChoiceExpandableListViewAdapter extends BaseExpandableListAdapter {
    private List<Title> dataTitleGroups;
    private Context mcontext;
    public RegionalChoiceExpandableListViewAdapter(List<Title> data, Context context) {
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
        if(dataTitleGroups.get(groupPosition).getItems().size()<=0){
            ToastUtil.ShortToast("当日木有数据");
            return 0;
        }
        return dataTitleGroups.get(groupPosition).getItems().size();//  获得某个父项的子项数目
    }

    @Override
    public Object getGroup(int groupPosition) {
        return dataTitleGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return dataTitleGroups.get(groupPosition).getItems().get(childPosition);
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

        if("".equals(dataTitleGroups.get(groupPosition).getItems().get(childPosition).getName())){

        }else{

        }


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

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
import nbsix.com.constructionbank.R;
import nbsix.com.constructionbank.Utils.ToastUtil;

/**
 * Name: RunningAccountDetailsExpandableListViewAdapter
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //多层展开adapter
 * Date: 2017-09-13 14:51
 */

public class RunningAccountDetailsExpandableListViewAdapter extends BaseExpandableListAdapter {
    private List<dataDayGroup> dataDayGroups;
    private Context mcontext;
    public RunningAccountDetailsExpandableListViewAdapter(List<dataDayGroup> data, Context context) {
        dataDayGroups=data;
        mcontext=context;
    }

    @Override
    public int getGroupCount() {
        if (dataDayGroups == null||dataDayGroups.size()<=0) {
            ToastUtil.ShortToast("木有数据");
            return 0;
        }
        return dataDayGroups.size();//父项的数量
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(dataDayGroups.get(groupPosition).getList().size()<=0){
            ToastUtil.ShortToast("当日木有数据");
            return 0;
        }
        return dataDayGroups.get(groupPosition).getList().size();//  获得某个父项的子项数目
    }

    @Override
    public Object getGroup(int groupPosition) {
        return dataDayGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return dataDayGroups.get(groupPosition).getList().get(childPosition);
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
            convertView = inflater.inflate(R.layout.layout_list_timeheader, null);
        }
        convertView.setTag(R.layout.layout_list_timeheader, groupPosition);
        TextView date = (TextView) convertView.findViewById(R.id.date);
        TextView total = (TextView) convertView.findViewById(R.id.total);
        date.setText(dataDayGroups.get(groupPosition).getDate());
        total.setText(dataDayGroups.get(groupPosition).getTotal());
        return convertView;//  获得父项显示的view
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mcontext);
            convertView = inflater.inflate(R.layout.layout_list_timeitem, null);
        }
        convertView.setTag(R.layout.layout_list_timeitem, groupPosition);
        ImageView img=(ImageView)convertView.findViewById(R.id.img);
        if("".equals(dataDayGroups.get(groupPosition).getList().get(childPosition).getPic())){
            Glide.with(mcontext).load(R.drawable.zhangdan).apply(app.optionsNormal).into(img);
        }else{
            Glide.with(mcontext).load(dataDayGroups.get(groupPosition).getList().get(childPosition).getPic()).apply(app.optionsNormal).into(img);
        }
        TextView num=(TextView)convertView.findViewById(R.id.num);
        num.setText(dataDayGroups.get(groupPosition).getList().get(childPosition).getTotal());
        TextView id=(TextView)convertView.findViewById(R.id.userid);
        id.setText(dataDayGroups.get(groupPosition).getList().get(childPosition).getName());
        TextView time=(TextView)convertView.findViewById(R.id.time);
        time.setText(dataDayGroups.get(groupPosition).getList().get(childPosition).getTime());
        TextView type=(TextView)convertView.findViewById(R.id.type);
        type.setText(dataDayGroups.get(groupPosition).getList().get(childPosition).getType());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

package com.clpays.tianfugou.Adapter.PackagesAdapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import com.clpays.tianfugou.App.app;
import com.clpays.tianfugou.Design.textViewHtml.MImageGetter;
import com.clpays.tianfugou.Entity.PackageChoice.NewPackagesBean;
import com.clpays.tianfugou.Entity.RegionalChoice.Title;
import com.clpays.tianfugou.Module.LoginRegister.LRpageActivity;
import com.clpays.tianfugou.Module.Major.Authentication.StartAuthenticationActivity;
import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.PreferenceUtil;
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
    private CompoundButton.OnCheckedChangeListener isnewListener;

    public boolean isnewbank() {
        return isnewbank;
    }

    public void setIsnewbank(boolean isnewbank) {
        this.isnewbank = isnewbank;
    }

    private boolean isnewbank=true;

    //自定义接口，用于回调按钮点击事件到Activity
    public interface IsNewListener{
        public void isnewListener(View v);
    }

    public PackagesExpandableListViewAdapter(List<NewPackagesBean> data, Context context,CompoundButton.OnCheckedChangeListener isnewListener) {
        dataTitleGroups=data;
        mcontext=context;
        this.isnewListener=isnewListener;

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
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        convertView = inflater.inflate(R.layout.package_title_layout, null);
        convertView.setTag(R.layout.package_title_layout, groupPosition);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        CheckBox checkBox=(CheckBox) convertView.findViewById(R.id.checkbox);
        TextView tip = (TextView) convertView.findViewById(R.id.tip);
        tip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder normalDialog =
                        new AlertDialog.Builder(mcontext);
                normalDialog.setIcon(R.mipmap.launcher);
                normalDialog.setTitle("提示");
                normalDialog.setMessage("所持营业执照是否初次开户？");
                normalDialog.setPositiveButton("初次开户",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                isnewbank=true;
                                tip.setHint("（初次开户）");
                            }
                        });
                normalDialog.setNegativeButton("非初次开户",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //...To-do
                                isnewbank=false;
                                tip.setHint("（非初次开户）");
                            }
                        });

                // 显示
                normalDialog.show();
            }
        });
        if(dataTitleGroups.get(groupPosition).getId().equals("3")){
            if(isnewbank){
                tip.setHint("（初次开户）");
            }else {
                tip.setHint("（非初次开户）");
            }
        }

        boolean isc=dataTitleGroups.get(groupPosition).isChoice();
        checkBox.setChecked(isc);
        if(dataTitleGroups.get(groupPosition).getRequired().equals("1")){
            checkBox.setChecked(true);
            checkBox.setEnabled(false);
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //ToastUtil.ShortToast(isChecked+"");

                //对列表本身进行操作
                if(!buttonView.isPressed())return;  //加这一条，否则当我setChecked()时会触发此listener
                if(dataTitleGroups.get(groupPosition).getId().equals("3")&&!isChecked){
                    final AlertDialog.Builder normalDialog =
                            new AlertDialog.Builder(mcontext);
                    normalDialog.setIcon(R.mipmap.launcher);
                    normalDialog.setTitle("提示");
                    normalDialog.setMessage("只有开立对公收款账户，才可获得无抵押信用贷款资格（最高200万元）。请您再次确认是否申请开立该账户？");
                    normalDialog.setPositiveButton("申请开立",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dataTitleGroups.get(groupPosition).setChoice(!isChecked);
                                    String r=dataTitleGroups.get(groupPosition).getRelated();
                                    /*if(!r.isEmpty()){
                                        int size=dataTitleGroups.size();
                                        int a=Integer.parseInt(dataTitleGroups.get(groupPosition).getRelated());
                                        dataTitleGroups.get(a-1).setChoice(isChecked);

                                    }*/
                                    notifyDataSetChanged();
                                    //...To-do
                                }
                            });
                    normalDialog.setNegativeButton("不申请",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //...To-do
                                    dataTitleGroups.get(groupPosition).setChoice(isChecked);

                                    String r=dataTitleGroups.get(groupPosition).getRelated();
                                    if(!r.isEmpty()){
                                       /*
                                        这段代码当时为了处理服务的关联关系而存在。如今它已经老了。
                                        int size=dataTitleGroups.size();
                                        int a=Integer.parseInt(dataTitleGroups.get(groupPosition).getRelated());
                                        dataTitleGroups.get(a-1).setChoice(isChecked);
                                        */
                                    }
                                    notifyDataSetChanged();
                                }
                            });

                    AlertDialog dialog=normalDialog.create();
                    //show必须在getButton之前，否则get为空
                    dialog.show();
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(mcontext,R.color.colorPrimary));
                    dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(mcontext,R.color.gray_light));
                    // 显示

                }else if(dataTitleGroups.get(groupPosition).getId().equals("2")&&isChecked) {
                    final AlertDialog.Builder normalDialog =
                            new AlertDialog.Builder(mcontext);
                    normalDialog.setIcon(R.mipmap.launcher);
                    normalDialog.setTitle("提示");
                    normalDialog.setMessage("所持营业执照是否初次开户？");
                    normalDialog.setPositiveButton("初次开户",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    isnewbank=true;
                                    tip.setHint("（初次开户）");
                                    dataTitleGroups.get(groupPosition).setChoice(isChecked);
                                    String r=dataTitleGroups.get(groupPosition).getRelated();
                                    if(!r.isEmpty()){
                                        /*
                                        这段代码当时为了处理服务的关联关系而存在。如今它已经老了。
                                        int size=dataTitleGroups.size();
                                        int a=Integer.parseInt(dataTitleGroups.get(groupPosition).getRelated());
                                        dataTitleGroups.get(a-1).setChoice(isChecked);
                                        notifyDataSetChanged();*/

                                    }
                                }
                            });
                    normalDialog.setNegativeButton("非初次开户",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //...To-do
                                    isnewbank=false;
                                    tip.setHint("（非初次开户）");
                                    dataTitleGroups.get(groupPosition).setChoice(isChecked);
                                    String r=dataTitleGroups.get(groupPosition).getRelated();
                                    if(!r.isEmpty()){
                                        /*
                                        这段代码当时为了处理服务的关联关系而存在。如今它已经老了。
                                        int size=dataTitleGroups.size();
                                        int a=Integer.parseInt(dataTitleGroups.get(groupPosition).getRelated());
                                        dataTitleGroups.get(a-1).setChoice(isChecked);
                                        notifyDataSetChanged();*/

                                    }
                                }
                            });

                    // 显示
                    normalDialog.show();
                }else{
                        dataTitleGroups.get(groupPosition).setChoice(isChecked);

                        String r=dataTitleGroups.get(groupPosition).getRelated();
                        if(!r.isEmpty()){
                            /*
                             这段代码当时为了处理服务的关联关系而存在。如今它已经老了。
                                        int size=dataTitleGroups.size();
                                        int a=Integer.parseInt(dataTitleGroups.get(groupPosition).getRelated());
                                        dataTitleGroups.get(a-1).setChoice(isChecked);
                                        notifyDataSetChanged();*/

                        }
                    }
                }

        });
        title.setText(dataTitleGroups.get(groupPosition).getTitle());
        return convertView;//  获得父项显示的view
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            /*if(dataTitleGroups.get(groupPosition).getBeenList().size()>1&&childPosition==0){
                LayoutInflater inflater = LayoutInflater.from(mcontext);
                convertView = inflater.inflate(R.layout.package_item2_layout, null);
                convertView.setTag(R.layout.package_item2_layout, groupPosition);
                CheckBox account = (CheckBox) convertView.findViewById(R.id.account);
                account.setChecked(isnewbank);
                account.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(!buttonView.isPressed())return;  //加这一条，否则当我setChecked()时会触发此listener
                        isnewbank=isChecked;
                    }
                });
            }else {
                LayoutInflater inflater = LayoutInflater.from(mcontext);
                convertView = inflater.inflate(R.layout.package_item_layout, null);
                convertView.setTag(R.layout.package_item_layout, groupPosition);
                TextView content = (TextView) convertView.findViewById(R.id.content);
                String html=dataTitleGroups.get(groupPosition).getBeenList().get(childPosition).getTitle();
                content.setText(Html.fromHtml(html, new MImageGetter(content, mcontext), null));
            }*/
        String html=dataTitleGroups.get(groupPosition).getBeenList().get(childPosition).getTitle();
            LayoutInflater inflater = LayoutInflater.from(mcontext);
            convertView = inflater.inflate(R.layout.package_item_layout, null);
            convertView.setTag(R.layout.package_item_layout, groupPosition);
            TextView content = (TextView) convertView.findViewById(R.id.content);
            content.setText(Html.fromHtml(html, new MImageGetter(content, mcontext), null));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

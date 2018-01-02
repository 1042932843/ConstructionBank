package com.clpays.tianfugou.Module.Major.QRGathering.Fragement;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.clpays.tianfugou.Adapter.RunningAccountDetailsExpandableListViewAdapter;
import com.clpays.tianfugou.Adapter.superAdapter.SuperViewHolder;
import com.clpays.tianfugou.Entity.GeneralJournal.QRcallbackItem;
import com.clpays.tianfugou.Entity.GeneralJournal.dataDay;
import com.clpays.tianfugou.Entity.GeneralJournal.dataDayGroup;
import com.clpays.tianfugou.Module.Base.BaseFragment;
import com.clpays.tianfugou.Network.RequestProperty;
import com.clpays.tianfugou.Network.RetrofitHelper;
import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.ImageBlurUtil;
import com.clpays.tianfugou.Utils.SystemBarHelper;
import com.clpays.tianfugou.Utils.ToastUtil;
import com.clpays.tianfugou.Utils.tools.isGetStringFromJson;
import com.clpays.tianfugou.Utils.tools.isJsonArray;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.jessewu.library.SuperAdapter;
import com.jessewu.library.paging.LoadDataListener;
import com.jessewu.library.paging.LoadDataStatus;
import com.jessewu.library.view.ViewHolder;
import com.jessewu.library.widget.SimpleFooterBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class GeneralJournalFragment extends BaseFragment {
    //private RunningAccountDetailsExpandableListViewAdapter adapter;
    //List<dataDayGroup> mydataDayGroups=new ArrayList<>();
    List<QRcallbackItem> mData;
    SuperAdapter<QRcallbackItem> mAdapter;

// 分页加载数据的起始页
    public  int START_PAGE = -1;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_list)
    RecyclerView recyclerView;

   /* @BindView(R.id.expandableListView)
    ExpandableListView expandableListView;*/

    @OnClick(R.id.back)
    public void back(){
        getFragmentManager().popBackStack();
    }

    public static GeneralJournalFragment newInstance() {

        return new GeneralJournalFragment();
    }

    @Override
    public void initRecyclerView(){

    }
    Gson gson ;
    @Override
    public void loadData(){

        /*List<dataDayGroup> dataDayGroups=new ArrayList<>();
        dataDayGroup dataDayGroup1=new dataDayGroup();
        List<dataDay> dataDays=new ArrayList<>();
        for(int i=0;i<5;i++){
            dataDay day=new dataDay();
            day.setTotal("300.00");
            day.setName("段师傅"+i);
            day.setPic("");
            day.setType("收款");
            day.setTime("13:20");
            dataDays.add(day);
        }
        dataDayGroup1.setList(dataDays);
        dataDayGroup1.setDate("6日-星期三");
        dataDayGroup1.setTotal("1500.00");

        dataDayGroup dataDayGroup2=new dataDayGroup();
        List<dataDay> dataDays2=new ArrayList<>();
        for(int i=0;i<3;i++){
            dataDay day=new dataDay();
            day.setTotal("5500.00");
            day.setName("重庆大酒店"+i);
            day.setPic("");
            day.setTime("12:15");
            day.setType("收款");
            dataDays2.add(day);
        }
        dataDayGroup2.setList(dataDays2);
        dataDayGroup2.setDate("5日-星期二");
        dataDayGroup2.setTotal("16500.00");

        dataDayGroup dataDayGroup3=new dataDayGroup();
        List<dataDay> dataDays3=new ArrayList<>();
        for(int i=0;i<9;i++){
            dataDay day=new dataDay();
            day.setTotal("600.00");
            day.setName("司机小章"+i);
            day.setPic("");
            day.setTime("06:30");
            day.setType("收款");
            dataDays3.add(day);
        }
        dataDayGroup3.setList(dataDays3);
        dataDayGroup3.setDate("4日-星期一");
        dataDayGroup3.setTotal("5400.00");

        dataDayGroups.add(dataDayGroup1);
        dataDayGroups.add(dataDayGroup2);
        dataDayGroups.add(dataDayGroup3);
        mydataDayGroups.clear();
        //mydataDayGroups.addAll(dataDayGroups);隐藏测试数据
        adapter=new RunningAccountDetailsExpandableListViewAdapter(mydataDayGroups,getContext());
        expandableListView.setAdapter(adapter);
        for(int i = 0; i < adapter.getGroupCount(); i++){
            expandableListView.expandGroup(i);
        }*/

    }


    public void getData(){

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_generaljournal;
    }

    @Override
    public void finishCreateView(Bundle state) {
        SystemBarHelper.setHeightAndPadding(getContext(), toolbar);
        gson= new Gson();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter= new SuperAdapter<QRcallbackItem>(R.layout.layout_list_timeitem){

            @Override
            public void bindView(ViewHolder viewHolder, QRcallbackItem qRcallbackItem, int i) {
                viewHolder.<TextView>getView(R.id.num).setText(qRcallbackItem.getAmount()+"元");
                if(Double.valueOf(qRcallbackItem.getAmount())>0){
                    viewHolder.<TextView>getView(R.id.type).setText("收款");
                    viewHolder.<TextView>getView(R.id.type).setTextColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                }else{
                    viewHolder.<TextView>getView(R.id.type).setText("付款");
                    viewHolder.<TextView>getView(R.id.type).setTextColor(ContextCompat.getColor(getContext(),R.color.red));
                }
                viewHolder.<TextView>getView(R.id.time).setText(qRcallbackItem.getTs());
                viewHolder.<ImageView>getView(R.id.img).setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.s_qita));
            }
        };

        mAdapter.addFooter(new SimpleFooterBuilder("已经滑动到底部","正在加载数据中","加载数据失败","没有更多了"));
        recyclerView.setAdapter(mAdapter);
        // 实现加载数据监听器

        LoadDataListener listener = new LoadDataListener() {

            @Override
            public void onLoadingData(final int loadPage, final LoadDataStatus loadDataStatus) {
                JsonObject obj= RequestProperty.CreateTokenJsonObjectBody();//带了Token的
                obj.addProperty("page",loadPage);
                RetrofitHelper.getQRAPI()
                        .fetchallorder(obj)
                        .compose(GeneralJournalFragment.this.bindToLifecycle())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(bean -> {
                            String a = bean.string();
                            String message= isGetStringFromJson.handleData("message",a);
                            if ("true".equals(isGetStringFromJson.handleData("success", a))) {
                                JsonArray array= isJsonArray.handleData("data",a);
                                List<QRcallbackItem> qRcallbackItems=gson.fromJson(array,
                                        new TypeToken<List<QRcallbackItem>>() {
                                        }.getType());
                                if(qRcallbackItems.size() == 0){
                                    loadDataStatus.onNoMoreData();
                                }else{
                                    loadDataStatus.onSuccess(qRcallbackItems);
                                }
                            }else{
                                ToastUtil.ShortToast(message);
                                loadDataStatus.onFailure(message);
                            }

                        }, throwable -> {
                            loadDataStatus.onFailure("请求出错");
                        });

            }
        };
        // 设置分页加载数据
        mAdapter.clearData();
        mAdapter.setPaginationData(START_PAGE, listener);

    }


}

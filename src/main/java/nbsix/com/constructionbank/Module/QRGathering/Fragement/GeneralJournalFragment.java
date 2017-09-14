package nbsix.com.constructionbank.Module.QRGathering.Fragement;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import nbsix.com.constructionbank.Adapter.dsyExpandableListViewAdapter;
import nbsix.com.constructionbank.Entity.GeneralJournal.dataDay;
import nbsix.com.constructionbank.Entity.GeneralJournal.dataDayGroup;
import nbsix.com.constructionbank.Module.Base.BaseFragment;
import nbsix.com.constructionbank.R;
import nbsix.com.constructionbank.Utils.SystemBarHelper;


public class GeneralJournalFragment extends BaseFragment {
    private dsyExpandableListViewAdapter adapter;
    List<dataDayGroup> mydataDayGroups=new ArrayList<>();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.expandableListView)
    ExpandableListView expandableListView;

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

    @Override
    public void loadData(){
        List<dataDayGroup> dataDayGroups=new ArrayList<>();
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
        mydataDayGroups.addAll(dataDayGroups);
        adapter=new dsyExpandableListViewAdapter(mydataDayGroups,getContext());
        expandableListView.setAdapter(adapter);
        for(int i = 0; i < adapter.getGroupCount(); i++){
            expandableListView.expandGroup(i);
        }


    }



    @Override
    public int getLayoutResId() {
        return R.layout.fragment_generaljournal;
    }

    @Override
    public void finishCreateView(Bundle state) {
        SystemBarHelper.setHeightAndPadding(getContext(), toolbar);
        loadData();
    }


}

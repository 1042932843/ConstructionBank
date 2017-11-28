package com.clpays.tianfugou.Module.Major.Credit.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.clpays.tianfugou.Adapter.CreditAdapter;
import com.clpays.tianfugou.Adapter.superAdapter.OnItemClickListener;
import com.clpays.tianfugou.Entity.Credit.CreditType;
import com.clpays.tianfugou.Module.Base.BaseFragment;
import com.clpays.tianfugou.Module.LoginRegister.LRpageActivity;
import com.clpays.tianfugou.Module.Message.MessageActivity;
import com.clpays.tianfugou.Network.RequestProperty;
import com.clpays.tianfugou.Network.RetrofitHelper;
import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.PreferenceUtil;
import com.clpays.tianfugou.Utils.SystemBarHelper;
import com.clpays.tianfugou.Utils.UserState;
import com.clpays.tianfugou.Utils.tools.isGetStringFromJson;
import com.clpays.tianfugou.Utils.tools.isJsonArray;
import com.clpays.tianfugou.Utils.tools.isJsonObj;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class CreditTypeFragment extends BaseFragment {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    CreditAdapter adapter;
    List<CreditType> creditTypeList;

    public static CreditTypeFragment newInstance() {
        return new CreditTypeFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        creditTypeList=new ArrayList<>();

    }

    @OnClick(R.id.back)
    public void back(){
        getActivity().finish();
    }

    @Override
    public void initRecyclerView(){
        adapter=new CreditAdapter(getContext(),creditTypeList,R.layout.credit_type_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                EventBus.getDefault().post(creditTypeList.get(position));
            }
        });
    }
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_credit_type;
    }

    @Override
    public void finishCreateView(Bundle state) {
        SystemBarHelper.setHeightAndPadding(getContext(), toolbar);
        initRecyclerView();
        getCreditType();
    }



    @Override
    public void onHiddenChanged(boolean hidden) {
        if(hidden){

        }else{
            lazyLoad();
        }
    }

    protected void lazyLoad() {
        getCreditType();
    }

    public void getCreditType(){
        JsonObject obj= RequestProperty.CreateTokenJsonObjectBody();//带了Token的
        RetrofitHelper.getCreditAPI()
                .fetchloan(obj)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    String a = bean.string();
                    //{"success":true,"message":"","data":{"loan":[{"id":"1","name":"\u62b5\u62bc\u5feb\u8d37\uff0c\u6709\u623f\u5c31\u8d37\uff01\u6211\u8981\u7533\u8bf7","intro":"<h3>\u7ebf\u4e0a\u5b9e\u65f6\u8bc4\u4f30\u62b5\u62bc\u7269\u4ef7\u503c\uff0c\u5efa\u884c\u672a\u8fd8\u6e05\u8d37\u6b3e\u7684\u6309\u63ed\u623f\u4e5f\u53ef\u4ee5\u62b5\u62bc\u7684\u623f\u4ea7\u62b5\u62bc\u8d37\u6b3e\uff0c\u4f4f\u5b85\u3001\u522b\u5885\u3001\u4e34\u8857\u5546\u94fa\u90fd\u80fd\u8d37\uff01</h3>\r\n<p>\uff08\u4e00\uff09\u529e\u7406\u6761\u4ef6\uff08\u9700\u540c\u65f6\u6ee1\u8db3\uff09\uff1a</p>\r\n1.\u7ecf\u5de5\u5546\u884c\u653f\u7ba1\u7406\u673a\u5173\u6838\u51c6\u767b\u8bb0\u7684\u4f01\u4e1a\u53ca\u4e2a\u4f53\u5de5\u5546\u6237\uff1b<br/> \r\n2.\u8fd1\u4e09\u5e74\u5728\u4e2d\u56fd\u4eba\u6c11\u94f6\u884c\u4f01\u4e1a\u5f81\u4fe1\u7cfb\u7edf\u65e0\u4e0d\u826f\u4fe1\u7528\u8bb0\u5f55\u3002<br/> \r\n<p><b>\u4f01\u4e1a\u4e3b\u3001\u5176\u914d\u5076\uff08\u5982\u6709\uff0c\u4e0b\u540c\uff09\u53ca\u5176\u5b9e\u9645\u63a7\u5236\u7684\u5168\u90e8\u4f01\u4e1a\u987b\u6ee1\u8db3\u4ee5\u4e0b\u6761\u4ef6\uff1a</b></p>\r\n1.\u5e74\u9f84\u6ee118\u5468\u5c81\uff0c\u4e14\u4e0d\u8d85\u8fc765\u5468\u5c81\uff08\u4e0d\u542b\uff09\uff1b\u4e14\u540c\u610f\u4f5c\u4e3a\u5171\u540c\u501f\u6b3e\u4eba\u3002<br/> \r\n2.\u4e2a\u4eba\u4fe1\u7528\u6b63\u5e38\uff1a\u8fd1\u4e24\u5e74\u5728\u4e2d\u56fd\u4eba\u6c11\u94f6\u884c\u4e2a\u4eba\u5f81\u4fe1\u7cfb\u7edf\u4e2a\u4eba\u903e\u671f\u6216\u6b20\u606f\u572830\u5929\uff08\u542b\uff09\u4ee5\u5185\u7684\u6b21\u6570\u4e0d\u8d85\u8fc76\u6b21\uff0c\u4e14\u4e0d\u5b58\u5728\u903e\u671f\u6216\u6b20\u606f30\u5929\u4ee5\u4e0a\u7684\u4fe1\u7528\u8bb0\u5f55\uff1b<br/> \r\n3.\u52a0\u4e0a\u672c\u7b14\u8d37\u6b3e\u540e\u8d37\u6b3e\u4f59\u989d\u5408\u8ba1\u4e0d\u8d85\u8fc73000\u4e07\u5143\uff1b<br/> \r\n4.\u5bf9\u5916\u62c5\u4fdd\u91d1\u989d\u5408\u8ba1\u4e0d\u8d85\u8fc71000\u4e07\u5143\u3002<br/> \r\n<p><b>\u62b5\u62bc\u7269\u9700\u6ee1\u8db3\u4ee5\u4e0b\u8981\u6c42\uff1a</b></p>\r\n\u5c45\u4f4f\u7528\u623f\u7c7b\u623f\u9f84\u4e0d\u8d85\u8fc720\u5e74\uff1b\u62b5\u62bc\u7269\u6240\u6709\u4eba\u4e3a\u81ea\u7136\u4eba\u7684\uff0c\u9700\u5e74\u6ee118\u5468\u5c81\uff0c\u4e14\u4e0d\u8d85\u8fc765\u5468\u5c81\u3002\u5c45\u4f4f\u7528\u623f\u7c7b\u7684\u571f\u5730\u4f7f\u7528\u5e74\u9650\u4e3a70\u5e74\u3002<br/> \r\n<p>\uff08\u4e8c\uff09\u8d37\u6b3e\u989d\u5ea6</p>\r\n<p><b>\u6839\u636e\u62b5\u62bc\u7269\u7684\u8bc4\u4f30\u4ef7\u503c\u4e58\u4ee5\u62b5\u62bc\u7387\u8fdb\u884c\u786e\u5b9a\u3002\u989d\u5ea6\u6700\u9ad8\u4e0d\u8d85\u8fc7500\u4e07\u5143\u3002</b></p>\r\n1.\u62b5\u62bc\u7269\u4e3a\u666e\u901a\u5546\u54c1\u7528\u623f\u3001\u9ad8\u6863\u516c\u5bd3\u548c\u7ecf\u6d4e\u9002\u7528\u4f4f\u623f\uff08\u9650\u4ef7\u623f\uff09\u7684\uff0c\u62b5\u62bc\u7387\u4e0d\u8d85\u8fc770%\uff1b\u62b5\u62bc\u7269\u4e3a\u522b\u5885\u7684\uff0c\u62b5\u62bc\u7387\u4e0d\u8d85\u8fc760%\uff1b\u62b5\u62bc\u7269\u4e3a\u5546\u94fa\uff08\u4e34\u8857\uff09\u7684\uff0c\u62b5\u62bc\u7387\u4e0d\u8d85\u8fc750%\u3002<br/> \r\n2.\u4ee5\u5efa\u884c\u5c1a\u6709\u6309\u63ed\u4f59\u989d\u7684\u4f4f\u5b85\u4f5c\u4e3a\u62b5\u62bc\u7269\u7684\uff08\u4ee5\u4e0b\u79f0\u201c\u4f59\u989d\u4e8c\u62bc\u201d\uff09\uff0c\u53ef\u8d37\u672c\u91d1=\u62b5\u62bc\u7269\u8bc4\u4f30\u4ef7\u503c*\u62b5\u62bc\u7387\u2013\u5728\u672c\u8d37\u6b3e\u5ba1\u6279\u65f6\u539f\u4e2a\u4eba\u4f4f\u623f\u6309\u63ed\u8d37\u6b3e\u7684\u672a\u7ed3\u6e05\u672c\u91d1\u548c\u4e00\u5e74\u5229\u606f\u4e4b\u548c\u3002<br/> \r\n3.\u8d37\u6b3e\u671f\u9650\uff1a<b>\u4e00\u822c1\u5e74\uff0c\u53ef\u5faa\u73af\u8d37\u6b3e\uff0c\u6700\u957f\u6709\u6548\u671f\u4e3a3\u5e74\uff08\u542b\uff09\u3002</b><br/> \r\n4.\u8d37\u6b3e\u5229\u7387\u548c\u8ba1\u606f\uff1a<b>\u65e5\u5229\u7387\u4f4e\u4e8e\u4e07\u5206\u4e4b2 \uff1b</b><br/> \r\n5.\u8fd8\u6b3e\u65b9\u5f0f\uff1a<br/> \r\n\u5230\u671f\u7cfb\u7edf\u81ea\u52a8\u4ece\u7b7e\u7ea6\u8fd8\u6b3e\u8d26\u6237\u6263\u6b3e\u6216\u901a\u8fc7\u5efa\u8bbe\u94f6\u884c\u67dc\u53f0\u9884\u7ea6\u63d0\u524d\u8fd8\u6b3e\u3002<br/> \r\n<img src=\"https://api.clpays.com:29001/intro/l1-1.png\" /><br/>"},{"id":"2","name":"\u4fe1\u7528\u8d37\u6b3e\uff0c\u8bda\u4fe1\u5c31\u8d37\uff01\u6211\u8981\u7533\u8bf7","intro":null},{"id":"3","name":"\u5176\u4ed6\u8d37\u6b3e\uff0c\u6211\u8981\u7533\u8bf7","intro":null}]}}
                    if ("true".equals(isGetStringFromJson.handleData("success", a))) {
                        creditTypeList.clear();
                        String data=isJsonObj.handleData("data",a);
                        JsonArray array= isJsonArray.handleData("loan",data);
                        int size=array.size();
                        for(int i=0;i<size;i++){
                            CreditType creditType=new CreditType();
                            String id=isGetStringFromJson.handleData("id",array.get(i).toString());
                            creditType.setId(id);
                            String name=isGetStringFromJson.handleData("name",array.get(i).toString());
                            creditType.setTitle(name);
                            String intro=isGetStringFromJson.handleData("intro",array.get(i).toString());
                            creditType.setContent(intro);
                            String need=isGetStringFromJson.handleData("need",array.get(i).toString());
                            creditType.setNeed(need);
                            String pic=isGetStringFromJson.handleData("img",array.get(i).toString());
                            creditType.setPic(pic);

                            if(!intro.isEmpty()){
                                creditType.setCmd("1");
                            }else{
                                creditType.setCmd("2");
                            }
                            creditTypeList.add(creditType);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, throwable -> {
                    //ToastUtil.ShortToast("数据错误");
                });
    }
}

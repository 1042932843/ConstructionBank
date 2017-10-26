package com.clpays.tianfugou.Module.Message;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.clpays.tianfugou.Adapter.MessageAdapter;
import com.clpays.tianfugou.Entity.Message.MessageVo;
import com.clpays.tianfugou.Module.Base.BaseActivity;
import com.clpays.tianfugou.Network.RequestProperty;
import com.clpays.tianfugou.Network.RetrofitHelper;
import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.PreferenceUtil;
import com.clpays.tianfugou.Utils.SystemBarHelper;
import com.clpays.tianfugou.Utils.tools.isGetStringFromJson;
import com.clpays.tianfugou.Utils.tools.isJsonArray;
import com.clpays.tianfugou.Utils.tools.isJsonObj;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MessageActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @OnClick(R.id.back)
    public void back(){
        finish();
    }

    @BindView(R.id.list)
    ListView listView;

    MessageAdapter messageAdapter;

    private List<MessageVo> msgList = new ArrayList<MessageVo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarHelper.immersiveStatusBar(this);
        SystemBarHelper.setHeightAndPadding(this, toolbar);
        messageAdapter=new MessageAdapter(this,msgList);
        listView.setAdapter(messageAdapter);

    }

    @Override
    public void onResume(){
        super.onResume();
        fetch();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    public void fetch(){
        JsonObject obj= RequestProperty.CreateTokenJsonObjectBody();
        RetrofitHelper.getMSGAPI()
                .fetchpush(obj)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    String a = bean.string();//{"success":true,"message":"","data":{"push":[]}}
                    if ("true".equals(isGetStringFromJson.handleData("success", a))) {
                        JsonArray array= isJsonArray.handleData("push",isJsonObj.handleData("data",a));
                        int size=array.size();
                        for(int i=0;i<size;i++){
                            String item=  array.get(i).toString();
                            String content=isGetStringFromJson.handleData("content",item);
                            String time=isGetStringFromJson.handleData("time",item);
                            String isread=isGetStringFromJson.handleData("isread",item);
                            MessageVo messageVo=new MessageVo(MessageVo.MESSAGE_FROM,content,time);
                            msgList.add(messageVo);

                        }
                        messageAdapter.notifyDataSetChanged();
                    }
                },throwable -> {

                });
    }

    @Override
    public void initToolBar() {

    }

}

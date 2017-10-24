package com.clpays.tianfugou.Module.Message;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.clpays.tianfugou.Adapter.MessageAdapter;
import com.clpays.tianfugou.Entity.Message.MessageVo;
import com.clpays.tianfugou.Module.Base.BaseActivity;
import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.SystemBarHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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
    public int getLayoutId() {
        return R.layout.activity_my_credit;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initToolBar() {

    }

}

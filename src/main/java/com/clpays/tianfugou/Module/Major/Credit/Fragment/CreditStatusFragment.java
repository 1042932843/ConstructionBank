package com.clpays.tianfugou.Module.Major.Credit.Fragment;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.clpays.tianfugou.Entity.Credit.CreditType;
import com.clpays.tianfugou.Module.Base.BaseFragment;
import com.clpays.tianfugou.Network.RequestProperty;
import com.clpays.tianfugou.Network.RetrofitHelper;
import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.SystemBarHelper;
import com.clpays.tianfugou.Utils.tools.isGetStringFromJson;
import com.clpays.tianfugou.Utils.tools.isJsonObj;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class CreditStatusFragment extends BaseFragment {
    CreditType creditType;

    @BindView(R.id.msg)
    TextView msg;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @OnClick(R.id.back)
    public void back(){
        if(creditType.getContent().isEmpty()){
            creditType.setCmd("0");
        }else{
            creditType.setCmd("back");
        }
        EventBus.getDefault().post(creditType);
        //EventBus.getDefault().post(new com.clpays.tianfugou.Entity.Common.EventUtil("基本资料"));
    }

    public static CreditStatusFragment newInstance() {
        return new CreditStatusFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_credit_status;
    }

    @Override
    public void finishCreateView(Bundle state) {
        SystemBarHelper.setHeightAndPadding(getContext(), toolbar);
        creditType=(CreditType)getArguments().getSerializable("CreditType");
        pushloan();
    }

    public void pushloan(){
        JsonObject obj= RequestProperty.CreateTokenJsonObjectBody();//带了Token的
        obj.addProperty("loanid",creditType.getId());
        RetrofitHelper.getCreditAPI()
                .pushloan(obj)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    String a = bean.string();
                    if ("true".equals(isGetStringFromJson.handleData("success", a))) {
                        String data= isJsonObj.handleData("data",a);

                    }
                    String message= isGetStringFromJson.handleData("message",a);
                    msg.setText(message);
                }, throwable -> {
                    //ToastUtil.ShortToast("数据错误");
                });
    }


}

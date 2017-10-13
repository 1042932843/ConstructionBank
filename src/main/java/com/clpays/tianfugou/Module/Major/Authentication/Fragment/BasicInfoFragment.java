package com.clpays.tianfugou.Module.Major.Authentication.Fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.clpays.tianfugou.Design.Dialog.DialogLoading;
import com.clpays.tianfugou.Design.Dialog.DialogRegionalChoice;
import com.clpays.tianfugou.Design.keyEditText.KeyEditText;
import com.clpays.tianfugou.Entity.RegionalChoice.EventUtil;
import com.clpays.tianfugou.Module.Base.BaseFragment;
import com.clpays.tianfugou.Network.RequestProperty;
import com.clpays.tianfugou.Network.RetrofitHelper;
import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.ToastUtil;
import com.clpays.tianfugou.Utils.UserState;
import com.clpays.tianfugou.Utils.tools.isGetStringFromJson;
import com.clpays.tianfugou.Utils.tools.isJsonObj;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import com.clpays.tianfugou.Utils.PreferenceUtil;


public class BasicInfoFragment extends BaseFragment implements KeyEditText.KeyPreImeListener {
    String address;
    String address2;
    DialogRegionalChoice dialogRegionalChoice;
    DialogLoading dialogLoading;

    @BindView(R.id.name_edit)
    KeyEditText name_edit;
    @BindView(R.id.ID_edit)
    KeyEditText ID_edit;
    @BindView(R.id.shangguanyuan_edit)
    KeyEditText shangguanyuan_edit;
    @BindView(R.id.phonenum_edit)
    KeyEditText phonenum_edit;

    @BindView(R.id.ad)
    KeyEditText ad_text;

    @BindView(R.id.shangpudizhi)
    RelativeLayout shangpudizhi;
    @OnClick(R.id.shangpudizhi)
    public void chose(){
        dialogRegionalChoice.show(getFragmentManager(),DialogRegionalChoice.TAG);
    }

    @BindView(R.id.next_step)
    Button next_step;
    @OnClick(R.id.next_step)
    public void next(){
        String name=name_edit.getText().toString();
        String ID=ID_edit.getText().toString();
        String phonenum=phonenum_edit.getText().toString();
        String shangguanyuan=shangguanyuan_edit.getText().toString();
        submit(name,ID,phonenum,address,address2,shangguanyuan);
    }

    public static BasicInfoFragment newInstance() {

        return new BasicInfoFragment();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventUtil event){
        String Type = event.getType();
        switch (Type){
            case "商铺地址":
                address=event.getAddress();
                address2=event.getAddress2();
                ad_text.setText(address+"  "+address2);
                ad_text.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorPrimary));
                break;

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void initRecyclerView(){

    }
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_basicinfo;
    }

    @Override
    public void finishCreateView(Bundle state) {
        initRecyclerView();
        dialogRegionalChoice=new DialogRegionalChoice();
        dialogLoading=new DialogLoading();
        name_edit.setKeyPreImeListener(this);
        ID_edit.setKeyPreImeListener(this);
        shangguanyuan_edit.setKeyPreImeListener(this);
        phonenum_edit.setKeyPreImeListener(this);
        ad_text.setKeyPreImeListener(this);
        ad_text.addTextChangedListener(textWatcher);
        name_edit.addTextChangedListener(textWatcher);
        ID_edit.addTextChangedListener(textWatcher);
        shangguanyuan_edit.addTextChangedListener(textWatcher);
        phonenum_edit.addTextChangedListener(textWatcher);

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if(hidden){

        }else{
            lazyLoad();
        }
    }

    protected void lazyLoad() {
        if(UserState.NA.equals(PreferenceUtil.getStringPRIVATE("username", UserState.NA))){

        }else{
            fetch();
        }
    }



    //加载数据
    public void fetch(){
        JsonObject obj= RequestProperty.CreateTokenJsonObjectBody();//带了Token的
        RetrofitHelper.getAuthenticationAPI()
                .fetchprofile(obj)
                //.compose(this.bindToLifecycle())这里因为在不可见情况下更新页面，所以不能绑定生命周期
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    String a=bean.string();
                    if("true".equals(isGetStringFromJson.handleData("success",a))){
                        String realname=isGetStringFromJson.handleData("realname", isJsonObj.handleData("data",a));
                        String idcard=isGetStringFromJson.handleData("idcard",isJsonObj.handleData("data",a));
                        String phonenum=isGetStringFromJson.handleData("phonenum",isJsonObj.handleData("data",a));
                        address=isGetStringFromJson.handleData("address1",isJsonObj.handleData("data",a));
                        address2=isGetStringFromJson.handleData("address2",isJsonObj.handleData("data",a));
                        String shopop=isGetStringFromJson.handleData("shopop",isJsonObj.handleData("data",a));
                        if(!realname.isEmpty()){
                            name_edit.setText(realname);
                        }
                        if(!idcard.isEmpty()){
                            ID_edit.setText(idcard);
                        }
                        if(!phonenum.isEmpty()){
                            phonenum_edit.setText(phonenum);
                        }
                        if(!address.isEmpty()&&!address2.isEmpty()){
                            ad_text.setText(address+"  "+address2);
                        }
                        if(!shopop.isEmpty()){
                            shangguanyuan_edit.setText(shopop);
                        }

                    }else{
                        //ToastUtil.ShortToast(isGetStringFromJson.handleData("message",a));
                    }

                }, throwable -> {
                    //ToastUtil.ShortToast("数据错误");
                });
    }

    //提交数据
    public void submit(String realname,String idcard,String phonenum,String address1,String address2,String shangguanyuan){
        dialogLoading.setMessage("资料提交中");
        dialogLoading.show(getFragmentManager(),DialogLoading.TAG);
        JsonObject obj= RequestProperty.CreateTokenJsonObjectBody();//带了Token的
        obj.addProperty("realname",realname);
        obj.addProperty("idcard",idcard);
        obj.addProperty("phonenum",phonenum);
        obj.addProperty("address1",address1);
        obj.addProperty("address2",address2);
        if(!shangguanyuan.isEmpty()){
            obj.addProperty("shopop",shangguanyuan);
        }
        RetrofitHelper.getAuthenticationAPI()
                .pushprofile(obj)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    String a=bean.string();
                    if("true".equals(isGetStringFromJson.handleData("success",a))){
                        EventBus.getDefault().post(new com.clpays.tianfugou.Entity.Common.EventUtil("套餐选择"));
                    }else{
                        ToastUtil.ShortToast(isGetStringFromJson.handleData("message",a));
                    }
                    dialogLoading.dismiss();
                }, throwable -> {
                    dialogLoading.dismiss();
                    //ToastUtil.ShortToast("数据错误");
                });

    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            next_step.setEnabled(name_edit.getText().length() != 0
                    && ID_edit.getText().length() != 0
                    && phonenum_edit.getText().length() != 0
                    && ad_text.getText().length() != 0);
        }
    };

    @Override
    public void onKeyPreImeUp(int keyCode, KeyEvent event) {
        name_edit.clearFocus();
        ID_edit.clearFocus();
        shangguanyuan_edit.clearFocus();
        phonenum_edit.clearFocus();
        ad_text.clearFocus();
    }
}

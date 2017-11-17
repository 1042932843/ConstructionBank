package com.clpays.tianfugou.Module.Major.Authentication.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.clpays.tianfugou.Design.Dialog.DialogLoading;
import com.clpays.tianfugou.Design.Dialog.DialogRegionalChoice;
import com.clpays.tianfugou.Design.keyEditText.KeyEditText;
import com.clpays.tianfugou.Entity.Auth.AddressBean;
import com.clpays.tianfugou.Entity.RegionalChoice.EventUtil;
import com.clpays.tianfugou.Module.Base.BaseFragment;
import com.clpays.tianfugou.Network.RequestProperty;
import com.clpays.tianfugou.Network.RetrofitHelper;
import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.ToastUtil;
import com.clpays.tianfugou.Utils.UserState;
import com.clpays.tianfugou.Utils.tools.isGetStringFromJson;
import com.clpays.tianfugou.Utils.tools.isJsonObj;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import com.clpays.tianfugou.Utils.PreferenceUtil;
import com.google.gson.JsonParser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class BasicInfoFragment extends BaseFragment implements KeyEditText.KeyPreImeListener {
    String address;
    String address2;
    DialogRegionalChoice dialogRegionalChoice;
    DialogLoading dialogLoading;

    @BindView(R.id.name_e)
    TextView name_e;
    @BindView(R.id.id_e)
    TextView id_e;
    @BindView(R.id.phone_e)
    TextView phone_e;
    @BindView(R.id.shangpu_e)
    TextView shangpu_e;
    @BindView(R.id.shangguanyuan_e)
    TextView shangguanyuan_e;

    @BindView(R.id.lianxiname_e)
    TextView lianxiname_e;
    @BindView(R.id.lianxiphone_e)
    TextView lianxiphone_e;



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

    @BindView(R.id.lianxiname_edit)
    KeyEditText lianxiname_edit;
    @BindView(R.id.lianxiphone_edit)
    KeyEditText lianxiphone_edit;

    @BindView(R.id.shangpudizhi)
    RelativeLayout shangpudizhi;
    @OnClick(R.id.shangpudizhi)
    public void chose(){
        DialogRegionalChoice.ad=address;
        Bundle args =new Bundle();
        args.putSerializable("list",(Serializable)addressBeanList);
        dialogRegionalChoice.setArguments(args);
        dialogRegionalChoice.show(getFragmentManager(),DialogRegionalChoice.TAG);
    }
    @OnClick (R.id.ad)
    public void chose2(){
        DialogRegionalChoice.ad=address;
        Bundle args =new Bundle();
        args.putSerializable("list",(Serializable)addressBeanList);
        dialogRegionalChoice.setArguments(args);
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
        String lianxiname=lianxiname_edit.getText().toString();
        String lianxiphone=lianxiphone_edit.getText().toString();
        submit(name,ID,phonenum,address,address2,shangguanyuan,lianxiname,lianxiphone);
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
                ad_text.setText(address+","+address2);
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
        lianxiname_edit.setKeyPreImeListener(this);
        lianxiphone_edit.setKeyPreImeListener(this);
        ad_text.addTextChangedListener(textWatcher);
        name_edit.addTextChangedListener(textWatcher);
        ID_edit.addTextChangedListener(textWatcher);
        shangguanyuan_edit.addTextChangedListener(textWatcher);
        phonenum_edit.addTextChangedListener(textWatcher);
        //shangguanyuan_edit.setText("000001");
        load=false;
        if(!load){
            lazyLoad();
            load=true;
        }
    }

    boolean load;
    @Override
    public void onHiddenChanged(boolean hidden) {
        if(hidden){

        }else{
            lazyLoad();
            load=true;
        }
    }

    protected void lazyLoad() {
        if(UserState.NA.equals(PreferenceUtil.getStringPRIVATE("username", UserState.NA))){

        }else{
            fetch();
        }
    }



    boolean realnameE;
    boolean idcardE;
    boolean phonenumE;
    boolean addressE;
    boolean address2E;
    boolean shopopE;
    boolean contactnameE;
    boolean contactnumE;
    String realname;
    String idcard;
    String phonenum;
    String shopop;
    String contactname;
    String contactnum;

    String Erealname;
    String Eidcard;
    String Ephonenum;
    String Eaddress;
    String Eaddress2;
    String Eshopop;
    String Econtactname;
    String Econtactnum;
    static ArrayList<AddressBean> addressBeanList;
    //加载数据
    public void fetch(){
        addressBeanList=new ArrayList<>();
        JsonObject obj= RequestProperty.CreateTokenJsonObjectBody();//带了Token的
        RetrofitHelper.getAuthenticationAPI()
                .fetchprofile(obj)
                //.compose(this.bindToLifecycle())这里因为在不可见情况下更新页面，所以不能绑定生命周期
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    String a=bean.string();
                    //{"success":true,"message":"","data":{"realname":"dsy","shopname":"ty","phonenum":"1872566327","contactname":"","contactnum":"","address1":"A","address2":"dfhh","shopop":1,"addresslist":{"\u4e00\u533a":["\u4e00\u697c","\u4e8c\u697c","\u4e09\u697c","\u56db\u697c"],"\u4e8c\u533a":["\u4e00\u697c","\u4e8c\u697c","\u4e09\u697c","\u56db\u697c"],"\u4e09\u533a":["\u4e00\u697c","\u4e8c\u697c","\u4e09\u697c","\u56db\u697c"],"\u56db\u533a":["\u4e00\u697c","\u4e8c\u697c","\u4e09\u697c","\u56db\u697c"],"\u519c\u4ea7\u54c1\u5e02\u573a(\u4e94\u533a)":["\u4e00\u697c"],"\u4e2d\u836f\u6750\u5e02\u573a(\u516d\u533a)":["\u4e00\u697c","\u4e8c\u697c"],"\u9676\u74f7\u5e02\u573a":["\u4e00\u697c"],"\u7f1d\u7eab\u673a\u5e02\u573a":["\u4e00\u697c"],"\u5176\u4ed6":["\u4e34\u65f6","\u5176\u4ed6"]}}}
                    if("true".equals(isGetStringFromJson.handleData("success",a))){
                        addressBeanList.clear();
                        String data=isJsonObj.handleData("data",a);
                         realname=isGetStringFromJson.handleData("realname", data);
                         idcard=isGetStringFromJson.handleData("shopname",data);
                        phonenum=isGetStringFromJson.handleData("phonenum",data);
                        address=isGetStringFromJson.handleData("address1",data);
                        address2=isGetStringFromJson.handleData("address2",data);
                         shopop=isGetStringFromJson.handleData("shopop",data);
                        contactname=isGetStringFromJson.handleData("contactname",data);
                        contactnum=isGetStringFromJson.handleData("contactnum",data);

                        //{"一区":["一楼","二楼","三楼","四楼"],"二区":["一楼","二楼","三楼","四楼"],"三区":["一楼","二楼","三楼","四楼"],"四区":["一楼","二楼","三楼","四楼"],"农产品市场(五区)":["一楼"],"中药材市场(六区)":["一楼","二楼"],"陶瓷市场":["一楼"],"缝纫机市场":["一楼"],"其他":["临时","其他"]}
                        String addresslist=isJsonObj.handleData("addresslist",data);
                        JsonObject myJsondata = new JsonParser().parse(addresslist).getAsJsonObject();

                        Iterator i$ = myJsondata.entrySet().iterator();
                        while(i$.hasNext()) {
                            Map.Entry entry = (Map.Entry)i$.next();
                            AddressBean bean1=new AddressBean();
                            bean1.setAddress(entry.getKey()+"");
                            JsonArray array= new JsonParser().parse(entry.getValue()+"").getAsJsonArray();
                            ArrayList<String> items=new ArrayList<String>();
                            int arraysize=array.size();
                            for(int i=0;i<arraysize;i++){
                               String item= array.get(i).getAsString();
                                items.add(item);
                            }
                            bean1.setItems(items);
                            addressBeanList.add(bean1);
                           // Log.v(TAG, entry.getKey()+"|"+entry.getValue());
                        }
                        addressBeanList.size();
                        String error=isJsonObj.handleData("error",data);

                        if(!error.isEmpty()){
                            Erealname =isGetStringFromJson.handleData("realname", error);
                            if(!Erealname.isEmpty()){
                                realnameE=true;
                            }
                            Eidcard =isGetStringFromJson.handleData("shopname",error);
                            if(!Eidcard.isEmpty()){
                                idcardE=true;
                            }
                            Ephonenum =isGetStringFromJson.handleData("phonenum",error);
                            if(!Ephonenum.isEmpty()){
                                phonenumE=true;
                            }
                            Eaddress =isGetStringFromJson.handleData("address1",error);
                            if(!Eaddress.isEmpty()){
                                addressE=true;
                            }
                            Eaddress2=isGetStringFromJson.handleData("address2",error);
                            if(!Eaddress2.isEmpty()){
                                address2E=true;
                            }
                            Eshopop =isGetStringFromJson.handleData("shopop",error);
                            if(!Eshopop.isEmpty()){
                                shopopE=true;
                            }
                            Econtactname=isGetStringFromJson.handleData("contactname",error);
                            if(!Econtactname.isEmpty()){
                                contactnameE=true;
                            }
                            Econtactnum=isGetStringFromJson.handleData("contactnum",error);
                            if(!Econtactnum.isEmpty()){
                                contactnumE=true;
                            }

                        }
                        if(!realname.isEmpty()){
                            if(realnameE){
                                name_e.setText(Erealname);
                                name_e.setVisibility(View.VISIBLE);
                            }
                            name_edit.setText(realname);

                        }
                        if(!idcard.isEmpty()){
                            if(idcardE){
                                id_e.setText(Eidcard);
                                id_e.setVisibility(View.VISIBLE);
                            }
                            ID_edit.setText(idcard);
                        }
                        if(!phonenum.isEmpty()){

                            if(phonenumE){
                                phone_e.setText(Ephonenum);
                                phone_e.setVisibility(View.VISIBLE);
                            }
                            phonenum_edit.setText(phonenum);
                        }
                        if(!address.isEmpty()&&!address2.isEmpty()){
                            String ad=address+"  "+address2;
                            if(addressE){
                                shangpu_e.setText(Eaddress);
                                shangpu_e.setVisibility(View.VISIBLE);
                            }
                            ad_text.setText(ad);
                        }
                        if(!shopop.isEmpty()){

                            if(shopopE){
                                shangguanyuan_e.setText(Eshopop);
                                shangguanyuan_e.setVisibility(View.VISIBLE);
                            }
                            shangguanyuan_edit.setText(shopop);
                        }

                        if(!contactname.isEmpty()){

                            if(contactnameE){
                                lianxiname_e.setText(Econtactname);
                                lianxiname_e.setVisibility(View.VISIBLE);
                            }
                            lianxiname_edit.setText(contactname);
                        }
                        if(!contactnum.isEmpty()){
                            if(contactnumE){
                                lianxiphone_e.setText(Econtactnum);
                                lianxiphone_e.setVisibility(View.VISIBLE);
                            }
                            lianxiphone_edit.setText(contactnum);
                        }

                    }else{
                        //ToastUtil.ShortToast(isGetStringFromJson.handleData("message",a));
                    }

                }, throwable -> {
                    //ToastUtil.ShortToast("数据错误");
                });
    }

    //提交数据
    public void submit(String realname,String shopname,String phonenum,String address1,String address2,String shangguanyuan,String lianxiname,String lianxiphone){

        JsonObject obj= RequestProperty.CreateTokenJsonObjectBody();//带了Token的
        obj.addProperty("realname",realname);
        obj.addProperty("shopname",shopname);
        obj.addProperty("phonenum",phonenum);
        obj.addProperty("address1",address1);
        obj.addProperty("address2",address2);
        if(!shangguanyuan.isEmpty()){
            obj.addProperty("shopop",shangguanyuan);
        }else{
            ToastUtil.ShortToast("商管员ID不能为空");
            return;
        }

        if(!lianxiname.isEmpty()){
            obj.addProperty("contactname",lianxiname);

        }
        if(!lianxiphone.isEmpty()){
            obj.addProperty("contactnum",lianxiphone);
        }
        dialogLoading.setMessage("资料提交中");
        dialogLoading.show(getFragmentManager(),DialogLoading.TAG);
        RetrofitHelper.getAuthenticationAPI()
                .pushprofile(obj)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    String a=bean.string();
                    if("true".equals(isGetStringFromJson.handleData("success",a))){
                        String s= PreferenceUtil.getStringPRIVATE("status", UserState.NA);
                        if("review_profile".equals(s)){
                            EventBus.getDefault().post(new com.clpays.tianfugou.Entity.Common.EventUtil("提交完成"));
                        }else if("review_profile_upload".equals(s)){
                            EventBus.getDefault().post(new com.clpays.tianfugou.Entity.Common.EventUtil("证照上传"));
                        }else {
                            EventBus.getDefault().post(new com.clpays.tianfugou.Entity.Common.EventUtil("套餐选择"));
                        }

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
            if(!realname.equals(name_edit.getText().toString())){
                name_e.setVisibility(View.GONE);
            }
            if(!idcard.equals(ID_edit.getText().toString())){
                id_e.setVisibility(View.GONE);
            }
            if(!phonenum.equals(phonenum_edit.getText().toString())){
                phone_e.setVisibility(View.GONE);
            }
            if(!(address+"  "+address2).equals(ad_text.getText().toString())){
                shangpu_e.setVisibility(View.GONE);
            }
            if(!shopop.equals(shangguanyuan_edit.getText().toString())){
                shangguanyuan_e.setVisibility(View.GONE);
            }

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
        lianxiphone_edit.clearFocus();
        lianxiphone_edit.clearFocus();
    }
}

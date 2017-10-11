package nbsix.com.constructionbank.Module.Major.Authentication.Fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import nbsix.com.constructionbank.Design.Dialog.DialogLoading;
import nbsix.com.constructionbank.Design.Dialog.DialogRegionalChoice;
import nbsix.com.constructionbank.Design.keyEditText.KeyEditText;
import nbsix.com.constructionbank.Module.Base.BaseFragment;
import nbsix.com.constructionbank.Network.RequestProperty;
import nbsix.com.constructionbank.Network.RetrofitHelper;
import nbsix.com.constructionbank.R;
import nbsix.com.constructionbank.Entity.Common.EventUtil;
import nbsix.com.constructionbank.Utils.PreferenceUtil;
import nbsix.com.constructionbank.Utils.ToastUtil;
import nbsix.com.constructionbank.Utils.UserState;
import nbsix.com.constructionbank.Utils.tools.isGetStringFromJson;


public class BasicInfoFragment extends BaseFragment implements KeyEditText.KeyPreImeListener{
    String ad;
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
    TextView ad_text;

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
        //submit();
    }

    public static BasicInfoFragment newInstance() {

        return new BasicInfoFragment();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventUtil event){
        String Type = event.getType();
        switch (Type){
            case "商铺地址":
                ad=event.getMsg();
                ad_text.setText(ad);
                ad_text.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorPrimary));
                break;

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
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
        name_edit.setKeyPreImeListener(this);
        ID_edit.setKeyPreImeListener(this);
        shangguanyuan_edit.setKeyPreImeListener(this);
        phonenum_edit.setKeyPreImeListener(this);
        name_edit.addTextChangedListener(textWatcher);
        ID_edit.addTextChangedListener(textWatcher);
        shangguanyuan_edit.addTextChangedListener(textWatcher);
        phonenum_edit.addTextChangedListener(textWatcher);
        if(UserState.NA.equals(PreferenceUtil.getStringPRIVATE("username", UserState.NA))){

        }else{
            phonenum_edit.setText(PreferenceUtil.getStringPRIVATE("username", UserState.NA));
        }


    }

    public void submit(String realname,String idcard,String phonenum,String address1,String address2){
        dialogLoading.setMessage("资料提交中");
        dialogLoading.show(getFragmentManager(),DialogLoading.TAG);
        JsonObject obj= RequestProperty.CreateTokenJsonObjectBody();
        obj.addProperty("realname ",realname);
        obj.addProperty("idcard ",idcard);
        obj.addProperty("phonenum",phonenum);
        obj.addProperty("address1",address1);
        obj.addProperty("address2",address2);
        RetrofitHelper.getLoginRegisterAPI()
                .login(obj)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    String a=bean.string();//{"success":true,"message":"","data":{"token":"1haL06uZXgHQIT6-0HuZ24Q1eQWjVSN0","status":"\u5b9e\u540d\u8ba4\u8bc1"}}
                    if("true".equals(isGetStringFromJson.handleData("success",a))){
                        EventBus.getDefault().post(new EventUtil("证件上传"));
                    }else{
                        ToastUtil.ShortToast(isGetStringFromJson.handleData("message",a));
                    }
                    dialogLoading.dismiss();
                }, throwable -> {
                    dialogLoading.dismiss();
                    ToastUtil.ShortToast("数据错误");
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
                    && shangguanyuan_edit.getText().length() != 0
                    && phonenum_edit.getText().length() != 0);
        }
    };

    @Override
    public void onKeyPreImeUp(int keyCode, KeyEvent event) {
        name_edit.clearFocus();
        ID_edit.clearFocus();
        shangguanyuan_edit.clearFocus();
        phonenum_edit.clearFocus();

    }
}

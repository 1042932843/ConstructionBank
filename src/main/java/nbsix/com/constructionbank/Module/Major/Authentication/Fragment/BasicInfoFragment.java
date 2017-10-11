package nbsix.com.constructionbank.Module.Major.Authentication.Fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bilibili.magicasakura.widgets.TintButton;
import com.bilibili.magicasakura.widgets.TintCheckBox;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import nbsix.com.constructionbank.Design.Dialog.DialogRegionalChoice;
import nbsix.com.constructionbank.Design.keyEditText.KeyEditText;
import nbsix.com.constructionbank.Module.Base.BaseFragment;
import nbsix.com.constructionbank.R;
import nbsix.com.constructionbank.Utils.EventUtil;
import nbsix.com.constructionbank.Utils.PreferenceUtil;
import nbsix.com.constructionbank.Utils.SystemBarHelper;
import nbsix.com.constructionbank.Utils.ToastUtil;
import nbsix.com.constructionbank.Utils.UserState;


public class BasicInfoFragment extends BaseFragment implements KeyEditText.KeyPreImeListener{
    String ad;
    DialogRegionalChoice dialogRegionalChoice;

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
        EventBus.getDefault().post(new EventUtil("证件上传"));
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

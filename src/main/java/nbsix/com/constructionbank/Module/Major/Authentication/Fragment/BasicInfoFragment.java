package nbsix.com.constructionbank.Module.Major.Authentication.Fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.TextView;

import com.bilibili.magicasakura.widgets.TintButton;
import com.bilibili.magicasakura.widgets.TintCheckBox;

import butterknife.BindView;
import butterknife.OnClick;
import nbsix.com.constructionbank.Design.keyEditText.KeyEditText;
import nbsix.com.constructionbank.Module.Base.BaseFragment;
import nbsix.com.constructionbank.R;
import nbsix.com.constructionbank.Utils.SystemBarHelper;


public class BasicInfoFragment extends BaseFragment implements KeyEditText.KeyPreImeListener{
    @BindView(R.id.name_edit)
    KeyEditText name_edit;
    @BindView(R.id.ID_edit)
    KeyEditText ID_edit;
    @BindView(R.id.com_edit)
    KeyEditText com_edit;
    @BindView(R.id.phonenum_edit)
    KeyEditText phonenum_edit;
    @BindView(R.id.checkbox_1)
    TintCheckBox checkbox_1;

    @BindView(R.id.next_step)
    Button next_step;
    @OnClick(R.id.next_step)
    public void next(){

    }

    public static BasicInfoFragment newInstance() {

        return new BasicInfoFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        name_edit.addTextChangedListener(textWatcher);
        ID_edit.addTextChangedListener(textWatcher);
        com_edit.addTextChangedListener(textWatcher);
        phonenum_edit.addTextChangedListener(textWatcher);

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
                    && com_edit.getText().length() != 0
                    && phonenum_edit.getText().length() != 0);
        }
    };

    @Override
    public void onKeyPreImeUp(int keyCode, KeyEvent event) {
        name_edit.clearFocus();
        ID_edit.clearFocus();
        com_edit.clearFocus();
        phonenum_edit.clearFocus();

    }
}

package com.clpays.tianfugou.Design.Dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.clpays.tianfugou.Adapter.MyAdapter;
import com.clpays.tianfugou.Design.keyEditText.KeyEditText;
import com.clpays.tianfugou.Entity.Auth.AddressBean;
import com.clpays.tianfugou.Entity.RegionalChoice.EventUtil;

import com.clpays.tianfugou.Module.Major.Authentication.Fragment.BasicInfoFragment;
import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.LogUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Name: DialogRegionalChoice
 * Author: Dusky
 * QQ: 1042932843
 * Comment: 自定义dialog
 * Date: 2017-04-12 00:44
 */

public class DialogRegionalChoice extends DialogFragment implements KeyEditText.KeyPreImeListener{
    public static final String TAG = DialogRegionalChoice.class.getSimpleName();
    public static String ad="";
    ArrayList<String> list2=new ArrayList<>();
    ArrayList<AddressBean> list;
    MyAdapter myAdapter,myAdapter2;
    String[] aa;
    @BindView(R.id.makesure_btn)
    Button makesure_btn;
    @OnClick(R.id.makesure_btn)
    public void sure(){
        String area= spinner1.getSelectedItem().toString();
        String area2= spinner2.getSelectedItem().toString();
        LogUtil.d(area+","+area2);
        String ad= edit_where.getText().toString();
        LogUtil.d(ad);
        EventBus.getDefault().post(new EventUtil("商铺地址",area+","+area2,ad));
        this.dismiss();
    }
    @BindView(R.id.edit_where)
    KeyEditText edit_where;
    @BindView(R.id.spinner1)
    AppCompatSpinner spinner1;
    @BindView(R.id.spinner2)
    AppCompatSpinner spinner2;

    private AdapterView.OnItemSelectedListener onItemSelectedListener= new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //选择列表项的操作
            list2.clear();
            list2.addAll(list.get(position).getItems());
            //myAdapter2.notifyDataSetChanged();
            spinner2.setAdapter(myAdapter2);
            //spinner2.setSelection(0,true);

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            //未选中时候的操作
        }
    };

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            makesure_btn.setEnabled(edit_where.getText().length() != 0
                    );
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme_AppCompat_Dialog_Alert2);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dialog_regionalchoice, container, false);
        ButterKnife.bind(this, view);
        aa=ad.split(",");
        edit_where.addTextChangedListener(textWatcher);
        edit_where.setKeyPreImeListener(this);
        spinner1.setOnItemSelectedListener(onItemSelectedListener);
        list=(ArrayList<AddressBean>)getArguments().getSerializable("list");
       ArrayList<String> list1=new ArrayList<>();
        int size1=list.size();
        String[] titleArr=new String[size1];
        for(int i=0;i<size1;i++){
            String si=list.get(i).getAddress();
            titleArr[i]= si;
            list1.add(si);
        }
        myAdapter=new MyAdapter(list1,getContext());
        spinner1.setAdapter(myAdapter);
        int biao=0;
        if( titleArr != null ){
            int titleLength = titleArr.length;
            for( int index = 0; index < titleLength; index++ ){
                if(titleArr[index].equals(aa[0])){
                    spinner1.setSelection(index);
                    biao=index;
                }
            }

        }
        list2.clear();
        list2.addAll(list.get(biao).getItems());
        myAdapter2=new MyAdapter(list2,getContext());
        spinner2.setAdapter(myAdapter2);
        for (int i=0;i<list2.size();i++){
            if(aa.length>1){
                if(list2.get(i). equals(aa[1])){
                    spinner2.setSelection(i);
                }
            }

        }
        return view;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onKeyPreImeUp(int keyCode, KeyEvent event) {
        edit_where.clearFocus();
    }
}

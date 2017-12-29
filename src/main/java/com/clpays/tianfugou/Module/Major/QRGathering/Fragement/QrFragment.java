package com.clpays.tianfugou.Module.Major.QRGathering.Fragement;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import com.bumptech.glide.Glide;
import com.clpays.tianfugou.App.app;
import com.clpays.tianfugou.Design.PayCustomView.PayCustomView;
import com.clpays.tianfugou.Module.Base.BaseFragment;
import com.clpays.tianfugou.Entity.Common.EventUtil;
import com.clpays.tianfugou.Network.RequestProperty;
import com.clpays.tianfugou.Network.RetrofitHelper;
import com.clpays.tianfugou.R;
import com.clpays.tianfugou.Utils.BitmapUtils;
import com.clpays.tianfugou.Utils.SystemBarHelper;
import com.clpays.tianfugou.Utils.tools.isGetStringFromJson;
import com.clpays.tianfugou.Utils.tools.isJsonObj;
import com.clpays.tianfugou.zxing.encode.CodeCreator;
import com.google.gson.JsonObject;


public class QrFragment extends BaseFragment {
    Bitmap bitmap;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.qr)
    ImageView qr;

    @BindView(R.id.paycustomView)
    PayCustomView paycustomView;

    @OnClick(R.id.save_pic)
    public void save(){
        BitmapUtils.saveBitmap(bitmap);
    }

    @BindView(R.id.paycustomView_layout)
    RelativeLayout paycustomView_layout;
    @BindView(R.id.qr_layout)
    RelativeLayout qr_layout;

    @OnClick(R.id.shoukuanjilu)
    public void goGj(){
        EventBus.getDefault().post(new EventUtil("流水明细"));
    }

    @OnClick(R.id.back)
    public void back(){
        getActivity().finish();
    }

    public static QrFragment newInstance() {

        return new QrFragment();
    }

    @Override
    public void initRecyclerView(){

    }
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_qrgathering;
    }

    @Override
    public void finishCreateView(Bundle state) {
        SystemBarHelper.setHeightAndPadding(getContext(), toolbar);
        JsonObject obj= RequestProperty.CreateTokenJsonObjectBody();//带了Token的
        RetrofitHelper.getQRAPI()
                .getQRContent(obj)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    String a = bean.string();
                    if ("true".equals(isGetStringFromJson.handleData("success", a))) {
                        String data= isJsonObj.handleData("data",a);
                        String qrcode= isGetStringFromJson.handleData("qrcode",data);
                        bitmap=CodeCreator.createQRCode(qrcode,800,800,null);
                        qr.setImageBitmap(bitmap);
                        //Glide.with(getContext()).load(bitmap).into(qr);
                    }
                    String message= isGetStringFromJson.handleData("message",a);
                }, throwable -> {
                    //ToastUtil.ShortToast("数据错误");
                });
       // Glide.with(getContext()).load(R.drawable.qr_g_dsy).apply(app.optionsNormal).into(qr);
        //paycustomView.loadLoading();
       /* Observable.timer(5000, TimeUnit.MILLISECONDS)
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    qr_layout.setVisibility(View.GONE);
                    paycustomView_layout.setVisibility(View.VISIBLE);
                    payOK();
                });*/
    }

    private void payOK(){
        Observable.timer(2000, TimeUnit.MILLISECONDS)
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    paycustomView.loadSuccess();
                });
    }

}

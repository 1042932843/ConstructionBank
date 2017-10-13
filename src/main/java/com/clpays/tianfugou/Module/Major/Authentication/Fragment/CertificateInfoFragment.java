package com.clpays.tianfugou.Module.Major.Authentication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.clpays.tianfugou.Adapter.ImagePickerAdapter;
import com.clpays.tianfugou.Design.Dialog.SelectDialog;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import com.clpays.tianfugou.Module.Base.BaseFragment;
import nbsix.clpays.tianfugou.R;
import com.clpays.tianfugou.Entity.Common.EventUtil;
import com.clpays.tianfugou.Utils.LogUtil;


public class CertificateInfoFragment extends BaseFragment implements ImagePickerAdapter.OnRecyclerViewItemClickListener {
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;

    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 1;


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.idcard_zhengmian)
    ImageView zhengmian;

    @BindView(R.id.idcard_fanmian)
    ImageView fanmian;

    @OnClick(R.id.idcard_zhengmian)
    public void getzhengmianImg(){
        onItemClick(null,IMAGE_ITEM_ADD);
    }

    @OnClick(R.id.idcard_fanmian)
    public void getfanmianImg(){
        onItemClick(null,IMAGE_ITEM_ADD);
    }

    @OnClick(R.id.next_step)
    public void next(){
        EventBus.getDefault().post(new EventUtil("提交完成"));
    }

    public static CertificateInfoFragment newInstance() {

        return new CertificateInfoFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void initRecyclerView(){
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(getContext(), selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_certificateinfo;
    }

    @Override
    public void finishCreateView(Bundle state) {
        initRecyclerView();

    }

    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(getActivity(), R.style
                .transparentFrameWindowStyle,
                listener, names);
        if (!getActivity().isFinishing()) {
            dialog.show();
        }
        return dialog;
    }


    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                List<String> names = new ArrayList<>();
                names.add("拍照");
                names.add("相册");
                showDialog(new SelectDialog.SelectDialogListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0: // 直接调起相机
                                //打开选择,本次允许选择的数量
                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                Intent intent = new Intent(getActivity(), ImageGridActivity.class);
                                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                                startActivityForResult(intent, REQUEST_CODE_SELECT);
                                break;
                            case 1:
                                //打开选择,本次允许选择的数量
                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                Intent intent1 = new Intent(getActivity(), ImageGridActivity.class);
                                /* 如果需要进入选择的时候显示已经选中的图片，
                                 * 详情请查看ImagePickerActivity
                                 * */
//                                intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
                                startActivityForResult(intent1, REQUEST_CODE_SELECT);
                                break;
                            default:
                                break;
                        }

                    }
                }, names);

                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(getActivity(), ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }
    ArrayList<ImageItem> images = null;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    selImageList.addAll(images);
                    //adapter.setImages(selImageList);
                    LogUtil.d(selImageList.get(0).path);
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images != null) {
                    selImageList.clear();
                    selImageList.addAll(images);
                    //adapter.setImages(selImageList);
                }
            }
        }
    }


    /**
     * Upload Image Client Code

    private void uploadImage() {


        //Create Upload Server Client
        UploadService service = RetrofitHelper.getUploadAPI();

        //File creating from selected URL
        File file = new File(imagePath);

        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("uploaded_file", file.getName(), requestFile);

        Call<Result> resultCall = service.uploadImage(body);

        // finally, execute the request
        resultCall.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                // Response Success or Fail
                if (response.isSuccessful()) {
                    if (response.body().getResult().equals("success")){

                    }


                } else {

                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    } */
}

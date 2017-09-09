package nbsix.com.constructionbank.Utils;

import android.content.Context;

import nbsix.com.duskycvre.Design.dialog.DialogLoading;
import nbsix.com.duskycvre.Design.dialog.DialogTheme;

/**
 * Name: DialogUtil
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-06-28 15:50
 */

public class DialogUtil {
    private DialogTheme dialogTheme;

    private DialogLoading dialogLoading;

    public void showDialogTheme(Context context,String title, String content, DialogTheme.onYesOnclickListener yesOnclickListener, DialogTheme.onNoOnclickListener onNoOnclickListener) {
        dialogTheme = new DialogTheme(context);
        dialogTheme.setTitle(title);
        dialogTheme.setMessage(content);
        dialogTheme.setYesOnclickListener("ok", yesOnclickListener);
        dialogTheme.setNoOnclickListener("cancel", onNoOnclickListener);
        dialogTheme.show();
    }

    public void showDialogLoading(Context context,String title){
        dialogLoading = new DialogLoading(context);
        dialogLoading.setTitle(title);
        dialogLoading.show();
    }

    public void dismissDialog(){
        if(dialogTheme !=null&&dialogTheme.isShowing()){
            dialogTheme.dismiss();
        }

        if(dialogLoading !=null&&dialogLoading.isShowing()){
            dialogLoading.dismiss();
        }

    }
}

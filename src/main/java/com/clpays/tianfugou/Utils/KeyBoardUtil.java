package com.clpays.tianfugou.Utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Name: KeyBoardUtil
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //软键盘工具类
 * Date: 2017-08-06 15:26
 */
public class KeyBoardUtil {

  public static void openKeybord(EditText mEditText, Context mContext) {

    InputMethodManager imm = (InputMethodManager) mContext
        .getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
        InputMethodManager.HIDE_IMPLICIT_ONLY);
  }


  public static void closeKeybord(EditText mEditText, Context mContext) {

    InputMethodManager imm = (InputMethodManager) mContext
        .getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
  }
}

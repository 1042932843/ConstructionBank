package nbsix.com.constructionbank.Design.TimeButton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bilibili.magicasakura.widgets.TintButton;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import nbsix.com.constructionbank.App.app;

/**
 * Name: TimeButton
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //倒计时按钮
 * Date: 2017-09-23 14:20
 */

public class TimeButton extends TintButton implements View.OnClickListener {
    private long length = 60 * 1000;// 倒计时长度,这里给了默认60秒
    private String textafter = "秒重新获取";
    private String textbefore = "获取验证码";
    private final String TIME = "time";
    private final String CTIME = "ctime";
    private View.OnClickListener mOnclickListener;
    private Timer t;
    private TimerTask tt;
    private long time;
    private Context mContext;
    Map<String, Long> map = new HashMap<String, Long>();

    public TimeButton(Context context) {
        super(context);
        setOnClickListener(this);

    }

    public TimeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setOnClickListener(this);
    }

    @SuppressLint("HandlerLeak")
    Handler han = new Handler() {
        public void handleMessage(android.os.Message msg) {
            TimeButton.this.setText(time / 1000 + textafter);
            time -= 1000;
            if (time < 0) {
                TimeButton.this.setEnabled(true);
                TimeButton.this.setText(textbefore);
                clearTimer();
            }
        };
    };

    public TimeButton initTimer() {
        time = length;
        t = new Timer();
        tt = new TimerTask() {

            @Override
            public void run() {
                Log.e("dsy", time / 1000 + "");
                han.sendEmptyMessage(0x01);
            }
        };
        this.setText(time / 1000 + textafter);
        this.setEnabled(false);
        t.schedule(tt, 0, 1000);
        return this;
    }

    private void clearTimer() {
        //Toast.makeText(mContext, "计时结束", Toast.LENGTH_SHORT).show();
        if (tt != null) {
            tt.cancel();
            tt = null;
        }
        if (t != null)
            t.cancel();
        t = null;
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        if (l instanceof TimeButton) {
            super.setOnClickListener(l);
        } else
            this.mOnclickListener = l;
    }

    @Override
    public void onClick(View v) {
        if (mOnclickListener != null)
            mOnclickListener.onClick(v);
        // t.scheduleAtFixedRate(task, delay, period);
    }

    /**
     * 和activity的onDestroy()方法同步
     */
    public void onDestroy() {
        if (app.map == null)
            app.map = new HashMap<String, Long>();
        app.map.put(TIME, time);
        app.map.put(CTIME, System.currentTimeMillis());
        clearTimer();
        Log.e("dsy", "onDestroy");
    }

    /**
     * 和activity的onCreate()方法同步
     */
    public void onCreate(Bundle bundle) {
        Log.e("dsy", app.map + "");
        if (app.map == null)
            return;
        if (app.map.size() <= 0)// 这里表示没有上次未完成的计时
            return;
        long time = System.currentTimeMillis()
                - app.map.get(CTIME)
                - app.map.get(TIME);
        app.map.clear();
        if (time > 0)
            return;
        else {
            initTimer();
            this.time = Math.abs(time);
            t.schedule(tt, 0, 1000);
            this.setTextSize(12);
            this.setText(time + textafter);
            this.setEnabled(false);
        }
    }

    /** * 设置计时时候显示的文本 */
    public TimeButton setTextAfter(String text1) {
        this.textafter = text1;
        return this;
    }

    /** * 设置点击之前的文本 */
    public TimeButton setTextBefore(String text0) {
        this.textbefore = text0;
        this.setText(textbefore);
        return this;
    }

    /**
     * 设置到计时长度
     *
     * @param length
     *            时间 默认毫秒
     * @return
     */
    public TimeButton setLength(long length) {
        this.length = length;
        return this;
    }
    /*

*
*/
}

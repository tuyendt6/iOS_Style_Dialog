package com.maple.iosdialog.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.maple.iosdialog.R;

/**
 * 警告框式Edit Dialog [ 标题 + 输入框 + 消息文本 + 左按钮 + 右按钮 ]
 *
 * @author maple
 * @time 17/3/23
 */
public class AlertEditDialog {
    private LinearLayout lLayout_bg;
    private TextView txt_title;
    private TextView txt_msg;
    private EditText et_text;
    private Button btn_neg;
    private Button btn_pos;
    private ImageView img_line;

    private Context context;
    private Dialog dialog;
    private Display display;
    private boolean showTitle = false;
    private boolean showMsg = false;
    private boolean showPosBtn = false;
    private boolean showNegBtn = false;

    public AlertEditDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public AlertEditDialog builder() {
        View view = LayoutInflater.from(context).inflate(R.layout.view_alert_edit_dialog, null);

        // get custom Dialog layout
        lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setVisibility(View.GONE);
        txt_msg = (TextView) view.findViewById(R.id.txt_msg);
        txt_msg.setVisibility(View.GONE);
        et_text = (EditText) view.findViewById(R.id.et_text);
//        et_text.setVisibility(View.GONE);
        btn_neg = (Button) view.findViewById(R.id.btn_neg);
        btn_neg.setVisibility(View.GONE);
        btn_pos = (Button) view.findViewById(R.id.btn_pos);
        btn_pos.setVisibility(View.GONE);
        img_line = (ImageView) view.findViewById(R.id.img_line);
        img_line.setVisibility(View.GONE);

        // set Dialog style
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);

        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.85), LayoutParams.WRAP_CONTENT));

        return this;
    }

    public AlertEditDialog setTitle(String title) {
        showTitle = true;
        if (TextUtils.isEmpty(title)) {
            txt_title.setText("Alert");
        } else {
            txt_title.setText(title);
        }
        return this;
    }

    public AlertEditDialog setMsg(String msg) {
        showMsg = true;
        if (TextUtils.isEmpty(msg)) {
            txt_msg.setText("body text");
        } else {
            txt_msg.setText(msg);
        }
        return this;
    }

    public AlertEditDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public AlertEditDialog setPositiveButton(String text, final OnClickListener listener) {
        showPosBtn = true;
        if (TextUtils.isEmpty(text)) {
            btn_pos.setText("OK");
        } else {
            btn_pos.setText(text);
        }
        btn_pos.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public AlertEditDialog setNegativeButton(String text, final OnClickListener listener) {
        showNegBtn = true;
        if (TextUtils.isEmpty(text)) {
            btn_neg.setText("Cancel");
        } else {
            btn_neg.setText(text);
        }
        btn_neg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public interface EditTextCallListener {
        void callBack(String str);
    }

    public AlertEditDialog setEditCallListener(final EditTextCallListener callListener) {
        et_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                callListener.callBack(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return this;
    }


    private void setLayout() {
        if (!showTitle && !showMsg) {
            txt_title.setText("Alert");
            txt_title.setVisibility(View.VISIBLE);
        }

        if (showTitle) {
            txt_title.setVisibility(View.VISIBLE);
        }

        if (showMsg) {
            txt_msg.setVisibility(View.VISIBLE);
        }

        if (!showPosBtn && !showNegBtn) {
            btn_pos.setText("OK");
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
            btn_pos.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

        if (showPosBtn && showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_right_selector);
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.drawable.alertdialog_left_selector);
            img_line.setVisibility(View.VISIBLE);
        }

        if (showPosBtn && !showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }

        if (!showPosBtn && showNegBtn) {
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }
    }

    public void show() {
        setLayout();
        dialog.show();
    }

}
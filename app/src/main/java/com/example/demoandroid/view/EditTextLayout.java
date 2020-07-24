package com.example.demoandroid.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.demoandroid.R;

public class EditTextLayout extends FrameLayout {
    private TextView tvHintView;
    private EditText editText;
    private LinearLayout llTwoItemContainer;
    private ImageView ivPreIcon;
    private ImageView ivAfterIcon;
    private LinearLayout llOneItemContainer;
    private TextView tvSuffix;
    private View lineView;
    private TextView tvErrorView;

    private OnFocusChangeListener onFocusChangeListener;

    //提示语
    private String hint;
    //提示语的动画是否只执行一次
    private boolean once;
    //后缀的参数
    private String suffixText;
    private int suffixTextColor;
    private int suffixTextSize;  //sp
    private int suffixBackgroundColor;
    //线的参数
    private int lineColor;
    private int lineActiveColor;
    private int lineErrorColor;
    private int lineHeight;//线的高度,px

    private OnHandleClickListener onPreIconListener;
    private OnHandleClickListener onAfterIconListener;


    public EditTextLayout(Context context) {
        this(context, null);
    }

    public EditTextLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public EditTextLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.edit_text_layout, this);
        tvHintView = findViewById(R.id.tvHintView);
        editText = findViewById(R.id.editText);
        llTwoItemContainer = findViewById(R.id.llTwoItemContainer);
        ivPreIcon = findViewById(R.id.ivPreIcon);
        ivAfterIcon = findViewById(R.id.ivAfterIcon);
        llOneItemContainer = findViewById(R.id.llOneItemContainer);
        tvSuffix = findViewById(R.id.tvSuffix);
        lineView = findViewById(R.id.lineView);
        tvErrorView = findViewById(R.id.tvErrorView);

        initDefaultValues();
        initListeners();
    }

    private void initListeners() {
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                this.lineView.setBackgroundColor(lineActiveColor);
            } else {
                this.lineView.setBackgroundColor(lineColor);
            }

            if (onFocusChangeListener != null) {
                onFocusChangeListener.onFocusChange(v, hasFocus);
            }
        });

        //清空editText
        ivPreIcon.setOnClickListener(v -> {
            if (onPreIconListener != null && onPreIconListener.onClick(v)) {
                return;
            }
            editText.setText("");
        });
        //EditText密码可见/不可见
        ivAfterIcon.setOnClickListener(v -> {
            if(onAfterIconListener != null && onAfterIconListener.onClick(v)){
                return;
            }

            if (editText.getInputType() == EditorInfo.TYPE_TEXT_VARIATION_PASSWORD) {
                editText.setInputType(EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                ivAfterIcon.setImageResource(R.mipmap.ic_password_visible);
            } else {
                editText.setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
                editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                ivAfterIcon.setImageResource(R.mipmap.ic_password_hidden);
            }

            //光标位置设置为最后
            editText.setSelection(editText.getText().length());
        });
    }

    private void initDefaultValues() {
        //线
        hint = editText.getHint().toString();
        lineColor = Color.parseColor("#DFE2E5");
        lineColor = Color.parseColor("#5124E8");

        //suffix
        suffixBackgroundColor = Color.parseColor("#ffffff");
        suffixTextSize = 14;
        suffixTextColor = Color.parseColor("#111112");
    }

    public void setOnFocusChangeListener(OnFocusChangeListener listener) {
        onFocusChangeListener = listener;
    }

    public void setEditTextHint(String hint) {
        this.hint = "";
        editText.setHint(hint);
    }

    public void clearEditTextHint() {
        editText.setHint("");
    }

    public void restoreEditTextHint() {
        editText.setHint(hint);
    }

    public void moveTextViewOnce() {
        if (!once) {
            moveTextView();
            once = true;
        }
    }

    public void setLineColor(int color) {
        this.lineColor = color;
        this.lineView.setBackgroundColor(color);
    }

    public void setLineActiveColor(int color) {
        this.lineActiveColor = color;
        this.lineView.setBackgroundColor(color);
    }

    public void setLineErrorColor(int color){
        this.lineErrorColor = color;
        this.lineView.setBackgroundColor(color);
    }

    //单位px
    public void setLineHeight(int height) {
        this.lineHeight = height;
        ViewGroup.LayoutParams layoutParams = lineView.getLayoutParams();
        layoutParams.height = height;
        this.lineView.setLayoutParams(layoutParams);
    }
    public void setSuffixText(String suffixText) {
        this.suffixText = suffixText;
        tvSuffix.setText(suffixText);
    }
    public void setSuffixTextColor(int color) {
        this.suffixTextColor = color;
        this.tvSuffix.setTextColor(color);
    }
    public void setSuffixTextSize(int textSize) {
        this.suffixTextSize = textSize;
        this.tvSuffix.setTextSize(textSize);
    }
    public void setSuffixBackgroundColor(int color) {
        this.suffixBackgroundColor = color;
        this.tvSuffix.setBackgroundColor(color);
    }
    public void setEditTextInputType(int type) {
        this.editText.setInputType(type);
    }
    public void setPreIcon(int res) {
        this.ivPreIcon.setImageResource(res);
    }
    public void setAfterIcon(int res) {
        this.ivAfterIcon.setImageResource(res);
    }
    public void setErrorText(String errorText){
        this.tvErrorView.setText(errorText);
    }
    public void setErrorTextColor(int color){
        this.tvErrorView.setTextColor(color);
    }
    public void setErrorTextSize(int size){
        this.tvErrorView.setTextSize(size);
    }
    public void setErrorBackgroundColor(int color){
        this.tvErrorView.setBackgroundColor(color);
    }
    public void setErrorViewVisibility(int visibility){
        this.tvErrorView.setVisibility(visibility);
    }
    public void setOnPreIconListener(OnHandleClickListener onPreIconListener) {
        this.onPreIconListener = onPreIconListener;
    }
    public void setOnAfterIconListener(OnHandleClickListener onAfterIconListener) {
        this.onAfterIconListener = onAfterIconListener;
    }

    public void moveTextView() {
        int top1 = editText.getTop() + editText.getHeight() / 2;
        int top2 = tvHintView.getTop();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(tvHintView, "translationY", top1, top2).setDuration(80);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                tvHintView.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                tvHintView.setTextSize(14);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        objectAnimator.start();
    }

    public interface OnHandleClickListener {
        boolean onClick(View v);
    }

}

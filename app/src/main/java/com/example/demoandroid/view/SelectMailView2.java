package com.example.demoandroid.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.demoandroid.R;
import com.example.demoandroid.util.SizeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SelectMailView2 extends ViewGroup implements TextWatcher, View.OnKeyListener, View.OnTouchListener, View.OnFocusChangeListener {
    //邮箱格式正则
    private final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    public static final int TYPE_ITEM_FIRST = 100;      //第一个元素,label
    public static final int TYPE_ITEM_MIDDLE = 101;     //中间的元素,类型是TextView
    public static final int TYPE_ITEM_LAST = 102;       //最后一个元素,EditText

    private int cellSpace;  //单元格间距,单位px
    private int lineSpace;  //行间距,单位px

    //label
    private String labelText;
    private int labelTextColor;
    private int labelTextSize;

    private TextView labelView;
    private EditText editText;

    //list中的每一个元素都是某一行的view的集合
    private SparseArray<List<View>> mAllView = new SparseArray<>();
    //每一行的最高的高度
    private SparseArray<Integer> mLineHeight = new SparseArray<>();

    private TextWatcher textWatcher;
    private OnEmailHandleListener onEmailHandleListener;
    private OnRightIconClickListener onRightIconClickListener;


    public SelectMailView2(Context context) {
        this(context, null);
    }

    public SelectMailView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        cellSpace = SizeUtil.dip2px(context,4);
        lineSpace = SizeUtil.dip2px(context,6);

        labelText = "收件人：";
        labelTextColor = Color.parseColor("#6F7680");
        labelTextSize = 14;
    }

    public void setTextWatcher(TextWatcher textWatcher) {
        this.textWatcher = textWatcher;
    }

    public void setOnEmailHandleListener(OnEmailHandleListener listener) {
        this.onEmailHandleListener = listener;
    }

    public void setOnRightIconClickListener(OnRightIconClickListener listener){
        this.onRightIconClickListener = listener;
    }

    private void clearAllView() {
        for (int i = 0; i < mAllView.size(); i++) {
            List<View> lineChildren = mAllView.get(i);
            if (lineChildren != null) {
                lineChildren.clear();
            }
        }

        mLineHeight.clear();
    }

    private List<View> getLineList(int line) {
        List<View> lineChildren = mAllView.get(line);
        if (lineChildren == null) {
            lineChildren = new ArrayList<>();
            mAllView.append(line, lineChildren);
        }

        return lineChildren;
    }

    private void addLineView(int line, View view) {
        getLineList(line).add(view);
    }

    //计算每一行最高的高度
    private void calcLineHeight() {
        mLineHeight.clear();
        for (int i = 0; i < mAllView.size(); i++) {
            mLineHeight.append(i, calcMaxHeightForLine(mAllView.get(i)));
        }
    }

    //计算某一行的最大的高度
    private int calcMaxHeightForLine(List<View> list) {
        int max = 0;
        for (int i = 0; i < list.size(); i++) {
            View view = list.get(i);
            max = max > view.getMeasuredHeight() ? max : view.getMeasuredHeight();
        }

        return max;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        onRealMeasure(widthMeasureSpec,heightMeasureSpec);


        //测量子view
//        measureChildren(widthMeasureSpec, heightMeasureSpec);

        //清空
        clearAllView();

        //ViewGroup的宽度
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int line = 0;
        int curWidth = getPaddingLeft() + getPaddingRight();
        int curHeight = getPaddingTop() + getPaddingBottom();
        boolean isFirst = true;//是否是一行中的第一个元素
        int maxHeight = 0;// 每一行中高度最高的

        for (int i = 0; i < getChildCount() - 1; i++) {
            View child = getChildAt(i);
            int cw = child.getMeasuredWidth();
            int ch = child.getMeasuredHeight();

            if (curWidth + cw + (isFirst ? 0 : cellSpace) <= widthSize) {
                if((int)child.getTag() == TYPE_ITEM_LAST){
                    cw = widthSize - curWidth + (isFirst ? 0 : cellSpace);
                }

                curWidth = curWidth + cw + (isFirst ? 0 : cellSpace);
                isFirst = false;
                addLineView(line, child);
                maxHeight = maxHeight > ch ? maxHeight : ch;
            } else {//换行
                isFirst = true;
                curWidth = getPaddingLeft() + getPaddingRight();
                curHeight += maxHeight;
                maxHeight = 0;
                maxHeight = maxHeight > ch ? maxHeight : ch;
                line++;
                if (cw >= widthSize) {
                    addLineView(line, child);
                    line++;
                } else {
                    curWidth = curWidth + cw + (isFirst ? 0 : cellSpace);
                    isFirst = false;
                    addLineView(line, child);
                }
            }
        }

        //设置最后一个元素的宽度为剩下的最大宽度
        List<View> lastList = mAllView.get(mAllView.size() - 1);
        int n = lastList.size();
        int usedWidth = getPaddingLeft() + getPaddingRight() + n * cellSpace;
        for (int i =0;i < n;i++){
            View child = lastList.get(i);
            usedWidth += child.getMeasuredWidth();
        }
        int remainWidth = widthSize - usedWidth;
        View lastView = getChildAt(getChildCount() - 1);

        if(remainWidth >= SizeUtil.dip2px(getContext(),80)){
            int widthSpec = MeasureSpec.makeMeasureSpec(remainWidth,MeasureSpec.EXACTLY);
            lastView.measure(widthSpec,heightMeasureSpec);
            List<View> lastLineList = getLineList(mAllView.size() - 1);
            lastLineList.add(lastView);
        }else {//独占一行
            int widthSpec = MeasureSpec.makeMeasureSpec(widthSize - getPaddingLeft() - getPaddingRight(),MeasureSpec.EXACTLY);
            lastView.measure(widthSpec,heightMeasureSpec);
            List<View> lastLineList = getLineList(mAllView.size());
            lastLineList.add(lastView);
            curHeight += lastView.getMeasuredHeight();
        }


        //算ViewGroup的高度
        curHeight = curHeight + maxHeight + (mAllView.size() - 1) * lineSpace;

        //设置ViewGroup的尺寸
        setMeasuredDimension(widthSize, curHeight);

        calcLineHeight();
    }

    private void onRealMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int curWidth = 0;

        int cCount = getChildCount();
        for (int i = 0;i < cCount;i++){
            View child = getChildAt(i);
            ViewGroup.LayoutParams lp = child.getLayoutParams();




        }


    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int curX;
        int curY = getPaddingTop();

        for (int i = 0; i < mAllView.size(); i++) {
            //每一行的view集合
            List<View> lineList = mAllView.get(i);
            //每一行的最大的高度
            int maxHeight = mLineHeight.get(i);

            curX = getPaddingLeft();

            //摆放位置
            for (int j = 0; j < lineList.size(); j++) {
                View child = lineList.get(j);
                int w = child.getMeasuredWidth();
                int h = child.getMeasuredHeight();
                int y = curY + (maxHeight - h) / 2;
                child.layout(curX, y, curX + w, y + h);
                curX = curX + w + cellSpace;
            }

            curY = curY + maxHeight + lineSpace;
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        addLabelTextView();
        addEditText();
    }

    /**
     * @param text 文本内容
     * @param normal true:正常的邮箱格式, false:错误的邮箱格式
     * @return
     */
    public TextView addTextView(String text,boolean normal) {
        TextView tagView = new TextView(getContext());
        tagView.setTag(TYPE_ITEM_MIDDLE);
        tagView.setTag(R.id.tag_is_select,false);
        tagView.setText(text);
        tagView.setTextSize(14);
        tagView.setGravity(Gravity.CENTER);
        int leftPadding = SizeUtil.dip2px(getContext(),12);
        int rightPadding = leftPadding;
        tagView.setPadding(leftPadding,0,rightPadding,0);
        tagView.setLayoutParams( new LayoutParams(LayoutParams.WRAP_CONTENT, SizeUtil.dip2px(getContext(),24)));
        if(normal){
            tagView.setBackgroundColor(Color.parseColor("#111112"));
            tagView.setBackgroundResource(R.drawable.circle_shape_normal);
        }else {
            tagView.setBackgroundColor(Color.parseColor("#F56262"));
            tagView.setBackgroundResource(R.drawable.circle_shape_error);
        }
        tagView.setOnClickListener(v -> {
            Boolean isSelected = (boolean) tagView.getTag(R.id.tag_is_select);
            if(isSelected != null && isSelected){
                Toast.makeText(getContext(),"选中了," + tagView.getText().toString(),Toast.LENGTH_SHORT).show();
            }else {
                tagView.setTag(R.id.tag_is_select,true);
                tagView.setBackgroundResource(R.drawable.circle_shape_select);
                tagView.setTextColor(Color.parseColor("#ffffff"));
            }
        });
        addView(tagView, getChildCount() - 1);
        return tagView;
    }

    private void addLabelTextView() {
        labelView = new TextView(getContext());
        labelView.setText(labelText);
        labelView.setTag(TYPE_ITEM_FIRST);
        labelView.setTextColor(labelTextColor);
        labelView.setTextSize(labelTextSize);
        labelView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        addView(labelView, 0);
    }

    private void addEditText() {
        editText = new EditText(getContext());
        editText.setTag(TYPE_ITEM_LAST);
        editText.setBackground(null);
        editText.setHint("请输入邮箱");
        editText.setTextSize(14);
        editText.setTextColor(Color.parseColor("#111112"));
        editText.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
        editText.setPadding(0,6,0,0);
        editText.setCompoundDrawablePadding(10);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, SizeUtil.dip2px(getContext(),36)));
        editText.addTextChangedListener(this);
        editText.setOnKeyListener(this);
        editText.setOnTouchListener(this);
        editText.setOnFocusChangeListener(this);
        editText.setCompoundDrawablesWithIntrinsicBounds(null,null, ContextCompat.getDrawable(getContext(),R.mipmap.ic_recipient_add),null);
        addView(editText, 1);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (textWatcher != null) {
            textWatcher.beforeTextChanged(s, start, count, after);
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (textWatcher != null) {
            textWatcher.onTextChanged(s, start, before, count);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        String text = s.toString();
        if (text == null || text.length() == 1)
            return;

        if (!text.endsWith(",") && !text.endsWith(",") && !text.endsWith(";"))
            return;

        //1 邮箱格式是否有效
        boolean validEmail = false;
        String emailOrNicker = null;
        if (onEmailHandleListener != null && (emailOrNicker = onEmailHandleListener.onEmailValid(text)) != null) {
            validEmail = true;
        } else if (isEmail(text.substring(0, text.length() - 1))) {
            validEmail = true;
            emailOrNicker = text.substring(0, text.length() - 1);
        }

        //2 有效的邮箱,添加view
        addTextView(emailOrNicker,validEmail);
        editText.setText("");

        if(textWatcher != null){
            textWatcher.afterTextChanged(s);
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_UP){
            if(getChildCount() > 2){
                removeViewAt(getChildCount() - 2);
            }
        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.e("zh33","action=" + event.getAction());
        Drawable[] drawables = editText.getCompoundDrawables();
        if(drawables == null || drawables.length == 0)
            return false;

        Drawable rightDrawable = drawables[2];
        if(rightDrawable == null)
            return false;

        Rect rect = rightDrawable.getBounds();
        if(rect == null)
            return false;

        int drawableWidth = rect.right - rect.left;
        int x = (int) event.getX();

        //点击到右边的drawable上了
        if(x > (v.getWidth() - drawableWidth - 20) && event.getAction() == MotionEvent.ACTION_UP && onRightIconClickListener != null){
            onRightIconClickListener.onClick(editText);
        }

        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        Drawable right = null;
        if(hasFocus){
            right = ContextCompat.getDrawable(getContext(),R.mipmap.ic_recipient_add);
            String content = editText.getText() == null ? "" : editText.getText().toString();
            editText.setSelection(content.length());
        }
        editText.setCompoundDrawablesWithIntrinsicBounds(null,null, right,null);
    }

    private boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    public interface OnEmailHandleListener {
        /**
         * 是否是有效的email
         *
         * @param email 待验证的email
         * @return 如果email有效, 则返回email或者返回昵称, 如果email无效,则返回null
         */
        String onEmailValid(String email);

    }

    /**
     * 右边的icon点击事件
     */
    public interface OnRightIconClickListener{
        void onClick(View view);
    }

}

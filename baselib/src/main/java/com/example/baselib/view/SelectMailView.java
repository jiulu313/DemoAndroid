package com.example.baselib.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SelectMailView extends ViewGroup {
    private int cellSpace;  //单元格间距,单位px
    private int lineSpace;  //行间距,单位px

    //label
    private String labelText;
    private int labelTextColor;
    private int labelTextSize;

    //list中的每一个元素都是某一行的view的集合
    private SparseArray<List<View>> mAllView = new SparseArray<>();
    //每一行的最高的高度
    private SparseArray<Integer> mLineHeight = new SparseArray<>();


    public SelectMailView(Context context) {
        this(context, null);
    }

    public SelectMailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        cellSpace = 40;
        lineSpace = 20;

        labelText = "收件人：";
        labelTextColor = Color.parseColor("#ff0000");
        labelTextSize = 16;

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
    private void calcLineHeight(){
        mLineHeight.clear();
        for (int i = 0;i < mAllView.size();i++){
            mLineHeight.append(i,calcMaxHeightForLine(mAllView.get(i)));
        }
    }

    //计算某一行的最大的高度
    private int calcMaxHeightForLine(List<View> list){
        int max = 0;
        for (int i = 0 ;i < list.size();i++){
            View view = list.get(i);
            max = max > view.getMeasuredHeight() ? max : view.getMeasuredHeight();
        }

        return max;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //测量子view
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        //清空
        clearAllView();

        //ViewGroup的宽度
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int line = 0;
        int curWidth = getPaddingLeft() + getPaddingRight();
        int curHeight = getPaddingTop() + getPaddingBottom();
        boolean isFirst = true;//是否是一行中的第一个元素
        int maxHeight = 0;// 每一行中高度最高的

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int cw = child.getMeasuredWidth();
            int ch = child.getMeasuredHeight();

            if (curWidth + cw + (isFirst ? 0 : cellSpace) <= widthSize) {
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

        //算ViewGroup的高度
        curHeight = curHeight + maxHeight + (mAllView.size() - 1) * lineSpace;

        //设置ViewGroup的尺寸
        setMeasuredDimension(widthSize, curHeight);

        calcLineHeight();
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

    private void addLabelTextView() {
        TextView labelView = new TextView(getContext());
        labelView.setText(labelText);
        labelView.setTextColor(labelTextColor);
        labelView.setTextSize(labelTextSize);
        labelView.setBackgroundColor(Color.parseColor("#aaee22"));
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        labelView.setLayoutParams(lp);
        addView(labelView, 0);
    }

    private void addEditText() {
        EditText editText = new EditText(getContext());
        editText.setBackgroundColor(Color.parseColor("#fa0dfa"));
        editText.setHint("请输入邮箱");
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        editText.setLayoutParams(lp);
        addView(editText, 1);
    }

    public void addTextView(String text){
        TextView tagView = new TextView(getContext());
        tagView.setText(text);
        tagView.setBackgroundColor(Color.parseColor("#cc33aa"));
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        tagView.setLayoutParams(lp);
        int count = getChildCount();
        addView(tagView,count - 1);
    }


}

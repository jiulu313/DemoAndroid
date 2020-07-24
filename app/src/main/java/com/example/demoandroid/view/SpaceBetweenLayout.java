package com.example.demoandroid.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.demoandroid.R;

import java.util.ArrayList;
import java.util.List;


public class SpaceBetweenLayout extends ViewGroup {
    private int lineCount;      //一行有多少个view
    private int hDistance;      //单元格之间的水平距离
    private int vDistance;      //单元格之间的垂直距离

    private int itemHeight;     //单元格的高度

    private SparseArray<List<View>> mAllViews = new SparseArray<>();

    public SpaceBetweenLayout(Context context) {
        this(context,null);
    }

    public SpaceBetweenLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        lineCount = 4;
        itemHeight = dip2px(context,34);
        hDistance = dip2px(context,6);
        vDistance = dip2px(context,10);
    }

    public void addTags(List<LabelTagBean> beanList){
        if(beanList == null || beanList.isEmpty()){
            return;
        }

        for (int i =0;i < beanList.size();i++){
            addView(newTagTextView(beanList.get(i)));
        }
    }

    private TextView newTagTextView(LabelTagBean bean){
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, dip2px(getContext(),28));
        lp.leftMargin = dip2px(getContext(),12);
        TextView tagView = new TextView(getContext());
        tagView.setLayoutParams(lp);
        tagView.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
        tagView.setText(bean.name);

        tagView.setBackgroundColor(Color.parseColor("#F0F0F0"));
        tagView.setTextColor(Color.parseColor("#ff00ff"));
        tagView.setLayoutParams(lp);
        return tagView;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int childWidth = (widthSize - (lineCount - 1) * hDistance - getPaddingLeft() - getPaddingRight()) / lineCount;
        int childHeight = itemHeight;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int childWidthSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
            int childHeightSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
            child.measure(childWidthSpec, childHeightSpec);
        }

        //行数
        int num = getLineCount();

        //分行
        int lineIndex = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            List<View> list = mAllViews.get(lineIndex);
            if (list == null) {
                list = new ArrayList<>();
                mAllViews.append(lineIndex, list);
            }

            if (list.size() < lineCount) {
                list.add(child);
            } else {
                lineIndex++;
                List<View> listTmp = mAllViews.get(lineIndex);
                if (listTmp == null) {
                    listTmp = new ArrayList<>();
                    listTmp.add(child);
                    mAllViews.append(lineIndex, listTmp);
                }
            }
        }

        //计算父view的高度
        int heightSize = num * itemHeight + (num - 1) * vDistance + getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int num = getLineCount();

        int x;
        int y;
        for (int row = 0; row < num; row++) {
            List<View> list = mAllViews.get(row);
            y = getPaddingTop() + (row * itemHeight + row * vDistance);
            for (int n = 0; n < list.size(); n++) {
                View child = list.get(n);
                int width = child.getMeasuredWidth();
                int height = child.getMeasuredHeight();

                x = getPaddingLeft() + (n * width + n * hDistance);
                child.layout(x, y, x + width, y + height);
            }
        }
    }

    //获取行数
    private int getLineCount() {
        int cCount = getChildCount();
        int m = cCount / lineCount;
        int n = cCount % lineCount;
        return m + (n > 0 ? 1 : 0);
    }


    /**
     * px 转为 dip.
     */
    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * dp 转为 px.
     */
    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px 转为 sp.
     */
    public static int px2sp(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp 转为 px.
     */
    public static int sp2px(Context context, float spValue) {
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scale + 0.5f);
    }

    public static class LabelTagBean{
        public int type;
        public String name;
        public boolean selected;

        public LabelTagBean(int type,String name){
            this.type = type;
            this.name = name;
        }
    }
}

package com.explore.archana.swimmingtechniques.coustomviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by archana on 8/22/2015.
 */
public class CustomImageView extends ImageView{
    public CustomImageView(Context context) {
        super(context);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width
    }
}

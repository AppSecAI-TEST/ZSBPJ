package com.lcc.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;

import zsbpj.lccpj.utils.DensityUtil;

public class TextImageView extends android.widget.TextView {

    public TextImageView(Context context) {
        super(context);
    }

    public TextImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TextImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private ImageSpan createImageSpan(int width, int resId) {
        int pxWidth = DensityUtil.dip2px(getContext(), width);
        Drawable drawable = this.getResources().getDrawable(resId);
        if (drawable!=null){
            drawable.setBounds(0, 0, pxWidth, pxWidth);
            return new ImageSpan(drawable);
        }
        return null;
    }

    public void setTextImageStart(int width, int resId, String text) {
        ImageSpan span = createImageSpan(width, resId);
        SpannableString spanStr = new SpannableString(" " + text);
        spanStr.setSpan(span, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        setText(spanStr);
    }

    public void setTextImageEnd(int width, int resId, String text) {
        ImageSpan span = createImageSpan(width, resId);
        SpannableString spanStr = new SpannableString(text + " ");
        spanStr.setSpan(span, text.length(), text.length() + 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        setText(spanStr);
    }
}


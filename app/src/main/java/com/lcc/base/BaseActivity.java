package com.lcc.base;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.WindowManager;
import com.lcc.activity.R;
import com.lcc.utils.PreferenceUtils;
import com.lcc.utils.SystemBarTintManager;
import com.lcc.utils.ThemeUtils;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年12月15日10:47:52
 * Description:    BaseActivity
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected PreferenceUtils preferenceUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        preferenceUtils=PreferenceUtils.getInstance(this);
        initTheme();
        super.onCreate(savedInstanceState);
        initWindow(isopen());
        setContentView(getLayoutView());
    }

    /**
     * 设置是否打开沉浸式
     */
    protected abstract boolean isopen();

    /**
     * 设置程序的布局文件
     */
    protected abstract int getLayoutView();

    private void initTheme(){
        ThemeUtils.Theme theme = getCurrentTheme();
        ThemeUtils.changTheme(this, theme);
    }

    protected ThemeUtils.Theme getCurrentTheme(){
        int value = preferenceUtils.getIntParam(getString(R.string.change_theme_key), 0);
        return ThemeUtils.Theme.mapValueToTheme(value);
    }

    /**
     * 获取沉浸式的颜色为系统的颜色
     */
    public int getStatusBarColor(){
        return getColorPrimary();
    }

    /**
     * 获取当前的系统颜色
     */
    public int getColorPrimary(){
        TypedValue typedValue = new  TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    /**
     * 沉浸式的相关设置，我在这直接给他设置一个开关
     */
    @TargetApi(19)
    private void initWindow(boolean open){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT&&open){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(getStatusBarColor());
            tintManager.setStatusBarTintEnabled(true);
        }
    }

    protected AlertDialog.Builder generateDialogBuilder(){
        ThemeUtils.Theme theme = getCurrentTheme();
        AlertDialog.Builder builder;
        int style = R.style.RedDialogTheme;
        switch (theme){
            case BROWN:
                style = R.style.BrownDialogTheme;
                break;
            case BLUE:
                style = R.style.BlueDialogTheme;
                break;
            case BLUE_GREY:
                style = R.style.BlueGreyDialogTheme;
                break;
            case YELLOW:
                style = R.style.YellowDialogTheme;
                break;
            case DEEP_PURPLE:
                style = R.style.DeepPurpleDialogTheme;
                break;
            case PINK:
                style = R.style.PinkDialogTheme;
                break;
            case GREEN:
                style = R.style.GreenDialogTheme;
                break;
            default:
                break;
        }
        builder = new AlertDialog.Builder(this, style);
        return builder;
    }
}

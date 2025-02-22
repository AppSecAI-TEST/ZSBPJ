package com.lcc.base;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.lcc.activity.R;
import com.lcc.rx.RxService;
import com.lcc.utils.NetWorkUtils;
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
    protected boolean isStartAnim = true;
    protected boolean isCloseAnim = true;
    public final static String IS_START_ANIM = "IS_START_ANIM";
    public final static String IS_CLOSE_ANIM = "IS_CLOSE_ANIM";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        parseIntent(getIntent());
        showActivityInAnim();
        preferenceUtils = PreferenceUtils.getInstance(this);
        initTheme();
        super.onCreate(savedInstanceState);
        RxService.getInstance().addCompositeSub(getTaskId());
        initWindow(Open());
        setContentView(getLayoutView());
        initView();
    }

    protected abstract void initView();

    /**
     * 设置是否打开沉浸式
     */
    protected abstract boolean Open();

    /**
     * 设置程序的布局文件
     */
    protected abstract int getLayoutView();

    private void initTheme() {
        ThemeUtils.Theme theme = getCurrentTheme();
        ThemeUtils.changTheme(this, theme);
    }

    protected ThemeUtils.Theme getCurrentTheme() {
        int value = preferenceUtils.getIntParam(getString(R.string.change_theme_key), 0);
        return ThemeUtils.Theme.mapValueToTheme(value);
    }

    /**
     * 获取沉浸式的颜色为系统的颜色
     */
    public int getStatusBarColor() {
        return getColorPrimary();
    }

    /**
     * 获取当前的系统颜色
     */
    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    /**
     * 沉浸式的相关设置，我在这直接给他设置一个开关
     */
    @TargetApi(19)
    private void initWindow(boolean open) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && open) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintColor(getStatusBarColor());
            tintManager.setStatusBarTintEnabled(true);
        }
    }

    /**
     * 设置toolbar的基本属性
     */
    protected void setUpCommonBackTooblBar(int toolbarId, String title) {
        Toolbar mToolbar = (Toolbar) findViewById(toolbarId);
        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);
        toolBarAsBackButton(mToolbar);
    }

    /**
     * toolbar点击返回，模拟系统返回
     * 设置toolbar 为箭头按钮
     */
    public void toolBarAsBackButton(Toolbar toolbar) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    /**
     * 检测网络
     */
    protected void checkNetWork() {
        if (!NetWorkUtils.isNetworkConnected(BaseActivity.this)) {
            Toast.makeText(BaseActivity.this, "当前无网络", Toast.LENGTH_SHORT).show();
        }
        if (NetWorkUtils.isWifiConnected(BaseActivity.this)) {
            //是移动网络做一些事情。
        }
    }

    protected AlertDialog.Builder generateDialogBuilder() {
        ThemeUtils.Theme theme = getCurrentTheme();
        AlertDialog.Builder builder;
        int style = R.style.RedDialogTheme;
        switch (theme) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxService.getInstance().removeCompositeSub(getTaskId());
    }

    protected void showActivityInAnim(){
        if (isStartAnim) {
            overridePendingTransition(R.anim.activity_down_up_anim, R.anim.activity_exit_anim);
        }
    }

    protected void showActivityExitAnim(){
        if (isCloseAnim) {
            overridePendingTransition(R.anim.activity_exit_anim, R.anim.activity_up_down_anim);
        }
    }

    private void parseIntent(Intent intent){
        if (intent != null) {
            isStartAnim = intent.getBooleanExtra(IS_START_ANIM, true);
            isCloseAnim = intent.getBooleanExtra(IS_CLOSE_ANIM, true);
        }
    }

    @Override
    public void finish() {
        super.finish();
        showActivityExitAnim();
    }
}

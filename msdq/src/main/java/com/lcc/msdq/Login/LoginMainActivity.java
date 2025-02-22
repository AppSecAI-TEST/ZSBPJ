package com.lcc.msdq.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import com.lcc.adapter.TabViewPagerAdapter;
import com.lcc.base.BaseActivity;
import com.lcc.msdq.MainActivity;
import com.lcc.msdq.R;
import com.lcc.msdq.comments.CommentsActivity;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2017年04月20日13:43:06
 * Description:  新的登录的界面
 */
public class LoginMainActivity extends BaseActivity {
    private String result;

    public static void startLoginMainActivity(String r, Activity startActivity) {
        Intent intent = new Intent(startActivity, LoginMainActivity.class);
        intent.putExtra("result", r);
        startActivity.startActivity(intent);
    }

    @Override
    protected void initView() {
        result = getIntent().getStringExtra("result");
        View close = findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginMainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        if (!TextUtils.isEmpty(result)){
            close.setVisibility(View.GONE);
        }else {
            close.setVisibility(View.VISIBLE);
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setupViewPager();
    }

    @Override
    protected boolean Open() {
        return false;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.main_login_layout;
    }

    /**
     * 设置tab下的viewpager
     */
    private void setupViewPager() {
        final ViewPager login_viewpager = (ViewPager) findViewById(R.id.login_viewpager);
        setupViewPager(login_viewpager);
        TabLayout login_tabs = (TabLayout) findViewById(R.id.login_tabs);
        login_tabs.setupWithViewPager(login_viewpager);
        login_tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                final 	int f=tab.getPosition();
                login_viewpager.setCurrentItem(f);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * 设置登录和注册的界面
     */
    private void setupViewPager(ViewPager viewPager) {
        TabViewPagerAdapter adapter = new TabViewPagerAdapter(getSupportFragmentManager());
        if (!TextUtils.isEmpty(result)){
            adapter.addFrag( FragmentLogin.newInstance(result), "登录");
            adapter.addFrag( FragmentRegister.newInstance(result), "注册");
        }else {
            adapter.addFrag( FragmentLogin.newInstance(""), "登录");
            adapter.addFrag( FragmentRegister.newInstance(""), "注册");
        }

        viewPager.setAdapter(adapter);
        if (!TextUtils.isEmpty(result) && result.equals("tv_register")){
            viewPager.setCurrentItem(1);
        }
    }

}

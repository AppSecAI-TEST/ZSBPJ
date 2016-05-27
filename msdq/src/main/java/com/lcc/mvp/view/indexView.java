package com.lcc.mvp.view;

import com.lcc.entity.ActivityEntity;

import java.util.List;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2015年11月21日15:28:25
 * Description:  开始或者结束轮训器
 */
public interface IndexView {

    /**
     * 登录失败：错误信息
     */
    void getLoginFail(String msg);

    /**
     * 获取成功
     */
    void getSuccess(List<ActivityEntity> list);

}

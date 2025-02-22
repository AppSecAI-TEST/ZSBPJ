package view.lcc.tyzs.mvp.view;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2017年04月15日20:00:59
 * Description:
 */
public interface JifenListView {

    void JifenListLoading();

    void JifenListSuccess(String msg);

    void JifenListFail(String msg);

    void NetWorkErr(String msg);

    void refreshOrLoadFail(String msg);

    void refreshDataSuccess(String msg);

    void loadMoreWeekDataSuccess(String msg);
}

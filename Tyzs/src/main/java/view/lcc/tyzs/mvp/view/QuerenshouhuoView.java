package view.lcc.tyzs.mvp.view;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:         2017年04月15日20:00:59
 * Description:
 */
public interface QuerenshouhuoView {

    void QuerenshouhuoLoading();

    void QuerenshouhuoSuccess(String msg);

    void QuerenshouhuoFail(String msg);

    void NetWorkErr(String msg);
}

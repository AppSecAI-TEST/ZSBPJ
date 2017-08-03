package view.lcc.tyzs.base;

import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.PopupWindow;

import view.lcc.tyzs.R;

/**
 * Author:       梁铖城
 * Email:        1038127753@qq.com
 * Date:
 * Description:
 */
public class BaseActivity extends FragmentActivity {

    public void showSnackbar(View view, String string) {
        Snackbar.make(view, string, Snackbar.LENGTH_LONG).show();
    }

    public PopupWindow createPopupWindow(View view, int width, int height) {
        PopupWindow popupWindow = new PopupWindow(getBaseContext());
        popupWindow.setContentView(view);
        popupWindow.setWidth(width);
        popupWindow.setHeight(height);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.xlk));
        return popupWindow;
    }
}

package view.lcc.tyzs.ui.tizhi.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zkk.view.rulerview.RulerView;

import view.lcc.tyzs.R;
import view.lcc.tyzs.base.BaseActivity;

/**
 * Author:       |梁铖城
 * Email:        |1038127753@qq.com
 * Date:         |05-16 07:24
 * Description:  |
 */
public class TanshiTestMainActivity extends BaseActivity implements View.OnClickListener {
    //身高的view
    private RulerView ruler_height;
    //体重的view
    private RulerView ruler_weight;
    private TextView tv_register_info_height_value, tv_register_info_weight_value;

    private float shengao = 165;
    private float tizhong = 55;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tanshi_test_activity);
        ruler_height = (RulerView) findViewById(R.id.ruler_height);
        ruler_weight = (RulerView) findViewById(R.id.ruler_weight);

        tv_register_info_height_value = (TextView) findViewById(R.id.tv_register_info_height_value);
        tv_register_info_weight_value = (TextView) findViewById(R.id.tv_register_info_weight_value);

        ruler_height.setOnValueChangeListener(new RulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                tv_register_info_height_value.setText(value + "");
                shengao = value;
            }
        });


        ruler_weight.setOnValueChangeListener(new RulerView.OnValueChangeListener() {
            @Override
            public void onValueChange(float value) {
                tv_register_info_weight_value.setText(value + "");
                tizhong = value;
            }
        });

        ruler_height.setValue(165, 80, 250, 1);
        ruler_weight.setValue(55, 20, 200, 0.1f);
        findViewById(R.id.pb_next).setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pb_next:
                Intent intent = new Intent(TanshiTestMainActivity.this, TanshiTestMain2Activity.class);
                intent.putExtra("shengao", shengao);
                intent.putExtra("tizhong", tizhong);
                startActivity(intent);
                finish();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}

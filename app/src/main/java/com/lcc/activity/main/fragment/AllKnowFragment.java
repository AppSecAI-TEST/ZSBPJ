package com.lcc.activity.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lcc.activity.R;
import com.lcc.activity.data.FavListActivity;
import com.lcc.activity.data.TestActivity;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:    2015年12月15日10:47:52
 * Description:    百科
 */
public class AllKnowFragment  extends Fragment{


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_know, null);
        view.findViewById(R.id.zwcs).setOnClickListener(new My_btnListener());
        view.findViewById(R.id.wdsc).setOnClickListener(new My_btnListener());
        return  view;
    }

    private class My_btnListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){

                case R.id.zwcs:
                    startActivity(new Intent(getActivity(), TestActivity.class));
                    break;

                case R.id.wdsc:
                    startActivity(new Intent(getActivity(), FavListActivity.class));
                    break;
            }
        }
    }

}

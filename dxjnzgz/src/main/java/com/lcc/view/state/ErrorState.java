package com.lcc.view.state;


import com.lcc.dxjnzgz.R;

public class ErrorState extends AbstractShowState {
    @Override
    public void show(boolean animate) {
        showViewById(R.id.epf_error, animate);
    }

    @Override
    public void dismiss(boolean animate) {
        dismissViewById(R.id.epf_error, animate);
    }
}

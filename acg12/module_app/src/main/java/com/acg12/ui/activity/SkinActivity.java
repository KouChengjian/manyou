package com.acg12.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.acg12.ui.base.SkinBaseActivity;
import com.acg12.ui.views.SkinView;

public class SkinActivity extends SkinBaseActivity<SkinView> implements View.OnClickListener {

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        mView.bindData();
    }

}
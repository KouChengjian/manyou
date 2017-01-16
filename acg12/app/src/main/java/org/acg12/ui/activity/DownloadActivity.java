package org.acg12.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import org.acg12.R;
import org.acg12.config.Constant;
import org.acg12.ui.base.PresenterActivityImpl;
import org.acg12.views.DownloadView;

public class DownloadActivity extends PresenterActivityImpl<DownloadView> implements View.OnClickListener {


    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == Constant.TOOLBAR_ID){
            finish();
        }
    }

}
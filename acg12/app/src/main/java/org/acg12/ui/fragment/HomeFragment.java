package org.acg12.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import org.acg12.R;
import org.acg12.config.Config;
import org.acg12.config.Constant;
import org.acg12.ui.activity.DownloadActivity;
import org.acg12.ui.base.PresenterFragmentImpl;
import org.acg12.views.HomeView;

public class HomeFragment extends PresenterFragmentImpl<HomeView> implements Toolbar.OnMenuItemClickListener ,
        View.OnClickListener {

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == Constant.TOOLBAR_ID){
            Config.navigationEventBus().post(true);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_search:

                break;
            case R.id.menu_main_download:
                startAnimActivity(DownloadActivity.class);
                break;
        }
        return false;
    }
}

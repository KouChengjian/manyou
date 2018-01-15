package org.acg12.ui.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.acg12.kk.listener.HttpRequestListener;
import com.acg12.kk.listener.ItemClickSupport;

import org.acg12.R;
import org.acg12.conf.Constant;
import org.acg12.entity.Video;
import org.acg12.net.HttpRequestImpl;
import com.acg12.common.ui.base.BaseActivity;
import org.acg12.ui.views.PreviewBangumiView;

public class PreviewBangumiActivity extends BaseActivity<PreviewBangumiView> implements View.OnClickListener,
        ItemClickSupport.OnItemClickListener {

    private String bangumiId = "";
    private boolean hasShow = false;

    @Override
    public void create(Bundle savedInstance) {
        super.create(savedInstance);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        bangumiId = getIntent().getExtras().getString("bangumiId");
        refresh();
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("video", mView.getVideo(position));
        startAnimActivity(PlayBungumiActivity.class, bundle);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == Constant.TOOLBAR_ID) {
            aminFinish();
        } else if (id == R.id.iv_video_arrow) {
            if (hasShow) {
                hasShow = false;
            } else {
                hasShow = true;
            }
            mView.hasShowDuration(hasShow);
        }
    }

    public void refresh() {
        HttpRequestImpl.getInstance().bangumiPreview(currentUser(), bangumiId, new HttpRequestListener<Video>() {
            @Override
            public void onSuccess(Video result) {
                mView.bindData(result);
                mView.stopRefreshLoadMore(true);
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                Log.e(mTag, msg);
                ShowToastView(msg);
                mView.stopRefreshLoadMore(false);
            }
        });
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}

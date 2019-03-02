package com.acg12.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.acg12.entity.CollectPaletteEntity;
import com.acg12.entity.Palette;
import com.acg12.lib.constant.Constant;
import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.ui.fragment.PresenterFragmentImpl;
import com.acg12.lib.utils.LogUtil;
import com.acg12.lib.widget.recycle.CommonRecycleview;
import com.acg12.lib.widget.recycle.IRecycleView;
import com.acg12.net.impl.HttpRequestImpl;
import com.acg12.ui.activity.PreviewPaletteActivity;
import com.acg12.ui.adapter.CollectPaletteAdapter;
import com.acg12.ui.adapter.SearchPaletteAdapter;
import com.acg12.ui.views.CollectPaletteView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/1/4 14:57
 * Description:
 */
public class CollectPaletteFragment extends PresenterFragmentImpl<CollectPaletteView>  implements IRecycleView.LoadingListener, SwipeRefreshLayout.OnRefreshListener
        , ItemClickSupport.OnItemClickListener, CommonRecycleview.IRecycleUpdataListener ,CollectPaletteAdapter.SearchPaletteListener {

    private int pageNum = 1;
    private boolean refresh = true;

    public static CollectPaletteFragment newInstance() {
        CollectPaletteFragment fragment = new CollectPaletteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        onLoadMore();
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("palette", mView.getObject(position));
//        startAnimActivity(PreviewPaletteActivity.class, bundle);
    }

    @Override
    public void onRecycleReload() {
        onLoadMore();
    }

    @Override
    public void onLoadMore() {
        pageNum++;
        refresh = false;
        requestData();
    }

    @Override
    public void onRefresh() {
        pageNum = 1;
        refresh = true;
        requestData();
    }

    @Override
    public void onClickCollect(int position) {
        CollectPaletteEntity palette = mView.getObject(position);
        if (palette.getIsCollect() == 1) {
            delCollectPalette(position, palette);
        } else {
            addCollectPalette(position, palette);
        }
    }

    public void requestData() {
        HttpRequestImpl.getInstance().collectPaletteList(pageNum, Constant.LIMIT_PAGER_20, new HttpRequestListener<List<CollectPaletteEntity>>() {
            @Override
            public void onSuccess(List<CollectPaletteEntity> result) {
                if (result.size() < Constant.LIMIT_PAGER) {
                    mView.stopLoading();
                }
                mView.bindData(result, refresh);
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                Log.e(mTag, msg);
//                ShowToastView(msg);
                mView.recycleException();
            }
        });
    }

    public void addCollectPalette(final int position, Palette palette) {
        Map<String, Object> params = new HashMap<>();
        params.put("boardId", palette.getBoardId());
        params.put("title", palette.getName()== null ? "" : palette.getName());
        params.put("sign", palette.getSign() == null ? "" : palette.getSign());
        params.put("num", palette.getNum());
        List<String> list = palette.getUrlAlbum();
        params.put("cover",      list.size() > 0 ? list.get(0) : "");
        params.put("thumImage1", list.size() > 1 ? list.get(1) : "");
        params.put("thumImage2", list.size() > 2 ? list.get(2) : "");
        params.put("thumImage3", list.size() > 3 ? list.get(3) : "");
        startLoading("收藏中...");
        HttpRequestImpl.getInstance().collectPaletteAdd(params, new HttpRequestListener<String>() {
            @Override
            public void onSuccess(String result) {
                stopLoading();
                mView.updataObject(position, 1);
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                stopLoading();
                ShowToast(msg);
                LogUtil.e(msg);
                if (errorcode == 5010001) {
                    mView.updataObject(position, 1);
                }
            }
        });
    }

    public void delCollectPalette(final int position, Palette palette) {
        startLoading("取消收藏中...");
        HttpRequestImpl.getInstance().collectPaletteDel(palette.getBoardId(), new HttpRequestListener<String>() {
            @Override
            public void onSuccess(String result) {
                stopLoading();
                mView.updataObject(position, 0);
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                stopLoading();
                ShowToast(msg);
                LogUtil.e(msg);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

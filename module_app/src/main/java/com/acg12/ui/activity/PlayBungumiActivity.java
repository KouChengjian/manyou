package com.acg12.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.acg12.lib.listener.HttpRequestListener;

import com.acg12.entity.Video;
import com.acg12.net.impl.HttpRequestImpl;
import com.acg12.ui.base.SkinBaseActivity;

//public class PlayBungumiActivity extends SkinBaseActivity<PlayBungumiView> {

//    private Video video;
//
//    @Override
//    public void create(Bundle savedInstance) {
//        super.create(savedInstance);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//    }
//
//    @Override
//    public void created(Bundle savedInstance) {
//        super.created(savedInstance);
//        video = (Video)getIntent().getExtras().getSerializable("video");
//        getVideoUrlOrXml(video.getAid());
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//    }
//
//    public void getVideoUrlOrXml(String av) {
//        HttpRequestImpl.getInstance().playBangumi(currentUser(),av, new HttpRequestListener<Video>() {
//            @Override
//            public void onSuccess(Video result) {
//                mView.setPlayer(result);
////                LogUtil.e(result.toString());
//            }
//
//            @Override
//            public void onFailure(int errorcode, String msg) {
//                Log.e(mTag , msg);
//                ShowToastView(msg);
//            }
//        });
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mView.getDanmakuVideoPlayer().release();
//        mView.getDanmakuVideoPlayer().getDanmakuView().release();
//    }
//}

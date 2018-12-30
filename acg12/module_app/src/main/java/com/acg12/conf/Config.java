package com.acg12.conf;


import android.content.Context;

import com.acg12.lib.utils.Toastor;
import com.acg12.lib.utils.glide.ImageLoadUtils;
import com.shuyu.gsyvideoplayer.utils.ListVideoUtil;

import com.acg12.net.impl.HttpRequestImpl;
import com.acg12.utlis.cache.Cache;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by DELL on 2016/11/25.
 */
public class Config {

    private Context mContext;
    static ListVideoUtil listVideoUtil;

    public static void init(Context mContext) {
        new Config(mContext);
    }

    public Config(Context mContext) {
        this.mContext = mContext;
//        eventbusUser = EventBus.builder().build();
//        eventbusNavigation = EventBus.builder().build();


        new Toastor(mContext);
        new HttpRequestImpl(mContext);
    }

    // init in activity
    public static void initListVideoUtil(Context mContext) {
        listVideoUtil = new ListVideoUtil(mContext);
        listVideoUtil.setHideActionBar(false);
        listVideoUtil.setHideStatusBar(false);
    }

    public static ListVideoUtil ListVideoUtilInstance() {
        return listVideoUtil;
    }

}
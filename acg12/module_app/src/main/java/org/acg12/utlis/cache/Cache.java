package org.acg12.utlis.cache;

import android.content.Context;

import com.acg12.lib.utils.ACache;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/18.
 */

public class Cache {

    private Context mContext;
    private static Cache instance;

    public Cache(Context mContext) {
        this.mContext = mContext;
        instance = this;
    }

    public static Cache getInstance() {
        return instance;
    }

    public void savaHistoryTags(List<String> list) {
        Gson gson = new Gson();
        ACache mCache = ACache.get(mContext);
        mCache.put("history_tags", gson.toJson(list, new TypeToken<List<String>>() {}.getType()));
    }

    public List<String>  getHistoryTags() {
        List<String>  searchList = new ArrayList<>();
        Gson gson = new Gson();
        ACache mCache = ACache.get(mContext);
        String banner = mCache.getAsString("history_tags");
        if (banner != null && !banner.isEmpty()) {
            Type type = new TypeToken<List<String>>() {}.getType();
            searchList.addAll((List<String>) gson.fromJson(banner, type));
        }
        return searchList;
    }
}
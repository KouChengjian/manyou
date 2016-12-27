package org.acg12.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.acg12.R;
import org.acg12.bean.Home;
import org.acg12.bean.Video;
import org.acg12.ui.adapter.base.TabAnimatViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2016/12/27.
 */
public class TabAnimatAdapter extends RecyclerView.Adapter<TabAnimatViewHolder> {

    private Context mContext;
    private List<Video> mList;
    private final LayoutInflater mInflater;

    public TabAnimatAdapter(Context mContext){
        this(mContext , new ArrayList<Video>());
    }

    public TabAnimatAdapter(Context mContext, List<Video> mList){
        this.mContext = mContext;
        this.mList = mList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void addAll(List<Video> mList){
        this.mList.addAll(mList);
    }

    public void add(Video home){
        this.mList.add(home);
    }

    public void setList(List<Video> mList){
        this.mList =  mList;
    }

    public List<Video> getList(){
        return mList;
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public TabAnimatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_tab_animat, parent, false);
        return new TabAnimatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TabAnimatViewHolder holder, int position) {
        holder.bindData(mContext ,mList.get(position));
    }
}

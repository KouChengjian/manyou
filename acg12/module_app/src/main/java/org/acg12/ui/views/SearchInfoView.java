package org.acg12.ui.views;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.ui.adapter.CommonPagerAdapter;
import com.acg12.lib.ui.base.PresenterHelper;
import com.acg12.lib.utils.LogUtil;
import com.acg12.lib.utils.glide.ImageLoadUtils;
import com.acg12.lib.widget.TipLayoutView;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;

import org.acg12.R;
import org.acg12.entity.Subject;
import org.acg12.ui.fragment.SearchAlbumFragment;
import org.acg12.ui.fragment.SearchAnimatFragment;
import org.acg12.ui.fragment.SearchBangunFragment;
import org.acg12.ui.fragment.SearchIntroFragment;
import org.acg12.ui.fragment.SearchPaletteFragment;
import org.acg12.utlis.BitmapBlurUtil;

import java.util.logging.Handler;

import butterknife.BindView;

/**
 * Created by DELL on 2017/1/3.
 */
public class SearchInfoView extends ViewImpl {

    @BindView(R.id.tipLayoutView)
    protected TipLayoutView mTipLayoutView;
    @BindView(R.id.coordinatorLayout)
    protected CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.iv_header_bg)
    ImageView iv_header_bg;
    @BindView(R.id.iv_header_pic)
    ImageView iv_header_pic;
    @BindView(R.id.tv_header_title)
    TextView tv_header_title;
    @BindView(R.id.tv_header_title_type)
    TextView tv_header_title_type;
    @BindView(R.id.tv_header_subtitle)
    TextView tv_header_subtitle;
    @BindView(R.id.tv_header_play_eps)
    TextView tv_header_play_eps;
    @BindView(R.id.tv_header_play_time)
    TextView tv_header_play_time;
    @BindView(R.id.search_toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.search_tabLayout)
    protected TabLayout mTabLayout;
    @BindView(R.id.search_viewpager)
    protected ViewPager mViewpager;
    @BindView(R.id.collapsingToolbarLayout)
    protected CollapsingToolbarLayout mCollapsingToolbarLayout;

    protected Fragment[] fragments;
    private String[] tabTitles;
    CommonPagerAdapter commonPagerAdapter;
    SearchIntroFragment searchIntroFragment;
    SearchAlbumFragment searchAlbumFragment;
    SearchPaletteFragment searchPaletteFragment;
    SearchBangunFragment searchBangunFragment;
    SearchAnimatFragment searchAnimatFragment;

    Bitmap mBlurBitmap;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_info;
    }

    @Override
    public void created() {
        super.created();
        toolbar.setNavigationIcon(R.mipmap.ic_action_back);
        mTipLayoutView.setContainer(mCoordinatorLayout);

        mCollapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);//设置还没收缩时状态下字体颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色
        mCollapsingToolbarLayout.setTitleEnabled(false);

        tabTitles = new String[]{"简介", "插画", "画册", "动画", "其他"};
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter, toolbar);
        mTipLayoutView.setOnReloadClick((TipLayoutView.OnReloadClick) mPresenter);
    }

    public void bindData(int id, String title, Subject subject) {
        toolbar.setTitle(subject.getName());
        tv_header_title.setText(subject.getNameCn().isEmpty() ? subject.getName() : subject.getNameCn());
        tv_header_title_type.setText(String.format("（%s）",subject.getTypeStatus()));
        tv_header_play_eps.setText(subject.getTypeEps());
        tv_header_play_time.setText(subject.getTypeTime());
        tv_header_subtitle.setText((subject.getAuthor() == null || subject.getAuthor().isEmpty() || subject.getAuthor().equals("null")) ? "???" : subject.getAuthor());
        String url = subject.getImage();
        ImageLoadUtils.glideLoading(url, iv_header_pic);
        ImageLoadUtils.glideLoading(url, new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                mBlurBitmap = BitmapBlurUtil.rsBlur(getContext(), resource, 24);
                iv_header_bg.setImageBitmap(mBlurBitmap);
            }
        });

        searchIntroFragment = SearchIntroFragment.newInstance(title, subject);
        searchAlbumFragment = SearchAlbumFragment.newInstance(title);
        searchPaletteFragment = SearchPaletteFragment.newInstance(title);
        searchBangunFragment = SearchBangunFragment.newInstance(title);
        searchAnimatFragment = SearchAnimatFragment.newInstance(title);
        fragments = new Fragment[]{searchIntroFragment, searchAlbumFragment, searchPaletteFragment, searchBangunFragment, searchAnimatFragment};

        commonPagerAdapter = new CommonPagerAdapter(((AppCompatActivity) getContext()).getSupportFragmentManager(), fragments, tabTitles);
        mViewpager.setAdapter(commonPagerAdapter);
        mViewpager.setOffscreenPageLimit(fragments.length);
        mTabLayout.setupWithViewPager(mViewpager);
    }

    public TabLayout getTabLayout() {
        return mTabLayout;
    }

    public TipLayoutView getTipLayoutView() {
        return mTipLayoutView;
    }

    public void onDestroy() {
        if (mBlurBitmap != null) {
            mBlurBitmap.recycle();
            mBlurBitmap = null;
        }
    }
}

// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class RecentFragment$$ViewInjector<T extends com.kcj.animationfriend.ui.fragment.RecentFragment> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131362108, "field 'mClearEditText'");
    target.mClearEditText = finder.castView(view, 2131362108, "field 'mClearEditText'");
    view = finder.findRequiredView(source, 2131362120, "field 'listview'");
    target.listview = finder.castView(view, 2131362120, "field 'listview'");
  }

  @Override public void reset(T target) {
    target.mClearEditText = null;
    target.listview = null;
  }
}

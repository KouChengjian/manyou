// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class SearchFragment$$ViewInjector<T extends com.kcj.animationfriend.ui.fragment.SearchFragment> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131231050, "field 'pager'");
    target.pager = finder.castView(view, 2131231050, "field 'pager'");
    view = finder.findRequiredView(source, 2131230916, "field 'tabs'");
    target.tabs = finder.castView(view, 2131230916, "field 'tabs'");
  }

  @Override public void reset(T target) {
    target.pager = null;
    target.tabs = null;
  }
}

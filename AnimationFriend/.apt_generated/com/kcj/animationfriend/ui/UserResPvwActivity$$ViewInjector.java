// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class UserResPvwActivity$$ViewInjector<T extends com.kcj.animationfriend.ui.UserResPvwActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230926, "field 'footView'");
    target.footView = view;
    view = finder.findRequiredView(source, 2131230925, "field 'recyclerView'");
    target.recyclerView = finder.castView(view, 2131230925, "field 'recyclerView'");
    view = finder.findRequiredView(source, 2131230869, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131230869, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131230872, "field 'myRefreshListView'");
    target.myRefreshListView = finder.castView(view, 2131230872, "field 'myRefreshListView'");
  }

  @Override public void reset(T target) {
    target.footView = null;
    target.recyclerView = null;
    target.toolbar = null;
    target.myRefreshListView = null;
  }
}
// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class SplashActivity$$ViewInjector<T extends com.kcj.animationfriend.ui.SplashActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230968, "field 'tv_login_version'");
    target.tv_login_version = finder.castView(view, 2131230968, "field 'tv_login_version'");
  }

  @Override public void reset(T target) {
    target.tv_login_version = null;
  }
}
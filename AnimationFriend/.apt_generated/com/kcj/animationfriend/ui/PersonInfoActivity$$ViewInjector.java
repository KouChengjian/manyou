// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class PersonInfoActivity$$ViewInjector<T extends com.kcj.animationfriend.ui.PersonInfoActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230939, "field 'rl_personl_add'");
    target.rl_personl_add = finder.castView(view, 2131230939, "field 'rl_personl_add'");
    view = finder.findRequiredView(source, 2131230931, "field 'personl_sex'");
    target.personl_sex = finder.castView(view, 2131230931, "field 'personl_sex'");
    view = finder.findRequiredView(source, 2131230927, "field 'll_resource_info'");
    target.ll_resource_info = finder.castView(view, 2131230927, "field 'll_resource_info'");
    view = finder.findRequiredView(source, 2131230938, "field 'rl_personl_send'");
    target.rl_personl_send = finder.castView(view, 2131230938, "field 'rl_personl_send'");
    view = finder.findRequiredView(source, 2131230936, "field 'rl_personl_res'");
    target.rl_personl_res = finder.castView(view, 2131230936, "field 'rl_personl_res'");
    view = finder.findRequiredView(source, 2131230928, "field 'iv_resource_icon'");
    target.iv_resource_icon = finder.castView(view, 2131230928, "field 'iv_resource_icon'");
    view = finder.findRequiredView(source, 2131230935, "field 'tv_personl_account'");
    target.tv_personl_account = finder.castView(view, 2131230935, "field 'tv_personl_account'");
    view = finder.findRequiredView(source, 2131230930, "field 'tv_resource_name'");
    target.tv_resource_name = finder.castView(view, 2131230930, "field 'tv_resource_name'");
    view = finder.findRequiredView(source, 2131230869, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131230869, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131230932, "field 'tv_resource_sign'");
    target.tv_resource_sign = finder.castView(view, 2131230932, "field 'tv_resource_sign'");
  }

  @Override public void reset(T target) {
    target.rl_personl_add = null;
    target.personl_sex = null;
    target.ll_resource_info = null;
    target.rl_personl_send = null;
    target.rl_personl_res = null;
    target.iv_resource_icon = null;
    target.tv_personl_account = null;
    target.tv_resource_name = null;
    target.toolbar = null;
    target.tv_resource_sign = null;
  }
}
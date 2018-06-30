package org.acg12.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import org.acg12.entity.User;
import com.acg12.lib.listener.HttpRequestListener;

import org.acg12.conf.Config;

import com.acg12.lib.ui.activity.PresenterActivityImpl;

import org.acg12.R;
import org.acg12.net.impl.HttpRequestImpl;
import org.acg12.ui.views.LoginView;
import com.acg12.lib.utils.LogUtil;
import com.acg12.lib.utils.ViewUtil;


public class LoginActivity extends PresenterActivityImpl<LoginView> implements View.OnClickListener{

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        int id = view.getId();
        if(id == R.id.title_right){
            startAnimActivity(ResetPwdActivity.class);
        } else if(id == R.id.btn_login){
            login();
        } else if(id == R.id.btn_register){
            startAnimActivity(RegisterActivity.class);
        }
    }

    public void login(){
        if(!ViewUtil.isNetConnected(mContext)) return;

        String name = mView.getUsername();
        String password = mView.getPassword();

        if (TextUtils.isEmpty(name)) {
            ShowToastView(R.string.toast_error_username_null);
            return;
        }

        if (TextUtils.isEmpty(password)) {
            ShowToastView(R.string.toast_error_password_null);
            return;
        }

        if (password.length() < 6 ) {
            ShowToastView(R.string.toast_error_password);
            return;
        }

        final ProgressDialog progress = ViewUtil.startLoading(mContext , getResources().getString(R.string.toast_login_loading));

        final User user = new User(mContext);
        user.setUsername(name);
        user.setPassword(password);

        HttpRequestImpl.getInstance().login(user, new HttpRequestListener<User>() {
            @Override
            public void onSuccess(User result) {
                progress.dismiss();
                Config.userEventBus().post(result);
                finish();
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                progress.dismiss();
                LogUtil.e(msg);
                ShowToastView(msg);
            }
        });
    }
}

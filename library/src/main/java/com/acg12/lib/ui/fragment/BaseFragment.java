package com.acg12.lib.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.acg12.lib.utils.AppStartUtil;
import com.acg12.lib.utils.Toastor;
import com.acg12.lib.utils.ViewUtil;

public class BaseFragment extends Fragment {

    protected Context mContext;
    protected String mTag;
    protected ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTag = this.getClass().getSimpleName();
        mContext = getActivity();
    }

    public View findViewById(int paramInt) {
        return getView().findViewById(paramInt);
    }

    protected void startAnimActivity(Class<?> cls) {
        startAnimActivity(cls, null, 0);
    }

    protected void startAnimActivity(Class<?> cls, int code) {
        startAnimActivity(cls, null, code);
    }

    protected void startAnimActivity(Class<?> cls, Bundle bundle) {
        startAnimActivity(cls, bundle, 0);
    }

    protected void startAnimActivity(Class<?> cls, Bundle bundle, int code) {
        AppStartUtil.startAnimActivity(this, cls, bundle, -1, code);
    }

    protected void ShowToast(final String text) {
        if (!TextUtils.isEmpty(text)) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toastor.ShowToast(text);
                }
            });
        }
    }

    protected void ShowToast(final int resId) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toastor.ShowToast(resId);
            }
        });
    }

    protected void ShowToastView(final String text) {
        if (!TextUtils.isEmpty(text)) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toastor.ShowToastView(text);
                }
            });
        }
    }

    protected void ShowToastView(final int resId) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toastor.ShowToastView(resId);
            }
        });
    }

    public void showSoftInputView() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void hideSoftInputView() {
        InputMethodManager manager = ((InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE));
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void startLoading(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = ViewUtil.startLoading(mContext, msg);
        } else {
            mProgressDialog.setMessage(msg);
            mProgressDialog.show();
        }
    }

    public void stopLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}

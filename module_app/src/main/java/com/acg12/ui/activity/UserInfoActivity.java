package com.acg12.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.acg12.lib.conf.EventConfig;
import com.acg12.entity.User;
import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.utils.LogUtil;
import com.acg12.lib.utils.premission.ApplyPermission;
import com.acg12.lib.utils.premission.FailPermission;
import com.acg12.lib.utils.premission.SuccessPermission;

import com.acg12.R;
import com.acg12.lib.constant.Constant;
import com.acg12.net.impl.HttpRequestImpl;
import com.acg12.ui.base.SkinBaseActivity;
import com.acg12.ui.views.UserInfoView;
import com.acg12.widget.popup.CommonPopupWindows;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class UserInfoActivity extends SkinBaseActivity<UserInfoView> implements View.OnClickListener ,CommonPopupWindows.OnUpdateAvatar {

    private User user;
    private CommonPopupWindows commonPopupWindows;

    @Override
    public void create(Bundle savedInstance) {
        super.create(savedInstance);
        setTranslucentStatus();
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        EventConfig.get().getUserEvent().register(this);
        user = (User) getIntent().getExtras().getSerializable("user");
        mView.paddingDate(user);
        commonPopupWindows = new CommonPopupWindows(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        commonPopupWindows.onActivityResult(requestCode ,resultCode ,data );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        ApplyPermission.onRequestPermissionsResult(this, requestCode,permissions, grantResults);
    }

    @SuccessPermission(requestCode = Constant.PERMISSION_CAMERE)
    public void openCamera() {
        commonPopupWindows.getOpenCamera();
    }

    @FailPermission(requestCode = Constant.PERMISSION_CAMERE)
    public void failOpenCamera() {
        //ShowToast("需要摄像机权限，请前往设置权限管理设置权限");
        ApplyPermission.showMissingPermissionDialog(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUser(User user){
        mView.paddingDate(user);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == Constant.TOOLBAR_ID){
            aminFinish();
        } else if(id == R.id.rl_user_avatar){
            commonPopupWindows.initOpenCamera();
            commonPopupWindows.setOnUpdateAvatar(this);
        } else if(id == R.id.rl_user_nick){
            Bundle bundle = new Bundle();
            bundle.putSerializable("user",user);
            startAnimActivity(NickActivity.class,bundle);
        } else if(id == R.id.rl_user_sign){
            Bundle bundle = new Bundle();
            bundle.putSerializable("user",user);
            startAnimActivity(SignActivity.class,bundle);
        } else if(id == R.id.iv_user_sex){
            if(user.getSex() == 0){
                user.setSex(1);
            }else {
                user.setSex(0);
            }
            mView.setSexSelector(user.getSex());
            updateSex();
        }
    }

    @Override
    public void updateAvatar(String url) {
        LogUtil.e(url);
        if (url == null || url.isEmpty())
            return;

        final ProgressDialog progress = new ProgressDialog(mContext);
        progress.setMessage("正在更新头像...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();

//        final BmobFile file = new BmobFile(new File(url));
//        file.upload(mContext, new UploadFileListener() {
//
//            @Override
//            public void onSuccess() {
//                progress.setMessage("同步数据...");
//                updateAvatar(progress , file);
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//                progress.dismiss();
////                LogUtil.e(s);
//                ShowToast(s);
//            }
//        });
    }

    public void updateAvatar(final ProgressDialog progress ){
//        final User currentUser = BmobUser.getCurrentUser(mContext, User.class);
//        currentUser.setAvatar(file.getFileUrl(mContext));
//        currentUser.update(mContext, new UpdateListener() {
//
//            @Override
//            public void onSuccess() {
//                progress.dismiss();
//                User user = DaoBaseImpl.getInstance().getCurrentUser();
//                user.setAvatar(currentUser.getAvatar());
//                DaoBaseImpl.getInstance().saveUser(user);
//                BaseConfig.userEventBus().post(user);
//
//                mView.paddingDate(user);
//            }
//
//            @Override
//            public void onFailure(int arg0, String arg1) {
//                progress.dismiss();
//                ShowToast("更新头像失败。请检查网络~");
//            }
//        });
    }


    public void updateSex(){
        HttpRequestImpl.getInstance().sex(user, new HttpRequestListener<User>() {
            @Override
            public void onSuccess(User result) {
                ShowToastView("更新成功");
                EventConfig.get().getUserEvent().post(result);

            }

            @Override
            public void onFailure(int errorcode, String msg) {
                LogUtil.e(msg);
                ShowToastView(msg);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventConfig.get().getUserEvent().unregister(this);
    }
}

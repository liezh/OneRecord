package com.liezh.onerecord.presenters;

import android.util.Log;
import android.view.View;

import com.liezh.bl.model.User;
import com.liezh.onerecord.core.extend.click.SingleClick;
import com.liezh.onerecord.interactor.LoginInteractor;
import com.liezh.onerecord.ui.login.LoginView;

import java.util.HashMap;
import java.util.Map;

/**
 * 处理业务逻辑和app网络请求等耗时操作
 */
public class LoginPresenter extends BasePresenter implements LoginInteractor.OnLoginFinishedListener {

    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenter(LoginView loginView, LoginInteractor loginInteractor) {
        this.loginView = loginView;
        this.loginInteractor = loginInteractor;
    }

    @SingleClick
    public void submitOnClick(View view, User user) {
        if (loginView != null) {
            loginView.showProgress();
        }
        loginInteractor.login(user.getUsername(), user.getPassword(), this);
    }

    @Override
    public void onUsernameError() {
        if (loginView != null) {
            loginView.hideProgress();
        }
        Log.i("user", "用户名不合法");
    }

    @Override
    public void onPasswordError() {
        if (loginView != null) {
            loginView.hideProgress();
        }
        Log.i("user", "密码不合法");
    }

    @Override
    public void onSuccess() {
        Map<String, Object> argMap = new HashMap<>();
        loginView.navigateToMain(argMap);
    }
}

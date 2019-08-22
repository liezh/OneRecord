package com.liezh.onerecord.ui.login;

import java.util.Map;

public interface LoginView {
    void showProgress();

    void hideProgress();

    void setUsernameError();

    void setPasswordError();

    void navigateToHome();

    void navigateToMain(Map<String, Object> argMap);
}

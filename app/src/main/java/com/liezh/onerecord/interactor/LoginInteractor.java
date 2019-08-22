package com.liezh.onerecord.interactor;

import android.os.Handler;
import android.text.TextUtils;

/**
 * 该类用来解耦合
 * 把presenter和model进行解耦
 */
public class LoginInteractor {

    public interface OnLoginFinishedListener {
        void onUsernameError();

        void onPasswordError();

        void onSuccess();
    }

    public void login(final String username, final String password, final OnLoginFinishedListener listener) {
        // Mock login. I'm creating a handler to delay the answer a couple of seconds
        // TODO 进行登陆，调用bl的登陆逻辑
        new Handler().postDelayed(() -> {
            if (TextUtils.isEmpty(username)) {
                listener.onUsernameError();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                listener.onPasswordError();
                return;
            }
            listener.onSuccess();
        }, 2000);
    }
}

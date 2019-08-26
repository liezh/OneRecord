package com.liezh.onerecord.interactor;

import android.os.Handler;
import android.text.TextUtils;

/**
 * 该类用来解耦合
 * 把presenter和model进行解耦
 */
public class MainInteractor {

    public interface OnMainFinishedListener {
        void onReqError();

        void onSuccess();
    }

    public void addNote(Integer type, final OnMainFinishedListener listener) {
        // Mock login. I'm creating a handler to delay the answer a couple of seconds
        // TODO 调用bl的逻辑，模拟请求的延时
        new Handler().postDelayed(() -> {
            if (type < 0) {
                listener.onReqError();
                return;
            }
            listener.onSuccess();
        }, 2000);
    }
}

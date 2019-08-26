package com.liezh.onerecord.presenters;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.liezh.bl.model.User;
import com.liezh.onerecord.core.extend.click.SingleClick;
import com.liezh.onerecord.interactor.LoginInteractor;
import com.liezh.onerecord.interactor.MainInteractor;
import com.liezh.onerecord.ui.main.MainView;

import java.util.HashMap;
import java.util.Map;

/**
 * 处理业务逻辑和app网络请求等耗时操作
 */
public class MainPresenter extends BasePresenter<MainView> implements MainInteractor.OnMainFinishedListener {

    private MainInteractor mainInteractor;

    public MainPresenter(MainView mainView, MainInteractor mainInteractor) {
        super.mView = mainView;
        this.mainInteractor = mainInteractor;
    }

    @SingleClick
    public void itemOnClick(AdapterView<?> parent, View view, int position, long id) {
        if (super.mView != null) {
            super.mView.showProgress();
        }
        mainInteractor.addNote(1, this);
    }


    @Override
    public void onReqError() {

    }

    @Override
    public void onSuccess() {

    }
}

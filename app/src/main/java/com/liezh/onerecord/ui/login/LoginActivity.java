package com.liezh.onerecord.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.liezh.bl.model.User;
import com.liezh.onerecord.R;
import com.liezh.onerecord.databinding.ActivityLoginBinding;
import com.liezh.onerecord.interactor.LoginInteractor;
import com.liezh.onerecord.presenters.LoginPresenter;
import com.liezh.onerecord.ui.main.MainActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LoginActivity extends AppCompatActivity implements LoginView {

    public class LoginDisplayModel {
        private boolean progress;

        public boolean isProgress() {
            return progress;
        }

        public void setProgress(boolean progress) {
            this.progress = progress;
        }
    }
    private ProgressBar progressBar;

    private User user = new User();

    private LoginPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        // 进度条
        progressBar = findViewById(R.id.login_progress);

        user.setUsername("123");
        user.setPassword("321");
        binding.setUser(user);

        presenter = new LoginPresenter(this, new LoginInteractor());
        binding.setPresenter(presenter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        presenter.dettach();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setUsernameError() {

    }

    @Override
    public void setPasswordError() {

    }

    @Override
    public void navigateToHome() {

    }

    @Override
    public void navigateToMain(Map<String, Object> argMap) {

        Intent intent = new Intent(this, MainActivity.class);
        if (! argMap.isEmpty()) {
            Bundle bundle=new Bundle();
            HashMap<String, Object> hmap = new HashMap<String, Object>(argMap);
            bundle.putSerializable("arg", hmap);
        }
        startActivity(intent);
    }
}

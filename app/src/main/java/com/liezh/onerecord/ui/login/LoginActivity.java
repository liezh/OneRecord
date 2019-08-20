package com.liezh.onerecord.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;

import com.liezh.onerecord.R;
import com.liezh.onerecord.core.extend.click.SingleClick;
import com.liezh.onerecord.data.model.User;
import com.liezh.onerecord.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    public class LoginDisplayModel {
        private boolean progress;

        public boolean isProgress() {
            return progress;
        }

        public void setProgress(boolean progress) {
            this.progress = progress;
        }
    }

    private User user = new User();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        user.setUsername("123");
        user.setPassword("321");
        binding.setUser(user);
        binding.setPresenter(new Presenter());
    }

    /**
     * 处理业务逻辑和app网络请求等耗时操作
     */
    public class Presenter {
        @SingleClick
        public void myOnClick(View view) {
            Toast.makeText(view.getContext(), "你点了", Toast.LENGTH_LONG).show();
            Log.i("sss", user.getUsername());
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

    }


}

package com.liezh.onerecord.ui.login;

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

    public class Presenter {
        @SingleClick
        public void myOnClick(View view) {
            Toast.makeText(view.getContext(), "你点了", Toast.LENGTH_LONG).show();
            Log.i("sss", user.getUsername());
        }

    }


}

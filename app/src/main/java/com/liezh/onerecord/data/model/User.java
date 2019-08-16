package com.liezh.onerecord.data.model;


import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.liezh.onerecord.BR;

public class User extends BaseObservable {

    private Long id;

    private String username;

    private String password;

    public User() {
    }
    @Bindable
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Bindable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }
}

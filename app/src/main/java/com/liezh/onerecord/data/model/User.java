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

    /**
     * TODO @Bindable 注解字段是否可以进行数据绑定
     * @return
     */
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
        // TODO 通知Data Binding 对该字段的控件进行数据更新
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

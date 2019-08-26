package com.liezh.onerecord.domian.vo;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.liezh.onerecord.BR;

import java.util.Date;

public class Note extends BaseObservable {

    private Integer id;

    private String title;

    private String desc;

    private String cover;

    private Date releaseDt;

    private Boolean start;

    private Integer type;

    public Note() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged( BR.title );
    }

    @Bindable
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
        notifyPropertyChanged( BR.desc );
    }

    @Bindable
    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
        notifyPropertyChanged( BR.cover );
    }

    @Bindable
    public Date getReleaseDt() {
        return releaseDt;
    }

    public void setReleaseDt(Date releaseDt) {
        this.releaseDt = releaseDt;
        notifyPropertyChanged( BR.releaseDt );
    }

    @Bindable
    public Boolean getStart() {
        return start;
    }

    public void setStart(Boolean start) {
        this.start = start;
        notifyPropertyChanged( BR.start );
    }

    @Bindable
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
        notifyPropertyChanged( BR.type );
    }
}

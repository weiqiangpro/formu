package com.formu.bean;

import java.util.Date;

public class Mes {
    private Integer mesId;

    private String mesMessage;

    private Integer mesToid;

    private Integer mesFromid;

    private Date mesCreatetime;

    private Integer mesIsread;

    public Integer getMesId() {
        return mesId;
    }

    public void setMesId(Integer mesId) {
        this.mesId = mesId;
    }

    public String getMesMessage() {
        return mesMessage;
    }

    public void setMesMessage(String mesMessage) {
        this.mesMessage = mesMessage == null ? null : mesMessage.trim();
    }

    public Integer getMesToid() {
        return mesToid;
    }

    public void setMesToid(Integer mesToid) {
        this.mesToid = mesToid;
    }

    public Integer getMesFromid() {
        return mesFromid;
    }

    public void setMesFromid(Integer mesFromid) {
        this.mesFromid = mesFromid;
    }

    public Date getMesCreatetime() {
        return mesCreatetime;
    }

    public void setMesCreatetime(Date mesCreatetime) {
        this.mesCreatetime = mesCreatetime;
    }

    public Integer getMesIsread() {
        return mesIsread;
    }

    public void setMesIsread(Integer mesIsread) {
        this.mesIsread = mesIsread;
    }
}
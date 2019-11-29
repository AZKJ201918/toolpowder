package com.azkj.toolpowder.user.entity;

import java.util.Date;

public class Orderit {
    private Integer id;

    private String orderitid;

    private Date createtime;

    private String uuid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderitid() {
        return orderitid;
    }

    public void setOrderitid(String orderitid) {
        this.orderitid = orderitid == null ? null : orderitid.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }
}
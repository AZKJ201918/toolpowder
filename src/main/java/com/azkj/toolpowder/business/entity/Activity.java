package com.azkj.toolpowder.business.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Activity {
    private Integer id;

    private String introduce;

    private Long status;

    private Date createtime;

    private Date updatetime;

}
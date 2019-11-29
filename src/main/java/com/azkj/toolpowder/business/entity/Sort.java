package com.azkj.toolpowder.business.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Sort {
    private Integer id;

    private String name;

    private String url;

    private Date createtime;

    private Date updatetime;

    private Long status;


}
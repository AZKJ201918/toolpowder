package com.azkj.toolpowder.user.entity;

import lombok.Data;

import java.util.Date;

@Data
public class League {
    private Integer id;

    private String name;

    private String username;

    private String phone;

    private String adress;

    private Date createtime;

    private Long status;

    private String uuid;

}
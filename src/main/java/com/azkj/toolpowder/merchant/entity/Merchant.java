package com.azkj.toolpowder.merchant.entity;

import lombok.Data;

@Data
public class Merchant {
    private Integer id;

    private String name;

    private Integer sid;

    private String appid;

    private String appsecrt;

    private String url;

    private Long type;

    private String adress;

    private Double price;

    private Integer uid;

    private String statusTime;

}
package com.azkj.toolpowder.merchant.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Coupon {
    private Integer id;

    private Integer number;

    private Integer robnumber;

    private Integer sale;

    private String name;

    private Integer doorsill;

    private Integer reduction;

    private Integer mid;

    private Long status;

    private Date starttime;

    private Date endtime;

    private  Merchant merchant;



}
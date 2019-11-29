package com.azkj.toolpowder.user.entity;

import com.azkj.toolpowder.merchant.entity.Coupon;
import lombok.Data;

@Data
public class UserCoupon {
    private Integer id;

    private String uuid;

    private Integer couponid;

    private Long status;

    private Coupon coupon;

}
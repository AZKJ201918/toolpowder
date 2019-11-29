package com.azkj.toolpowder.merchant.dao;

import com.azkj.toolpowder.merchant.entity.Coupon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CouponMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Coupon record);

    int insertSelective(Coupon record);

    Coupon selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Coupon record);

    int updateByPrimaryKey(Coupon record);

    @Select("SELECT * FROM coupon WHERE NOW() BETWEEN startTime AND endTime AND status=1")
    List<Coupon> selectByCoupon();
    @Select("SELECT * FROM coupon WHERE NOW() BETWEEN startTime AND endTime AND status=1 and mid=#{id}")
    List<Coupon> selectMerchantCoupon(Integer id);
}
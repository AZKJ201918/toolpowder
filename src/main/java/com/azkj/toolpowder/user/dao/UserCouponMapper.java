package com.azkj.toolpowder.user.dao;

import com.azkj.toolpowder.user.entity.UserCoupon;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserCouponMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserCoupon record);

    int insertSelective(UserCoupon record);

    UserCoupon selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserCoupon record);

    int updateByPrimaryKey(UserCoupon record);

    List<UserCoupon> selectByCoupon(String uuid);
}
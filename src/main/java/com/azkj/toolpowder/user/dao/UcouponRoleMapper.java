package com.azkj.toolpowder.user.dao;

import com.azkj.toolpowder.user.entity.UcouponRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UcouponRoleMapper {
    int deleteByPrimaryKey(Integer ucid);

    int insert(UcouponRole record);

    int insertSelective(UcouponRole record);

    UcouponRole selectByPrimaryKey(Integer ucid);

    int updateByPrimaryKeySelective(UcouponRole record);

    int updateByPrimaryKey(UcouponRole record);

    @Select("SELECT * FROM  ucoupon_role WHERE uid=#{uuid} AND cid=#{cid}")
    UcouponRole selectByUcRole(@Param("uuid") String uuid, @Param("cid") Integer cid);
}
package com.azkj.toolpowder.user.dao;

import com.azkj.toolpowder.user.entity.Orderit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderitMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Orderit record);

    int insertSelective(Orderit record);

    Orderit selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Orderit record);

    int updateByPrimaryKey(Orderit record);

    @Select("SELECT * FROM orderit WHERE uuid=#{uuid}")
    Orderit SelectByOrderit(String uuid);
}
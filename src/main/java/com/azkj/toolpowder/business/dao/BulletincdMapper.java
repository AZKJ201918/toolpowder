package com.azkj.toolpowder.business.dao;

import com.azkj.toolpowder.business.entity.Bulletincd;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BulletincdMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Bulletincd record);

    int insertSelective(Bulletincd record);

    Bulletincd selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Bulletincd record);

    int updateByPrimaryKey(Bulletincd record);

    @Select("SELECT * FROM bulletincd WHERE status=1")
    Bulletincd SelectBulletincd();
}
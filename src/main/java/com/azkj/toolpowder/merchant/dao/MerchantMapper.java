package com.azkj.toolpowder.merchant.dao;

import com.azkj.toolpowder.merchant.entity.Merchant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MerchantMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Merchant record);

    int insertSelective(Merchant record);

    Merchant selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Merchant record);

    int updateByPrimaryKey(Merchant record);

    @Select("SELECT COUNT(*) FROM  merchant where type=2 ")
    Integer SelectCount();
    @Select("SELECT id,name,url FROM merchant WHERE type=2   LIMIT  #{startRow} , #{pageSize}")
    List<Merchant> SelectPage(@Param("startRow") Integer startRow, @Param("pageSize") Integer pageSize);
    @Select("SELECT * FROM merchant WHERE sid=#{id}")
    List<Merchant> SelectSortMerchant(Integer id);
    @Select("SELECT * FROM  merchant where id=#{mid} ")
    Merchant selectByPrimary(Integer mid);
}
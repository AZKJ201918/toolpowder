package com.azkj.toolpowder.user.dao;

import com.azkj.toolpowder.user.entity.Earnings;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EarningsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Earnings record);

    int insertSelective(Earnings record);

    Earnings selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Earnings record);

    int updateByPrimaryKey(Earnings record);

    @Select("SELECT * FROM earnings WHERE price>0 AND status=1 AND uuid=#{uuid}")
    List<Earnings> SelectEarnings(String uuid);

    @Select("SELECT SUM(price) FROM earnings WHERE price>0 AND status=1 AND uuid=#{uuid}")
    Double selectSumEarnings(String uuid);

    @Select("SELECT SUM(price) FROM earnings WHERE  status=1 AND uuid=#{uuid}")
    Double SelectBalanceEarninges(String uuid);
}
package com.azkj.toolpowder.user.dao;

import com.azkj.toolpowder.user.entity.Bankcard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BankcardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Bankcard record);

    int insertSelective(Bankcard record);

    Bankcard selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Bankcard record);

    int updateByPrimaryKey(Bankcard record);

    @Select("SELECT * FROM bankcard WHERE uuid=#{uid} AND status=1")
    List<Bankcard> SelectBankcardEarninges(String uuid);
}
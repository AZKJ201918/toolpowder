package com.azkj.toolpowder.user.dao;

import com.azkj.toolpowder.user.entity.League;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LeagueMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(League record);

    int insertSelective(League record);

    League selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(League record);

    int updateByPrimaryKey(League record);

    @Select("SELECT *FROM league WHERE status=1 AND uuid=#{uuid}")
    League selectByLeagues(String uuid);
}
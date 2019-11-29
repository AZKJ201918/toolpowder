package com.azkj.toolpowder.business.dao;

import com.azkj.toolpowder.business.entity.Music;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MusicMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Music record);

    int insertSelective(Music record);

    Music selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Music record);

    int updateByPrimaryKey(Music record);

    @Select("SELECT COUNT(*) FROM music")
    Integer SelectMusicCount();
    @Select("SELECT * FROM music where id!=#{id} ")
    List<Music> selectByAll(Integer id);
}
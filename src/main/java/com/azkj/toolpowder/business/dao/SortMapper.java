package com.azkj.toolpowder.business.dao;

import com.azkj.toolpowder.business.entity.Sort;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SortMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Sort record);

    int insertSelective(Sort record);

    Sort selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Sort record);

    int updateByPrimaryKey(Sort record);

    @Select("SELECT * FROM sort WHERE status=1 LIMIT 0,7")
    List<Sort> SelectSort();

    @Select("SELECT * FROM sort WHERE status=1")
    List<Sort> SelectMoreSort();
}
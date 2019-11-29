package com.azkj.toolpowder.merchant.dao;

import com.azkj.toolpowder.merchant.entity.Details;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DetailsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Details record);

    int insertSelective(Details record);

    Details selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Details record);

    int updateByPrimaryKey(Details record);

    @Select("SELECT * FROM details WHERE mid=#{id}")
    Details SelectDetails(Integer id);
}
package com.azkj.toolpowder.user.dao;

import com.azkj.toolpowder.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    @Select("select * from user where uuid=#{uuid}")
    User selectByUser(String uuid);

    @Select("select * from user where openid=#{openid}")
    User SelectByOpenid(String openid);

    @Select("SELECT userImg FROM user WHERE uuid=#{uuid}")
    String SelectImg(String uuid);
}
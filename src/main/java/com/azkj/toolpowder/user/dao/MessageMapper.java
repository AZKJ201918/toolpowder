package com.azkj.toolpowder.user.dao;

import com.azkj.toolpowder.user.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);

    @Select("SELECT * FROM message WHERE uuid=#{uuid}")
    List<Message> selectByMessageList(String uuid);

    @Update("UPDATE message SET status=2 WHERE uuid=#{uuid}")
    void updateByMessage(String uuid);
    @Select("SELECT phone FROM message WHERE uuid=#{uuid} and status=1")
    String selectByPhone(String uuid);

}
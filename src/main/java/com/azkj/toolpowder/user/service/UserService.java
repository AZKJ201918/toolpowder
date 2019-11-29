package com.azkj.toolpowder.user.service;

import com.azkj.toolpowder.common.exception.ToolPowderExcption;
import com.azkj.toolpowder.merchant.entity.Coupon;
import com.azkj.toolpowder.user.entity.League;
import com.azkj.toolpowder.user.entity.Message;
import com.azkj.toolpowder.user.entity.User;
import com.azkj.toolpowder.user.entity.UserCoupon;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public interface UserService {
    void instrMessage(Message message)throws ToolPowderExcption;

    void instrLeague(League league) throws ToolPowderExcption;

    String instrUser(String openid,String name,String img) throws Exception;

    List<UserCoupon> SelectUserCoupon(String uuid)throws ToolPowderExcption;

    Map<String,Object> SelectEarnings(String uuid)throws ToolPowderExcption;

    User SelectOpenid(String uuid);

    void InstrOrderit(String uuid, String out_trade_no) throws IOException;

    String SelectImg(String uuid);

    void drawCoupon(String uuid, Integer cid) throws ToolPowderExcption, IOException;

    Boolean SelectInfo(String uuid);
}

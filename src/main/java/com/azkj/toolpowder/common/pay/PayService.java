package com.azkj.toolpowder.common.pay;

public interface PayService {

    String unifiedOrder(Integer price, String openid,String remoteAddr,String out_trade_no);
}

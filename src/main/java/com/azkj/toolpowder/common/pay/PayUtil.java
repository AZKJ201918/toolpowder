package com.azkj.toolpowder.common.pay;

import com.azkj.toolpowder.common.constants.Constants;
import com.azkj.toolpowder.common.exception.ToolPowderExcption;
import com.azkj.toolpowder.common.utils.DateUtil;
import com.azkj.toolpowder.common.utils.HttpUtil;
import com.azkj.toolpowder.common.utils.MD5Util;
import com.azkj.toolpowder.user.dao.OrderitMapper;
import com.azkj.toolpowder.user.entity.Orderit;
import com.azkj.toolpowder.user.entity.User;
import com.azkj.toolpowder.user.service.UserService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Component
public class PayUtil {
    @Autowired
    @Qualifier("payServiceImpl")
    private PayService payService;

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @Autowired
    private OrderitMapper orderitMapper;


    public Map<String,String> pay(Integer price,String uuid,String remoteAddr) throws ToolPowderExcption {
        String out_trade_no=MD5Util.getRandomStr(8);
        User user=userService.SelectOpenid(uuid);
        System.out.println(1);
        Orderit orderit=orderitMapper.SelectByOrderit(uuid);
        System.out.println(2);
        if(orderit==null) {

            System.out.println(user.getOpenid());
            String prepayId = payService.unifiedOrder(price, user.getOpenid(), remoteAddr, out_trade_no);
            System.out.println(prepayId);
            if (prepayId != null) {
                String timeStamp = DateUtil.getTimeStamp();
                String nonceStr = MD5Util.getRandomStr(20);
                SortedMap<String, Object> signMap = new TreeMap<>();
                signMap.put("appId", Constants.WEXING_STATUS_APPID);
                signMap.put("package", prepayId);
                signMap.put("timeStamp", timeStamp);
                signMap.put("nonceStr", nonceStr);
                signMap.put("signType", "MD5");
                Map<String, String> map = new HashMap<>();
                map.put("appId", Constants.WEXING_STATUS_APPID);
                map.put("timeStamp", timeStamp);
                map.put("nonceStr", nonceStr);
                map.put("prepayId", prepayId);
                map.put("signType", MD5Util.getSign(signMap));
                map.put("out_trade_no", out_trade_no);
                return map;

            }
        }else{
            throw new ToolPowderExcption("你已经买过了");
        }
        return null;

    }

    public String QrCodePay(String ip, int price) throws  ToolPowderExcption{
        Map<String, String> resultMap = null;
//        SortedMap<String,Object> packageParams = new TreeMap<String, Object>();
//        packageParams.put("appid", Constants.WEXING_STATUS_APPID);    		 				// 公众APPID
//        packageParams.put("mch_id", Constants.MCH_ID);    		 			// 商会ID
//        packageParams.put("nonce_str", MD5Util.getRandomStr(32));
//        packageParams.put("body","买商品测试" );  		 	// 产品名称
//        packageParams.put("out_trade_no", MD5Util.getRandomStr(8));  	// 订单号
//        packageParams.put("total_fee", price );//价格的单位为分
//        packageParams.put("spbill_create_ip", ip);  	//支付发起IP
//        packageParams.put("notify_url", Constants.NOTIFY_URL);   				//支付回调地址
//        packageParams.put("trade_type", Constants.TRADE_TYPE_NATIVE);  				//支付类型
//        packageParams.put("sign", MD5Util.getSign(packageParams));


        PaySendData paySendData = new PaySendData();
        paySendData.setAppId(Constants.WEXING_STATUS_APPID);
        paySendData.setMch_id(Constants.MCH_ID);
        paySendData.setSub_mch_id(Constants.SUB_MCH_ID);
        paySendData.setNotify_url(Constants.NOTIFY_URL);
        paySendData.setTrade_type(Constants.TRADE_TYPE_NATIVE);
        paySendData.setDevice_info(Constants.WEB);
        paySendData.setBody("买优惠卷");
        paySendData.setNonce_str(MD5Util.getRandomStr(32));
        paySendData.setOut_trade_no(MD5Util.getRandomStr(8));
        paySendData.setTotal_fee(price);
        paySendData.setSpbill_create_ip(ip);
        paySendData.setSign(MD5Util.getSign(buildParamMap(paySendData)));

        System.out.println(1);
        String reqXml = MD5Util.sendDataToXml(paySendData);
        System.out.println(2);
        try {
            // 发送请求
            CloseableHttpResponse response = HttpUtil.Post(Constants.UNIFIED_ORDER_URL, reqXml);
            try {
                resultMap = MD5Util.parseXml(response.getEntity().getContent());
                // 关闭流
                EntityUtils.consume(response.getEntity());
            } finally {
                response.close();
            }
        } catch (Exception e) {
            System.out.println("微信支付统一下单异常");
        }
        System.out.println(resultMap);

        String urlCode = resultMap.get("code_url");
        return urlCode;
    }



    public SortedMap<String, Object> buildParamMap(PaySendData data) {
        SortedMap<String, Object> paramters = new TreeMap<String, Object>();
        Field[] fields = data.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                if (null != field.get(data)) {
                    paramters.put(field.getName().toLowerCase(), field.get(data).toString());
                }
            }
        } catch (Exception e) {
            System.out.print("构建签名map错误: ");
            e.printStackTrace();
        }

        return paramters;
    }
}


package com.azkj.toolpowder.common.pay;

import com.azkj.toolpowder.common.constants.Constants;
import com.azkj.toolpowder.common.utils.HttpUtil;
import com.azkj.toolpowder.common.utils.MD5Util;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Service("payServiceImpl")
public class PayServiceImpl implements  PayService {
    @Override
    public String unifiedOrder(Integer price, String openid,String remoteAddr,String out_trade_no) {
        Map<String, String> resultMap = null;
        // 统一下单返回的预支付id
        String prepayId = null;
        PaySendData paySendData = new PaySendData();
        paySendData.setAppId(Constants.WEXING_STATUS_APPID);
        paySendData.setMch_id(Constants.MCH_ID);
        paySendData.setSub_mch_id(Constants.SUB_MCH_ID);
        paySendData.setNotify_url(Constants.NOTIFY_URL);
        paySendData.setTrade_type(Constants.TRADE_TYPE_JSAPI);
        paySendData.setDevice_info(Constants.WEB);
        paySendData.setBody("买优惠卷");
        paySendData.setNonce_str(MD5Util.getRandomStr(32));
        paySendData.setOut_trade_no(out_trade_no);
        paySendData.setTotal_fee(price);
        paySendData.setSpbill_create_ip(remoteAddr);
        paySendData.setOpenId(openid);
        paySendData.setSign(MD5Util.getSign(buildParamMap(paySendData)));
        String reqXml = MD5Util.sendDataToXml(paySendData);
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
        String return_code = resultMap.get("return_code");
        System.out.println(return_code);
        String result_code = resultMap.get("result_code");
        System.out.println(result_code);
        if (Constants.RETURN_SUCCESS.equals(return_code) && Constants.RETURN_SUCCESS.equals(result_code)) {
            // return_code=通信标识
            // result_code=交易标识
            // 只有当returnCode与resultCode均返回“success”，才代表微信支付统一下单成功
            prepayId = "prepay_id=" + resultMap.get("prepay_id");
        }
        return prepayId;
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

package com.azkj.toolpowder.common.utils;

import com.azkj.toolpowder.common.constants.Constants;
import com.azkj.toolpowder.common.pay.PaySendData;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.*;


public class MD5Util {

    public static XStream xStream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));

    public static String getMD5(String source){
        return DigestUtils.md5Hex(source);
    }


    public static String getRandomStr(int length) {
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static String getSign(SortedMap<String, Object> map) {
        StringBuffer buffer = new StringBuffer();
        Set<Map.Entry<String, Object>> set = map.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            String k = entry.getKey();
            Object v = entry.getValue();
            // 参数中sign、key不参与签名加密
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                buffer.append(k + "=" + v + "&");
            }
        }
        buffer.append("key=" + Constants.KEY);
        String sign = MD5Encode(buffer.toString()).toUpperCase();
        return sign;
    }

    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    public static String MD5Encode(String origin) {
        String result = null;
        try {
            result = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            result = byteArrayToHexString(md.digest(result.getBytes("UTF-8")));
        } catch (Exception exception) {
        }
        return result;
    }



    public static String sendDataToXml(PaySendData data) {
        xStream.autodetectAnnotations(true);
        xStream.alias("xml", PaySendData.class);
        return xStream.toXML(data);
    }

    public static Map<String, String> parseXml(InputStream inputStream) {
        SortedMap<String, String> map = new TreeMap<String, String>();
        try {
            // 获取request输入流
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStream);
            // 得到xml根元素
            Element root = document.getRootElement();
            // 得到根元素所有节点
            List<Element> elementList = root.elements();
            // 遍历所有子节点
            for (Element element : elementList) {
                map.put(element.getName(), element.getText());
            }
            // 释放资源
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("微信工具类:解析xml异常");
        }
        return map;
    }

    public static String getNonceStr(){
        return RandomUtil.randomString(RandomUtil.LETTER_NUMBER_CHAR, 32);
    }
    public static String getTransferNo() {
        // 自增8位数 00000001
        return "TNO" + DatetimeUtil.formatDate(new Date(), DatetimeUtil.TIME_STAMP_PATTERN) + "00000001";
    }

    public static String getSign(Map<String, String> params, String paternerKey) throws UnsupportedEncodingException {
        return getMD5S(createSign(params, false) + "&key=" + paternerKey).toUpperCase();
    }

    public static String getMD5S(String content) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(content.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }

        return md5StrBuff.toString();
    }

    public static String createSign(Map<String, String> params, boolean encode) throws UnsupportedEncodingException {
        Set<String> keysSet = params.keySet();
        Object[] keys = keysSet.toArray();
        Arrays.sort(keys);
        StringBuffer temp = new StringBuffer();
        boolean first = true;
        for (Object key : keys) {
            if (key == null || StringUtil.isEmpty(params.get(key))) // 参数为空不参与签名
                continue;
            if (first) {
                first = false;
            } else {
                temp.append("&");
            }
            temp.append(key).append("=");
            Object value = params.get(key);
            String valueStr = "";
            if (null != value) {
                valueStr = value.toString();
            }
            if (encode) {
                temp.append(URLEncoder.encode(valueStr, "UTF-8"));
            } else {
                temp.append(valueStr);
            }
        }
        return temp.toString();
    }
}

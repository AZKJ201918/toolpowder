package com.azkj.toolpowder;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.azkj.toolpowder.business.service.ActivityService;
import com.azkj.toolpowder.business.service.BulletincdService;
import com.azkj.toolpowder.business.service.MusicService;
import com.azkj.toolpowder.business.service.SortService;
import com.azkj.toolpowder.common.constants.Constants;
import com.azkj.toolpowder.common.exception.ToolPowderExcption;
import com.azkj.toolpowder.common.pay.PayUtil;
import com.azkj.toolpowder.common.utils.HttpUtil;
import com.azkj.toolpowder.common.utils.RandomCodeUtil;

import com.azkj.toolpowder.merchant.service.MerchantService;
import com.azkj.toolpowder.user.controller.UserController;
import com.azkj.toolpowder.user.entity.UserCoupon;
import com.azkj.toolpowder.user.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
	@SpringBootTest
	public class ToolpowderApplicationTests {
	@Autowired
	@Qualifier("musicServiceImpl")
	private MusicService musicService;

	@Autowired
	@Qualifier("sortServiceImpl")
	private SortService sortService;

	@Autowired
	@Qualifier("activityServiceImpl")
	private ActivityService activityService;

	@Autowired
	@Qualifier("bulletincdServiceImpl")
	private BulletincdService bulletincdService;

	@Autowired
	@Qualifier("merchantServiceImpl")
	private MerchantService merchantService;


	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;


	@Autowired
	private UserController userController;

	private static String operation = "/industrySMS/sendSMS";

		@Autowired
		private PayUtil payUtil;

	@Test
	public void contextLoads() throws ToolPowderExcption, IOException {


//		payUtil.QrCodePay("127.0.0.1",1);
//		System.out.println(merchantService.SelectDetails(1).toString());
//		List<Coupon> couponList=merchantService.SelectCoupon();
//		couponList.stream().forEach(
//				parp->{
//					System.out.println(parp.toString());
//				}
//		);
//		List<UserCoupon> coupons=userService.SelectUserCoupon("f2086d2cc571ca4f261950894c4fd548");
//        coupons.stream().forEach(
//        		para->{
//					System.out.println(para.toString());
//				}
//		);
//		userService.InstrOrderit("7ea7cea297c03ca16f2d1aa5d3be864d","123131231231231");
//		String url="https://wxpay.chazhanyuan.cn:9999/messages?phone="+1313132123;
//		JSONObject ok=Constants.doGetJson(url);
//		System.out.println(ok.toString());
//		userService.InstrOrderit("92e11cd58872cb4f5b0a9846e80e1efc","1232131231");

//		String url="https://wxpay.chazhanyuan.cn:555/messages?phone="+12312312;
//		Constants.message(url);

//		System.out.println(userService.SelectInfo("7ea7cea297c03ca16f2d1aa5d3be864d"));
//		userController.spay("adad112412",1);
		String code = RandomCodeUtil.generateCode();//生成验证码
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timestamp = sdf.format(new Date());
		String sig = DigestUtils.md5Hex(Constants.MDSMS_ACCOUNT_SID + Constants.MDSMS_AUTH_TOKEN + timestamp);//签名
		String url = Constants.MDSMS_REST_URL + operation;
		Map<String, String> param = new HashMap<>();
		param.put("accountSid", Constants.MDSMS_ACCOUNT_SID);
		param.put("to", "17683716034");
		param.put("templateid", Constants.MDSMS_VERCODE_TPLID);
		param.put("param", code);
		param.put("timestamp", timestamp);
		param.put("sig", sig);
		param.put("respDataType", "json");
		String results = HttpUtil.post(url, param);
		JSONObject jsonObject = JSON.parseObject(results);
		String respCode = jsonObject.getString("respCode");
	}

}

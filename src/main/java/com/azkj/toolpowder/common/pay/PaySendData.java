package com.azkj.toolpowder.common.pay;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;


@Data
public class PaySendData {

	/**
	 * 公众账号ID[必须]
	 */
	@XStreamAlias("appid")
	private String appId;

	/**
	 * 商户号[必须]
	 */
	@XStreamAlias("mch_id")
	private String mch_id;

	@XStreamAlias("sub_mch_id")
	private String sub_mch_id;
	/**
	 * 设备号[WEB]
	 */
	@XStreamAlias("device_info")
	private String device_info;

	/**
	 * 随机字符串[必须]
	 */
	@XStreamAlias("nonce_str")
	private String nonce_str;

	/**
	 * 签名[必须]
	 */
	@XStreamAlias("sign")
	private String sign;

	/**
	 * 商品描述[必须]
	 */
	@XStreamAlias("body")
	private String body;

	/**
	 * 商品详情
	 */
	@XStreamAlias("detail")
	private String detail;

	/**
	 * 附加数据
	 */
	@XStreamAlias("attach")
	private String attach;

	/**
	 * 商户订单号[必须]
	 */
	@XStreamAlias("out_trade_no")
	private String out_trade_no;

	/**
	 * 货币类型
	 */
	@XStreamAlias("fee_type")
	private String fee_type;

	/**
	 * 交易金额 [必须]
	 */
	@XStreamAlias("total_fee")
	private Integer total_fee;

	/**
	 * 交易类型 [必须]
	 */
	@XStreamAlias("trade_type")
	private String trade_type;

	/**
	 * 通知地址 [必须]
	 */
	@XStreamAlias("notify_url")
	private String notify_url;

	/**
	 * 终端Ip [必须]
	 */
	@XStreamAlias("spbill_create_ip")
	private String spbill_create_ip;

	/**
	 * 订单生成时间yyyyMMddHHmmss
	 */
	@XStreamAlias("time_start")
	private String time_start;

	/**
	 * 订单失效时间yyyyMMddHHmmss 间隔>5min
	 */
	@XStreamAlias("time_expire")
	private String time_expire;

	/**
	 * 用户标识 tradeType=JSAPI时必须
	 */
	@XStreamAlias("openid")
	private String openId;

	/**
	 * 商品标记
	 */
	@XStreamAlias("goods_tag")
	private String goods_tag;

	/** 指定支付方式 */
	@XStreamAlias("limit_pay")
	private String limit_pay;

	// ===============以下属性为申请退款参数===================

	/**
	 * 微信订单号 [必须] 与商户退款单号二选一
	 */
	@XStreamAlias("transaction_id")
	private String transaction_id;

	/**
	 * 商户退款单号 [必须] 与微信单号二选一
	 */
	@XStreamAlias("out_refund_no")
	private String out_refund_no;

	/**
	 * 退款金额 [必须]
	 */
	@XStreamAlias("refund_fee")
	private Integer refund_fee;

	/**
	 * 退款货币种类
	 */
	@XStreamAlias("refund_fee_type")
	private String refund_fee_type;

	/**
	 * 操作员账号:默认为商户号 [必须]
	 */
	@XStreamAlias("op_user_id")
	private String op_user_id;



}
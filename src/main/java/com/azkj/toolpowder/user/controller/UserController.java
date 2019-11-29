package com.azkj.toolpowder.user.controller;



import com.alibaba.fastjson.JSONObject;
import com.azkj.toolpowder.common.constants.Constants;
import com.azkj.toolpowder.common.exception.ToolPowderExcption;
import com.azkj.toolpowder.common.pay.PayUtil;
import com.azkj.toolpowder.common.resp.ApiResult;
import com.azkj.toolpowder.common.utils.*;
import com.azkj.toolpowder.merchant.entity.Details;
import com.azkj.toolpowder.user.entity.League;
import com.azkj.toolpowder.user.entity.Message;
import com.azkj.toolpowder.user.entity.User;
import com.azkj.toolpowder.user.entity.UserCoupon;
import com.azkj.toolpowder.user.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@RestController
@CrossOrigin
@Slf4j
public class UserController {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;


    @Autowired
    private ZkClient zkClient;

    @Autowired
    private  CuratorFramework curatorFramework;


    private InterProcessMutex lock=null;

    @Autowired
    private PayUtil payUtil;




    @GetMapping("/wxCallback")
    public ApiResult<String> wxCallback(String code) throws Exception {

        ApiResult<String> result=new ApiResult<>();
        try {

            String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Constants.WEXING_STATUS_APPID +
                    "&secret=" + Constants.WEIXING_STATUS_APPSECRET +
                    "&code=" + code +
                    "&grant_type=authorization_code";

            JSONObject jsonObject = Constants.doGetJson(url);
            String token = jsonObject.getString("access_token");
            String openid = jsonObject.getString("openid");
            String getUserInfo = "https://api.weixin.qq.com/sns/userinfo?access_token=" + token + "&openid=" + openid + "&lang=zh_CN";
            JSONObject userInfo = Constants.doGetJson(getUserInfo);
            String nickname = userInfo.getString("nickname");
            String headimgurl = userInfo.getString("headimgurl");
            String uuid = userService.instrUser(openid, nickname, headimgurl);
            result.setData(uuid);
        }catch (ToolPowderExcption e){
            result.setMessage("请在一分钟重试");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
        }
        return result;

    }




    @ApiOperation(value = "录入信息",notes = "录入信息",httpMethod = "POST")
    @ApiImplicitParam(name = "message",required = true,dataType = "Message")
    @PostMapping("instrMessage")
    public ApiResult instrMessage(@RequestBody Message message){
        ApiResult<Details> result=new ApiResult();
        try {
            userService.instrMessage(message);
            result.setMessage("录入信息成功");
        }catch (ToolPowderExcption e){
            result.setCode(e.getStatusCode());
            result.setMessage(e.getMessage());
        }catch (Exception e){
            log.error("SQL statement error or that place is empty");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("内部错误");
        }
        return result;
    }

    @ApiOperation(value = "立即加盟",notes = "立即加盟",httpMethod = "POST")
    @ApiImplicitParam(name = "league",required = true,dataType = "League")
    @PostMapping("instrLeague")
    public ApiResult instrLeague(@RequestBody League league){
        ApiResult<Details> result=new ApiResult();
        try {
            userService.instrLeague(league);
            result.setMessage("加盟成功");
        }catch (ToolPowderExcption e){
            result.setCode(e.getStatusCode());
            result.setMessage(e.getMessage());
        }catch (Exception e){
            log.error("SQL statement error or that place is empty");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("内部错误");
        }
        return result;
    }


    @ApiOperation(value = "查看自己的优惠卷",notes = "查看自己的优惠卷",httpMethod = "GET")
    @ApiImplicitParam
    @GetMapping("SelectUserCoupon")
    public ApiResult SelectUserCoupon(String uuid){
        ApiResult<List<UserCoupon>> result=new ApiResult();
        try {
            List<UserCoupon> couponList=userService.SelectUserCoupon(uuid);
            result.setData(couponList);
            result.setMessage("查看成功");
        }catch (ToolPowderExcption e){
            result.setCode(e.getStatusCode());
            result.setMessage(e.getMessage());
        }catch (Exception e){
            log.error("SQL statement error or that place is empty");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("内部错误");
        }
        return result;
    }

    @ApiOperation(value = "收益",notes = "收益",httpMethod = "GET")
    @ApiImplicitParam
    @GetMapping("SelectEarnings")
    public ApiResult SelectEarnings(String uuid){
        ApiResult<Map<String,Object>> result=new ApiResult();
        try {

            Map<String,Object> map=userService.SelectEarnings(uuid);
            result.setData(map);
            result.setMessage("查看成功");
        }catch (ToolPowderExcption e){
            result.setCode(e.getStatusCode());
            result.setMessage(e.getMessage());
        }catch (Exception e){
            log.error("SQL statement error or that place is empty"+e);
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("内部错误");
        }
        return result;
    }

    @GetMapping("pay")
    public ApiResult pay(HttpServletRequest request, String uuid){
        ApiResult<Map<String,String>> result=new ApiResult<>();

        System.out.println("==============================");
        System.out.println(uuid);
        try {
            String ip=request.getRemoteAddr();
            Map<String,String>  map=payUtil.pay(1,uuid,ip);
            result.setData(map);
            result.setMessage("查看成功");
        }catch (ToolPowderExcption e){
            result.setCode(e.getStatusCode());
            result.setMessage(e.getMessage());
        }catch (Exception e){
            log.error("SQL statement error or that place is empty");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("内部错误");
        }
        return result;
    }

    //二维码支付
    @GetMapping("QrCodePay")
    public ApiResult QrCodePay(HttpServletRequest request){
        ApiResult result=new ApiResult();
        try {
            String ip=request.getRemoteAddr();
            String  codeUrl=payUtil.QrCodePay(ip,1);
            result.setData(codeUrl);
            result.setMessage("查看成功");
        }catch (ToolPowderExcption e){
            result.setCode(e.getStatusCode());
            result.setMessage(e.getMessage());
        }catch (Exception e){
            log.error("SQL statement error or that place is empty");
            result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
            result.setMessage("内部错误");
        }
        return result;
    }


  @GetMapping("payStart")
   public ApiResult   PayStart(String uuid ,String out_trade_no) throws IOException {
        ApiResult result=new ApiResult();
        userService.InstrOrderit(uuid,out_trade_no);
        result.setMessage("优惠卷添加成功");
        return result;

  }

  @GetMapping("SelectImg")
  public ApiResult  SelectImg(String uuid){
        ApiResult<String> result=new ApiResult<String>();
        String img=userService.SelectImg(uuid);
        result.setData(img);
        return  result;
  }

  @GetMapping("drawCoupon")
    public ApiResult  drawCoupon(String uuid,Integer cid){
      ApiResult result=new ApiResult();

      try {
          userService.drawCoupon(uuid,cid);
          result.setMessage("领取成功");
      }catch (ToolPowderExcption e){
          result.setCode(e.getStatusCode());
          result.setMessage(e.getMessage());
      }catch (Exception e){
          log.error("SQL statement error or that place is empty");
          result.setCode(Constants.RESP_STATUS_INTERNAL_ERROR);
          result.setMessage("内部错误");
      }
      return  result;

  }


  @GetMapping("SelectInfo")
  public Boolean  SelectInfo(String uuid){
        Boolean result=userService.SelectInfo(uuid);
        return result;
  }

  //企业付款到零钱
  @GetMapping("spay")
   public String spay(HttpServletRequest request,String uuid,String price) throws UnsupportedEncodingException {
      Map<String, String> restmap = null;
      User user=userService.SelectOpenid(uuid);
      try {
          Map<String, String> parm = new HashMap<String, String>();
          parm.put("mch_appid", Constants.WEXING_STATUS_APPID); //公众账号appid
          parm.put("mchid", Constants.MCH_ID); //商户号
          parm.put("nonce_str", MD5Util.getNonceStr()); //随机字符串
          parm.put("partner_trade_no", MD5Util.getTransferNo()); //商户订单号
          parm.put("openid", user.getOpenid()); //用户openid
          parm.put("check_name", "NO_CHECK"); //校验用户姓名选项 OPTION_CHECK
          //parm.put("re_user_name", "安迪"); //check_name设置为FORCE_CHECK或OPTION_CHECK，则必填
          parm.put("amount", price); //转账金额
          parm.put("desc", "测试转账到个人"); //企业付款描述信息
          parm.put("spbill_create_ip",request.getRemoteAddr() ); //Ip地址
          parm.put("sign", MD5Util.getSign(parm, Constants.KEY));
          String restxml = HttpUtil.posts(Constants.TRANSFERS_PAY, XmlUtil.xmlFormat(parm, false));
          System.out.println(restxml);
          restmap = XmlUtil.xmlParse(restxml);
      } catch (Exception e) {
          log.error(e.getMessage(), e);
      }

      System.out.println(restmap);
      if (CollectionUtil.isNotEmpty(restmap) && "SUCCESS".equals(restmap.get("result_code"))) {
          System.out.println("ok");
      }
     return null;
  }



}
package com.azkj.toolpowder.merchant.controller;


import com.azkj.toolpowder.common.constants.Constants;
import com.azkj.toolpowder.common.exception.ToolPowderExcption;
import com.azkj.toolpowder.common.resp.ApiResult;
import com.azkj.toolpowder.merchant.entity.Coupon;
import com.azkj.toolpowder.merchant.entity.Details;
import com.azkj.toolpowder.merchant.entity.Merchant;
import com.azkj.toolpowder.merchant.service.MerchantService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin
public class MerchantController{

    @Autowired
    @Qualifier("merchantServiceImpl")
    private MerchantService merchantService;


    @ApiOperation(value = "精选商家",notes = "精选商家",httpMethod = "GET")
    @ApiImplicitParam
    @GetMapping("SelectMerchant")
    public ApiResult SelectMerchant(){
        ApiResult<List<List<Merchant>>> result=new ApiResult();
        try {
            List<List<Merchant>> merchantList=merchantService.SelectMerchant();
            result.setData(merchantList);
            result.setMessage("精选商家查询成功");
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


    @ApiOperation(value = "分类商家",notes = "分类商家",httpMethod = "GET")
    @ApiImplicitParam
    @GetMapping("SelectSortMerchant")
    public ApiResult SelectSortMerchant(Integer id){
        ApiResult<List<Merchant>> result=new ApiResult();
        try {
            List<Merchant> merchantList=merchantService.SelectSortMerchant(id);
            result.setData(merchantList);
            result.setMessage("商家查询成功");
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

    @ApiOperation(value = "商家详情",notes = "商家详情",httpMethod = "GET")
    @ApiImplicitParam
    @GetMapping("SelectDetails")
    public ApiResult SelectDetails(Integer id){
        ApiResult<Details> result=new ApiResult();
        try {
            Details details=merchantService.SelectDetails(id);
            result.setData(details);
            result.setMessage("商家详情查询成功");
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


    @ApiOperation(value = "查看优惠卷",notes = "查看优惠卷",httpMethod = "GET")
    @ApiImplicitParam
    @GetMapping("SelectCoupon")
    public ApiResult SelectCoupon(){
        ApiResult<List<Coupon>> result=new ApiResult();
        try {
            List<Coupon> couponList=merchantService.SelectCoupon();
            result.setData(couponList);
            result.setMessage("优惠卷查询成功");
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



    @ApiOperation(value = "查看商户优惠卷",notes = "查看商户优惠卷",httpMethod = "GET")
    @ApiImplicitParam
    @GetMapping("SelectByCoupon")
    public ApiResult SelectByCoupon(Integer id){
        ApiResult<List<Coupon>> result=new ApiResult();
        try {
            List<Coupon> couponList=merchantService.SelectByCoupon(id);
            result.setData(couponList);
            result.setMessage("优惠卷查询成功");
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
}

package com.azkj.toolpowder.business.controller;

import com.azkj.toolpowder.business.entity.Bulletincd;
import com.azkj.toolpowder.business.service.BulletincdService;
import com.azkj.toolpowder.common.constants.Constants;
import com.azkj.toolpowder.common.exception.ToolPowderExcption;
import com.azkj.toolpowder.common.resp.ApiResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@CrossOrigin
@Slf4j
public class BulletincdController {

    @Autowired
    @Qualifier("bulletincdServiceImpl")
    private BulletincdService bulletincdService;


    @ApiOperation(value = "倒计时",notes = "倒计时",httpMethod = "GET")
    @ApiImplicitParam
    @GetMapping("SelectBulletincd")
    public ApiResult SelectBulletincd(){
        ApiResult<Bulletincd> result=new ApiResult();
        try {
            Bulletincd bulletincd=bulletincdService.SelectBulletincd();
            result.setData(bulletincd);
            result.setMessage("倒计时查询成功");
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

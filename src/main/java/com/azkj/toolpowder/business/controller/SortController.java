package com.azkj.toolpowder.business.controller;

import com.azkj.toolpowder.business.entity.Sort;
import com.azkj.toolpowder.business.service.SortService;
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

import java.util.List;

@RestController
@Slf4j
@CrossOrigin
public class SortController {

    @Autowired
    @Qualifier("sortServiceImpl")
    private SortService sortService;

    @ApiOperation(value = "分类",notes = "分类",httpMethod = "GET")
    @ApiImplicitParam
    @GetMapping("SelectSort")
    public ApiResult SelectSort(){
        ApiResult<List<Sort>> result=new ApiResult();
        try {
            List<Sort> musicList=sortService.SelectSort();
            result.setData(musicList);
            result.setMessage("分类查询成功");
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


    @ApiOperation(value = "更多分类",notes = "更多分类",httpMethod = "GET")
    @ApiImplicitParam
    @GetMapping("SelectMoreSort")
    public ApiResult SelectMoreSort(){
        ApiResult<List<Sort>> result=new ApiResult();
        try {
            List<Sort> musicList=sortService.SelectMoreSort();
            result.setData(musicList);
            result.setMessage("分类查询成功");
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

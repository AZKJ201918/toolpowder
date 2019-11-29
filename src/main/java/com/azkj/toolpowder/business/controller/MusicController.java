package com.azkj.toolpowder.business.controller;

import com.azkj.toolpowder.business.entity.Music;
import com.azkj.toolpowder.business.service.MusicService;
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
public class MusicController {

    @Autowired
    @Qualifier("musicServiceImpl")
    private MusicService musicService;


    @ApiOperation(value = "音乐",notes = "音乐",httpMethod = "GET")
    @ApiImplicitParam
    @GetMapping("SelectMusic")
    public ApiResult SelectMusic(){
        ApiResult<List<Music>> result=new ApiResult();
        try {
            List<Music> musicList=musicService.SelectMusic();
            result.setData(musicList);
            result.setMessage("音乐查询成功");
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

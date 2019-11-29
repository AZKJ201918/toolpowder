package com.azkj.toolpowder.business.service.Impl;

import com.azkj.toolpowder.business.dao.ActivityMapper;
import com.azkj.toolpowder.business.entity.Activity;
import com.azkj.toolpowder.business.service.ActivityService;
import com.azkj.toolpowder.common.exception.ToolPowderExcption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("activityServiceImpl")
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;


    @Override
    public List<Activity> SelectActivity() throws ToolPowderExcption {
        return activityMapper.SelectActivity();
    }
}

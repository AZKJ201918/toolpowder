package com.azkj.toolpowder.business.service;

import com.azkj.toolpowder.business.entity.Activity;
import com.azkj.toolpowder.common.exception.ToolPowderExcption;

import java.util.List;

public interface ActivityService {

    List<Activity> SelectActivity() throws ToolPowderExcption;
}

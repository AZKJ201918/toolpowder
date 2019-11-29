package com.azkj.toolpowder.business.service;

import com.azkj.toolpowder.business.entity.Sort;
import com.azkj.toolpowder.common.exception.ToolPowderExcption;

import java.util.List;

public interface SortService  {
    List<Sort> SelectSort()throws ToolPowderExcption;

    List<Sort> SelectMoreSort()throws ToolPowderExcption;
}

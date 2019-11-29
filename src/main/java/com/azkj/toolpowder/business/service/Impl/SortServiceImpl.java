package com.azkj.toolpowder.business.service.Impl;

import com.azkj.toolpowder.business.dao.SortMapper;
import com.azkj.toolpowder.business.entity.Sort;
import com.azkj.toolpowder.business.service.SortService;
import com.azkj.toolpowder.common.exception.ToolPowderExcption;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service("sortServiceImpl")
public class SortServiceImpl implements SortService {

    @Autowired
    private SortMapper sortMapper;

    @Override
    public List<Sort> SelectSort() throws ToolPowderExcption {
        List<Sort> sortList=sortMapper.SelectSort();
        if(CollectionUtils.isNotEmpty(sortList)){
            return sortList;
        }else{
            throw new ToolPowderExcption("没有分类");
        }

    }

    @Override
    public List<Sort> SelectMoreSort() {
        return sortMapper.SelectMoreSort();
    }
}

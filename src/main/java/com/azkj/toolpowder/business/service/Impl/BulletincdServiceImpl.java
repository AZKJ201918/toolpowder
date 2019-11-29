package com.azkj.toolpowder.business.service.Impl;

import com.azkj.toolpowder.business.dao.BulletincdMapper;
import com.azkj.toolpowder.business.entity.Bulletincd;
import com.azkj.toolpowder.business.service.BulletincdService;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("bulletincdServiceImpl")
public class BulletincdServiceImpl implements BulletincdService {

    @Autowired
    private BulletincdMapper bulletincdMapper;

    @Override
    public Bulletincd SelectBulletincd() {
        return bulletincdMapper.SelectBulletincd();
    }
}

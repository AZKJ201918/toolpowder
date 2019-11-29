package com.azkj.toolpowder.business.service;

import com.azkj.toolpowder.business.entity.Bulletincd;
import com.azkj.toolpowder.common.exception.ToolPowderExcption;

public interface BulletincdService {

    Bulletincd SelectBulletincd() throws ToolPowderExcption;
}

package com.azkj.toolpowder.business.service;

import com.azkj.toolpowder.business.entity.Music;
import com.azkj.toolpowder.common.exception.ToolPowderExcption;

import java.util.List;

public interface MusicService {


    List<Music> SelectMusic() throws ToolPowderExcption;
}

package com.azkj.toolpowder.business.service.Impl;

import com.azkj.toolpowder.business.dao.MusicMapper;
import com.azkj.toolpowder.business.entity.Music;
import com.azkj.toolpowder.business.service.MusicService;
import com.azkj.toolpowder.common.exception.ToolPowderExcption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service("musicServiceImpl")
public class MusicServiceImpl implements MusicService {


    private static AtomicInteger counter = new AtomicInteger(0);

    @Autowired
    private MusicMapper musicMapper;

    @Override
    public List<Music> SelectMusic() throws ToolPowderExcption {
        List<Music> musicList=new ArrayList<>();
        Integer count=musicMapper.SelectMusicCount();
        Integer id=(counter.get()%count)+1;
        Music music=musicMapper.selectByPrimaryKey(id);
        musicList.add(music);
        List<Music> listmusic=musicMapper.selectByAll(id);
        for(Music musics:listmusic){
            musicList.add(musics);
        }
        counter.incrementAndGet();
        return musicList;
    }
}

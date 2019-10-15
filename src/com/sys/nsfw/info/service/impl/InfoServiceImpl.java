package com.sys.nsfw.info.service.impl;

import com.sys.basecore.service.BaseService;
import com.sys.basecore.service.impl.BaseServiceImpl;
import com.sys.nsfw.info.dao.InfoDao;
import com.sys.nsfw.info.entity.Info;
import com.sys.nsfw.info.service.InfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("infoService")
public class InfoServiceImpl extends BaseServiceImpl<Info> implements InfoService {

    private InfoDao infoDao;

    @Resource
    public void setInfoDao(InfoDao infoDao) {
        super.setBaseDao(infoDao);
        this.infoDao = infoDao;
    }
}

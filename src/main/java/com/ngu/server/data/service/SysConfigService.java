package com.ngu.server.data.service;

import com.ngu.server.data.entity.SysConfig;

import java.util.List;

public interface SysConfigService {

    List<SysConfig> getSysConfig(String type);

    void saveOrUpdateSysConfigBatch(List<SysConfig> sysConfigList);
}

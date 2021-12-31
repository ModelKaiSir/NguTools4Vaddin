package com.ngu.server.data.service.impl;

import com.ngu.server.data.dto.DataSourceConfigInfo;
import com.ngu.server.data.service.GenerateServerConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class GenerateServerConfigServiceImpl implements GenerateServerConfigService {

    private DataSourceConfigInfo SYS_CONFIG = new DataSourceConfigInfo();

    @PostConstruct
    public void start(){
        SYS_CONFIG.setDataBaseUrl("jdbc:mysql://47.107.122.122:3306/ehr3-dev?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        SYS_CONFIG.setDriverName("com.mysql.cj.jdbc.Driver");
        SYS_CONFIG.setUserName("root");
        SYS_CONFIG.setPassword("Ed26_diYgje@#ds;fHHdD145D2HU2HBdS");
    }

    @Override
    public DataSourceConfigInfo getServerConfig() {
        return SYS_CONFIG;
    }

    @Override
    public void setServerConfig(DataSourceConfigInfo bean) {
        SYS_CONFIG = bean;
    }
}

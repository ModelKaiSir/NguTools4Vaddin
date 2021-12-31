package com.ngu.server.data.service;

import com.ngu.server.data.dto.DataSourceConfigInfo;

public interface GenerateServerConfigService {

    DataSourceConfigInfo getServerConfig();

    void setServerConfig(DataSourceConfigInfo bean);
}

package com.ngu.server.data.dto;

import lombok.Data;

/**
 *
 * 数据库配置
 */
@Data
public class DataSourceConfigInfo {

    private String dataBaseUrl;
    private String driverName;
    private String userName;
    private String password;
}

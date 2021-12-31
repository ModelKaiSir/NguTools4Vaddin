package com.ngu.server.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("sys_config")
public class SysConfig {

    @TableField("type")
    private String type;

    @TableField("alias")
    private String alias;

    @TableField("value")
    private String value;
}

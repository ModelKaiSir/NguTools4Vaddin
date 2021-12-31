package com.ngu.server.data.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 *
 * 生成代码配置
 */
@Data
public class DataSourceGenerateConfig {

    @NotBlank(message = "项目不能为空")
    private String project;

    @NotBlank(message = "项目包路径不能为空")
    private String packagePath;

    @NotBlank(message = "作者不能为空")
    private String author;

    @NotBlank(message = "表名不能为空")
    private String tableNames;
}

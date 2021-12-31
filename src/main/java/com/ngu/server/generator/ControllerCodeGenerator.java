package com.ngu.server.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.ngu.server.data.dto.DataSourceConfigInfo;
import com.ngu.server.data.dto.DataSourceGenerateConfig;
import com.ngu.server.generator.constant.TemplatePath;

import java.util.HashMap;
import java.util.Map;

public class ControllerCodeGenerator {

    public static void generate(DataSourceConfigInfo info, DataSourceGenerateConfig config) {

        AutoGenerator generator = new AutoGenerator();
        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");

        String outputPath = projectPath + "/" + config.getProject() + "/src/main/java";
        globalConfig.setOutputDir(outputPath);
        globalConfig.setAuthor(config.getAuthor());
        globalConfig.setOpen(false);
        globalConfig.setFileOverride(false);//默认false不要覆盖
        globalConfig.setBaseColumnList(true);
        globalConfig.setBaseResultMap(true);
        globalConfig.setSwagger2(true);
        generator.setGlobalConfig(globalConfig);

        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig().setDbQuery(new MySqlQuery() {

            /**
             * 重写父类预留查询自定义字段<br>
             * 这里查询的 SQL 对应父类 tableFieldsSql 的查询字段，默认不能满足你的需求请重写它<br>
             * 模板中调用：  table.fields 获取所有字段信息，
             * 然后循环字段获取 field.customMap 从 MAP 中获取注入字段如下  NULL 或者 PRIVILEGES
             */
            @Override
            public String[] fieldCustom() {
                return new String[]{"NULL", "PRIVILEGES"};
            }
        });

        dataSourceConfig.setUrl(info.getDataBaseUrl());
        dataSourceConfig.setDriverName(info.getDriverName());
        dataSourceConfig.setUsername(info.getUserName());
        dataSourceConfig.setPassword(info.getPassword());
        generator.setDataSource(dataSourceConfig);

        // 包配置
        PackageConfig packageConfig = new PackageConfig();

        // 包的基础路径
        String packagePath = config.getPackagePath();
        packageConfig.setParent(packagePath);
        generator.setPackageInfo(packageConfig);

        // 配置自定义代码模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        templateConfig.setMapper(null);
        templateConfig.setEntity(null);
        templateConfig.setService(null);
        templateConfig.setServiceImpl(null);
        templateConfig.setController(TemplatePath.CONTROLLER_TEMPLATE_PATH);
        generator.setTemplate(templateConfig);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
                Map<String, Object> map = new HashMap<>();
                map.put("custombasepath", packagePath);
                map.put("dto", "dto");
                map.put("customcommon", "com.ngu.hrms.common");//通用的查询相关
                this.setMap(map);
            }
        };
        generator.setCfg(cfg);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();

        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);

        String[] tables = config.getTableNames().split(",");
        strategy.setInclude(tables);

        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(packageConfig.getModuleName() + "_");
        strategy.setEntityTableFieldAnnotationEnable(true);
        generator.setStrategy(strategy);
        generator.setTemplateEngine(new FreemarkerTemplateEngine());
        generator.execute();
    }
}



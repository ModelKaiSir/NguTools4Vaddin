package com.ngu.server.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.ngu.server.data.dto.DataSourceConfigInfo;
import com.ngu.server.data.dto.DataSourceGenerateConfig;
import com.ngu.server.generator.constant.ProjectCodeGeneratorConstant;
import com.ngu.server.generator.constant.TemplatePath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectCodeGenerator {

    public static void generate(DataSourceConfigInfo info, DataSourceGenerateConfig config) {

        AutoGenerator generator = new AutoGenerator();

        // 全局配置
        int templateType = 1;
        //模板类型
        String moduleName = config.getProject();
        String author = config.getAuthor();
        String outputPath = SetGlobalConfig(generator, author, moduleName, templateType);

        // 数据源配置
        setDataSource(info, generator);
        // 包配置
        PackageConfig packageConfig = new PackageConfig();
        // 包的基础路径
        String packagePath = config.getPackagePath();
        packageConfig.setParent(packagePath);
        generator.setPackageInfo(packageConfig);

        // 配置自定义代码模板
        SetTemplateConfig(generator, templateType);
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
                Map<String, Object> map = new HashMap<>();
                map.put("custombasepath", packagePath);
                map.put("dto", "dto");
                map.put("customcommon", "com.ngu.hrms.common.info");//通用的查询相关
                this.setMap(map);
            }
        };
        generator.setCfg(cfg);

        // 自定义输出配置
        IsSetFileOutConfigList(cfg, false, packagePath, outputPath);

        // 策略配置
        String dataTables = config.getTableNames();
        String[] tables = dataTables.split(",");
        SetStrategyConfig(generator, packageConfig, tables);
        System.out.println("开始生成实体类：");
        generator.execute();
        System.out.println("\r\n");
        //-----------------------------------------------------------------------------------------
        templateType = 2;//模板类型 service
        generator.setConfig(null);
        outputPath = SetGlobalConfig(generator, author, moduleName, templateType);
        // 配置自定义代码模板
        SetTemplateConfig(generator, templateType);
        // 自定义输出配置
        IsSetFileOutConfigList(cfg, true, packagePath, outputPath);

        generator.setCfg(cfg);
        SetStrategyConfig(generator, packageConfig, tables);
        System.out.println("开始生成服务类：");
        generator.execute();
        //-----------------------------------------------------------------------------------------
        templateType = 3;//模板类型 service
        generator.setConfig(null);
        outputPath = SetGlobalConfig(generator, author, moduleName, templateType);
        // 配置自定义代码模板
        SetTemplateConfig(generator, templateType);
        // 自定义输出配置
        cfg = new InjectionConfig() {
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
        SetStrategyConfig(generator, packageConfig, tables);
        System.out.println("\r\n开始生成controller类：");
        generator.execute();
    }

    //templateType模板类型，1 实体，2，servcie，3，controller
    public static String SetGlobalConfig(AutoGenerator generator, String author, String moduleName, int templateType) {
        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");

        String temp = "";
        if (templateType == 1) {
            temp = ProjectCodeGeneratorConstant.Module_Entity.replace("#", moduleName);
        } else if (templateType == 2) {
            temp = ProjectCodeGeneratorConstant.Module_Biz.replace("#", moduleName);
        } else if (templateType == 3) {
            temp = ProjectCodeGeneratorConstant.Module_Controller.replace("#", moduleName);
        }
        String outputPath = projectPath + "/" + temp + "/src/main/java";
        globalConfig.setOutputDir(outputPath);

        globalConfig.setAuthor(author);
        globalConfig.setOpen(false);
        globalConfig.setFileOverride(false);//默认false不要覆盖
        globalConfig.setBaseColumnList(true);
        globalConfig.setBaseResultMap(true);
        globalConfig.setSwagger2(true);
        globalConfig.setDateType(DateType.ONLY_DATE);//定义生成的实体类中日期类型
        generator.setGlobalConfig(globalConfig);
        return outputPath;
    }

    //配置数据源
    public static void setDataSource(DataSourceConfigInfo info, AutoGenerator generator) {
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
    }

    //设置模板，templateType模板类型，1 实体，2，servcie，3，controller
    public static void SetTemplateConfig(AutoGenerator generator, int templateType) {
        // 配置自定义代码模板
        TemplateConfig templateConfig = new TemplateConfig();

        if (templateType == 1) {
            templateConfig.setEntity(TemplatePath.ENTITY_TEMPLATE_PATH);
            templateConfig.setMapper(null);
            templateConfig.setXml(null);
            templateConfig.setService(null);
            templateConfig.setServiceImpl(null);
            templateConfig.setController(null);
        } else if (templateType == 2) {
            templateConfig.setXml(TemplatePath.MaperXmlTemplateFilePath);
            templateConfig.setMapper(TemplatePath.MapperTemplateFilePath);
            templateConfig.setEntity(null);
            templateConfig.setService(TemplatePath.SERVICE_TEMPLATE_PATH);
            templateConfig.setServiceImpl(TemplatePath.SERVICE_IMPL_TEMPLATE_PATH);
            templateConfig.setController(null);
        } else if (templateType == 3) {
            templateConfig.setXml(null);
            templateConfig.setMapper(null);
            templateConfig.setEntity(null);
            templateConfig.setService(null);
            templateConfig.setServiceImpl(null);
            templateConfig.setController(TemplatePath.CONTROLLER_TEMPLATE_PATH);
        }
        generator.setTemplate(templateConfig);
    }

    //是否设置自定义file输出
    public static void IsSetFileOutConfigList(InjectionConfig cfg, boolean isClearSet, String packagePath, String outputPath) {

        if (isClearSet) {
            cfg.setFileOutConfigList(null);
            return;
        }

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();

        focList.add(new FileOutConfig(TemplatePath.Querydto_TEMPLATE_PATH) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return outputPath + "/" + packagePath.replace(".", "/") + "/dto/" + tableInfo.getEntityName().toLowerCase().replace("_", "") + "dto/" + tableInfo.getEntityName() + "QueryDto.java";
            }
        });

        focList.add(new FileOutConfig(TemplatePath.Adddto_TEMPLATE_PATH) {

            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return outputPath + "/" + packagePath.replace(".", "/") + "/dto/" + tableInfo.getEntityName().toLowerCase().replace("_", "") + "dto/" + tableInfo.getEntityName() + "AddDto.java";
            }
        });

        focList.add(new FileOutConfig(TemplatePath.Updatedto_TEMPLATE_PATH) {

            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return outputPath + "/" + packagePath.replace(".", "/") + "/dto/" + tableInfo.getEntityName().toLowerCase().replace("_", "") + "dto/" + tableInfo.getEntityName() + "UpdateDto.java";
            }
        });

        cfg.setFileOutConfigList(focList);
    }

    //策略配置
    public static void SetStrategyConfig(AutoGenerator generator, PackageConfig packageConfig, String[] tables) {

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setInclude(tables);

        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(packageConfig.getModuleName() + "_");
        strategy.setEntityTableFieldAnnotationEnable(true);
        generator.setStrategy(strategy);
        generator.setTemplateEngine(new FreemarkerTemplateEngine());
    }
}

package ${package.Controller};

import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import io.swagger.annotations.Api;
import ${cfg.customcommon}.annotation.Log;
import ${cfg.customcommon}.exception.NguException;
import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.ApiOperation;
import ${cfg.customcommon}.info.PageQueryRequest;
import ${cfg.customcommon}.info.PageResult;
import ${cfg.custombasepath}.${cfg.dto}.${entity? lower_case}dto.${entity}QueryDto;

<#if restControllerStyle>
    import org.springframework.web.bind.annotation.RestController;
<#else>
    import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
    import ${superControllerClassPackage};
</#if>

/**
* @author ${author}
*/
@Slf4j
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
<#--@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")-->
@Api(tags="${table.comment!}")
@RequestMapping("${entity}")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>
    @Autowired
    private ${table.serviceName} ${entity? uncap_first }Service;

    @ApiOperation("获取${table.comment!}分页数据")
    @PostMapping("list")
    //@PreAuthorize("hasAuthority('${entity?uncap_first}:list')")
    public PageResult<${entity}> ${entity?uncap_first}List(@RequestBody PageQueryRequest<${entity}QueryDto> data) throws NguException{
     try {
         return this.${entity? uncap_first }Service.find${entity}s(data);
     } catch (Exception e) {
            if(e instanceof NguException)throw e;
            String message = "获取${table.comment!}失败";
            log.error(message, e);
            throw new NguException(message);
        }
    }

    @Log(action ="新增${table.comment!}",module ="${table.comment!}")
    @ApiOperation("新增${table.comment!}")
    @PostMapping
    public boolean add${entity}(@RequestBody ${entity} ${entity? uncap_first }) throws NguException {
        try {
             return this.${entity? uncap_first }Service.save(${entity? uncap_first });
        } catch (Exception e) {
            if(e instanceof NguException)throw e;
            String message = "新增${table.comment!}失败";
            log.error(message, e);
            throw new NguException(message);
        }
    }

    @GetMapping("/get/{id}")
    @ApiOperation("获取${table.comment!}")
    public ${entity} detail(@PathVariable <#list table.fields as field><#if field.keyFlag>${field.propertyType}</#if></#list> id) throws NguException {
        try {
             return this.${entity? uncap_first }Service.getById(id);
        } catch (Exception e) {
            if(e instanceof NguException)throw e;
            String message = "获取${table.comment!}失败";
            log.error(message, e);
            throw new NguException(message);
        }
    }

    @Log(action = "删除${table.comment!}",module ="${table.comment!}")
    @ApiOperation("删除${table.comment!}")
    @DeleteMapping("/Delete/{id}")
    public boolean delete${entity}(@PathVariable <#list table.fields as field><#if field.keyFlag>${field.propertyType}</#if></#list> id) throws NguException {
        try {
             return this.${entity? uncap_first }Service.removeById(id);
        } catch (Exception e) {
            if(e instanceof NguException)throw e;
            String message = "删除${table.comment!}失败";
            log.error(message, e);
            throw new NguException(message);
        }
    }

    @Log(action = "修改${table.comment!}",module ="${table.comment!}")
    @ApiOperation("修改${table.comment!}")
    @PutMapping
    //@PreAuthorize("hasAuthority('${entity}:update')")
    public boolean update${entity}(@RequestBody ${entity} ${entity? uncap_first })throws NguException {
        try {
             return this.${entity? uncap_first }Service.saveOrUpdate(${entity? uncap_first });
        } catch (Exception e) {
            if(e instanceof NguException)throw e;
            String message = "修改${table.comment!}失败";
            log.error(message, e);
            throw new NguException(message);
        }
    }
}
    </#if>

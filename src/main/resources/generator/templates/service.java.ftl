package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import ${cfg.customcommon}.PageResult;
import ${cfg.customcommon}.PageQueryRequest;
import ${cfg.custombasepath}.${cfg.dto}.${entity? lower_case}dto.${entity}QueryDto;

/**
 * @author ${author}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

 /**
 * 查询（分页）
 * @param data PageQueryRequest<${entity}QueryDto>
 * @return PageResult<${entity}>
 */
 PageResult<${entity}> find${entity}s(PageQueryRequest<${entity}QueryDto> data);

}
</#if>

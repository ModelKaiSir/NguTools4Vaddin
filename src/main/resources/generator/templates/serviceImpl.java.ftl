package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${cfg.customcommon}.PageResult;
import ${cfg.customcommon}.PageQueryRequest;
import ${cfg.custombasepath}.${cfg.dto}.${entity? lower_case}dto.${entity}QueryDto;
/**
 * @author ${author}
 */
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {

 @Override
 public PageResult<${entity}> find${entity}s(PageQueryRequest<${entity}QueryDto> data) {
 LambdaQueryWrapper<${entity}> queryWrapper = new LambdaQueryWrapper<>();
 // TODO 设置查询条件
 // eg： queryWrapper.eq(HrEmpinfoType::getEtName,hrEmpinfoType.getEtCode());
 Page<${entity}> page = new Page<>(data.getPageNum(), data.getPageSize());
    return new PageResult<${entity}>(this.page(page, queryWrapper));
 }
}
</#if>

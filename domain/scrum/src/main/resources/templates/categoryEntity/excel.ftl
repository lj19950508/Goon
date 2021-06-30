package pers.gon.manage.${moduleName}.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import pers.gon.domain.${relatedTree.moduleName}.entity.${relatedTree.entityName?cap_first};

import pers.gon.domain.${moduleName}.entity.${upEntityName};
import java.math.*;
import java.util.*;
<#list items as item>
    <#if item["itemType"]=="UpmsDept">
import pers.gon.domain.upms.entity.UpmsDept;
    </#if>
</#list>


@Data
public class ${upEntityName}Excel {

<#list items as item>
    @ExcelProperty("${item["itemDesc"]}")
    private ${item["itemType"]} ${item["itemName"]};
</#list>
    @ExcelProperty("${relatedTree.entityName}")
    private ${relatedTree.entityName?cap_first} ${relatedTree.entityName};

    public static List<${upEntityName}Excel> transform(List<${upEntityName}> ${entityName}s){
        List<${upEntityName}Excel> ${entityName}Excels = new ArrayList<>();
        ${entityName}s.forEach(item->{
            ${upEntityName}Excel ${entityName}Excel = new ${upEntityName}Excel();
        <#list items as item>
            ${entityName}Excel.set${item["itemName"]?cap_first}(item.get${item["itemName"]?cap_first}());
        </#list>
            ${entityName}Excels.add(${entityName}Excel);
        });
        return ${entityName}Excels;
    }
}

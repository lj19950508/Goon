package pers.gon.web.${moduleName}.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import pers.gon.domain.upms.entity.${upEntityName};
import java.math.*;
import java.util.*;

@Data
public class ${upEntityName}Excel {

<#list items as item>
    @ExcelProperty("${item["itemDesc"]}")
    private ${item["itemType"]} ${item["itemName"]};
</#list>

    public static List<${upEntityName}Excel> transform(List<${upEntityName}> ${entityName}s){
        List<${upEntityName}Excel> ${entityName}Excels = new ArrayList<>();
        ${entityName}s.forEach(item->{
        ${upEntityName}Excel ${entityName}Excel = new ${upEntityName}Excel();
        <#list items as item>
            ${entityName}Excel.set${item["itemName"]?cap_first}(item.get${item["itemName"]?cap_first}());
        </#list>

        });
        return ${entityName}Excels;
    }
}

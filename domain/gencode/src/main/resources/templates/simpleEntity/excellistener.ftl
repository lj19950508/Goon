package pers.gon.web.${moduleName}.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;
import pers.gon.domain.${moduleName}.entity.${upEntityName};
import pers.gon.domain.${moduleName}.service.I${upEntityName}Service;
import java.math.*;
import java.util.*;

@Slf4j
public class ${upEntityName}ExcelListener extends AnalysisEventListener<${upEntityName}Excel> {

    /**
    * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
    */
    private static final int BATCH_COUNT = 100;
    List<${upEntityName}Excel> list = new ArrayList<${upEntityName}Excel>();
    private I${upEntityName}Service ${entityName}Service;

    public ${upEntityName}ExcelListener(){
    }

    @Override
    public void invoke(${upEntityName}Excel data, AnalysisContext analysisContext) {
        log.info("数据:{}",data);
        list.add(data);
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData();
    }

    private void saveData() {
        List<${upEntityName}> ${entityName}s = new ArrayList<>();
        list.forEach(item->{
            ${upEntityName} ${entityName} = new ${upEntityName}();
            <#list items as item>
            ${entityName}.set${item["itemName"]?cap_first}(item.get${item["itemName"]?cap_first}());
            </#list>
            ${entityName}s.add(${entityName});
        });
        ${entityName}Service.saveAll(${entityName}s);
    }
}

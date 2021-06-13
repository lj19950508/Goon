package pers.gon.domain.${moduleName}.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import pers.gon.domain.DataEntity;
import pers.gon.domain.${moduleName}.repository.${upEntityName}Repository;
import pers.gon.infrastructure.common.valid.InsertGroup;
import pers.gon.infrastructure.common.valid.SaveGroup;
import pers.gon.infrastructure.common.valid.Unique;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.*;
import java.util.*;




@Entity
@Getter
@Setter
@Table(name = "${moduleName}_${underEntityName}")
<#if isLogicDelete == true >
@SQLDelete(sql = "update ${moduleName}_${underEntityName} set del_flag = 1 where id = ?")
@Where(clause = "del_flag = 0")
</#if>
/**
 * ${entityDesc}
 */
public class ${upEntityName} extends DataEntity {

<#list items as item>
    //${item["itemDesc"]}
    <#if item.must==true>
        <#if item.itemType!='String'>
    ${r'@'}NotNull(message = "${item["itemDesc"]}不能未空",groups = SaveGroup.class)
        </#if>
        <#if item.itemType=='String'>
    ${r'@'}NotEmpty(message = "${item["itemDesc"]}不能未空",groups = SaveGroup.class)
        </#if>
    </#if>
    ${r'@'}Column(<#if item.must==true>nullable = false,</#if>columnDefinition = " ${item["sqlType"]}<#if item.sqlLength!=''>(${item["sqlLength"]})</#if>  comment '${item["itemDesc"]}'")
    <#if item.unrepeat==true>
    ${r'@'}Unique(repository = ${upEntityName}Repository.class,fieldName = "${item["itemName"]}",groups = InsertGroup.class,message = "${item["itemDesc"]}已存在")
    </#if>
    private ${item["itemType"]} ${item["itemName"]};
</#list>

<#if isLogicDelete == true >
    @JsonIgnore
    @Column(columnDefinition = "char(1) comment '逻辑删除'")
    private boolean delFlag;
</#if>

}

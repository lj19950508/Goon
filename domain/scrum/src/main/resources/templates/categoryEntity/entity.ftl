package pers.gon.domain.${moduleName}.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import pers.gon.domain.DataEntity;
import pers.gon.domain.${relatedTree.moduleName}.repository.${relatedTree.entityName?cap_first}Repository;
import pers.gon.domain.${moduleName}.repository.${upEntityName}Repository;
import pers.gon.infrastructure.common.valid.InsertGroup;
import pers.gon.infrastructure.common.valid.SaveGroup;
import pers.gon.infrastructure.common.valid.Unique;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import pers.gon.infrastructure.common.valid.Exist;
import java.math.*;
import java.util.*;
<#list items as item>
    <#if item["itemType"]=="UpmsDept">
import pers.gon.domain.upms.entity.UpmsDept;
import pers.gon.domain.upms.repository.UpmsDeptRepository;
import pers.gon.infrastructure.common.valid.Exist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
    </#if>
</#list>

@Entity
@Getter
@Setter
@Table(name = "${moduleName}_${underEntityName}")
<#if isLogicDelete == true >
@SQLDelete(sql = "update ${moduleName}_${underEntityName} set del_flag = 1 where id = ?")
@Where(clause = "del_flag = 0")
@org.hibernate.annotations.Table(appliesTo = "${moduleName}_${underEntityName}",comment = "${entityDesc}")
</#if>
<#list items as item>
    <#if item["itemType"]=="UpmsDept">
        <#if item.unrepeat==true>
${r'@'}Unique(repository = ${upEntityName}Repository.class,fieldName = "${item["itemName"]}.id",groups = SaveGroup.class,message = "${item["itemDesc"]}已存在")
        </#if>
    <#else>
        <#if item.unrepeat==true>
${r'@'}Unique(repository = ${upEntityName}Repository.class,fieldName = "${item["itemName"]}",groups = SaveGroup.class,message = "${item["itemDesc"]}已存在")
        </#if>
    </#if>
</#list>
/**
 * ${entityDesc}
 */
public class ${upEntityName} extends DataEntity {

    @Exist(groups = SaveGroup.class,repository = ${relatedTree.entityName?cap_first}Repository.class,message = "${relatedTree.entityDesc}不存在")
    @JsonIgnoreProperties(value = {"updateBy","createBy","parent","children"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "${relatedTree.entityDesc}_id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    private ${relatedTree.entityName?cap_first} ${relatedTree.entityName};

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
    <#if item["itemType"]=="UpmsDept">
    @Exist(groups = SaveGroup.class,repository = UpmsDeptRepository.class,message = "部门不存在")
    @JsonIgnoreProperties(value = {"updateBy","createBy","parent","children"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dept_id",foreignKey = @ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    @NotFound(action= NotFoundAction.IGNORE)
    <#else>
    ${r'@'}Column(<#if item.must==true>nullable = false,</#if>columnDefinition = " ${item["sqlType"]}<#if item.sqlLength!=''>(${item["sqlLength"]})</#if>  comment '${item["itemDesc"]}'")
    </#if>
    private ${item["itemType"]} ${item["itemName"]};
</#list>

<#if isLogicDelete == true >
    @JsonIgnore
    @Column(columnDefinition = "char(1) comment '逻辑删除'")
    private boolean delFlag;
</#if>

}

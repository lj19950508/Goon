package pers.gon.manage.${moduleName};

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pers.gon.application.utils.CurrentAdminUtils;
import pers.gon.application.utils.DataScopeUtils;
import pers.gon.domain.${moduleName}.entity.${upEntityName};
import pers.gon.domain.${moduleName}.service.I${upEntityName}Service;
import pers.gon.infrastructure.common.controller.BaseController;
import pers.gon.infrastructure.common.entity.CommonResult;

import javax.persistence.criteria.Predicate;
import java.math.*;
import java.util.*;
<#if excel==true>
import com.alibaba.excel.EasyExcel;
import pers.gon.manage.${moduleName}.excel.${upEntityName}Excel;
import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;
import pers.gon.manage.${moduleName}.excel.${upEntityName}ExcelListener;
</#if>


@Slf4j
@Controller
@RequestMapping("${r'${global.adminpath}'}/${moduleName}/${entityName}")
public class ${upEntityName}Controller extends BaseController {
    @Autowired
    I${upEntityName}Service ${entityName}Service;

    @RequiresPermissions("${moduleName?upper_case}:${entityName?upper_case}:LIST")
    @RequestMapping("")
    public String index(Model model) {
        return "modules/${moduleName}/${entityName}/${entityName}.html";
    }

    @RequiresPermissions(value = {"${moduleName?upper_case}:${entityName?upper_case}:VIEW", "${moduleName?upper_case}:${entityName?upper_case}:ADD", "${moduleName?upper_case}:${entityName?upper_case}:EDIT"}, logical = Logical.OR)
    @RequestMapping("/form/{mode}")
    public String form(String id, Model model,@PathVariable String mode,String parentId) {
        if("view".equals(mode)||"edit".equals(mode)){
            ${upEntityName} ${entityName} = ${entityName}Service.findById(id);
            model.addAttribute("${entityName}",${entityName});
        }
        if(StrUtil.isNotEmpty(parentId) && "add".equals(mode)){
            ${upEntityName} ${entityName} =new ${upEntityName}();
            ${entityName}.setParent(${entityName}Service.findById(parentId));
            model.addAttribute("${entityName}",${entityName});
        }
        model.addAttribute("mode",mode);
        return "modules/${moduleName}/${entityName}/${entityName}Form.html";
    }

    @RequiresPermissions("${moduleName?upper_case}:${entityName?upper_case}:LIST")
    @ResponseBody
    @GetMapping("/page")
    public CommonResult page(${upEntityName} ${entityName}, Pageable pageable<#list items as item><#if item.queryExp==8>,String start${item["itemName"]?cap_first},String end${item["itemName"]?cap_first}</#if><#if item.formType!=6 && item.queryType ==4 >,String ${item["itemName"]}s</#if></#list>) {
        String msg = queryValidate(${entityName});
        if (StrUtil.isNotEmpty(msg)) {
            return CommonResult.fail(msg);
        }
        Specification specification = (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();//查询条件集
            <#list items as item>
            <#if item.queryType!=0>
                <#if item.queryExp!=8>
                    <#if  item.queryType ==4>
                        <#if item.itemType!='String'>
            if (null != ${item["itemName"]}s) {
                        </#if>
                        <#if item.itemType=='String'>
            if (StrUtil.isNotEmpty(${item["itemName"]}s)) {
                        </#if>
                    <#else>
                        <#if item.itemType!='String'>
            if (null != ${entityName}.get${item["itemName"]?cap_first}()) {
                        </#if>
                        <#if item.itemType=='String'>
            if (StrUtil.isNotEmpty(${entityName}.get${item["itemName"]?cap_first}())) {
                        </#if>
                    </#if>
                 </#if>
            <#if item.queryExp==8>
                <#if item.itemType!='String'>
            if (null != start${item["itemName"]?cap_first}) || null != end${item["itemName"]?cap_first}) {
                </#if>
                <#if item.itemType=='String'>
            if (StrUtil.isNotEmpty(start${item["itemName"]?cap_first}) || StrUtil.isNotEmpty(end${item["itemName"]?cap_first})) {
                </#if>
            </#if>
            <#if item.queryExp==0>
                <#if item.formType!=6 && item.queryType !=4>
                predicates.add(cb.equal(root.get("${item["itemName"]}"),${entityName}.get${item["itemName"]?cap_first}()));
                </#if>
                <#if item.formType==6 && item.queryType !=4>
                predicates.add(cb.like(root.get("${item["itemName"]}"),"%"+${entityName}.get${item["itemName"]?cap_first}()+"%"));
                </#if>
                <#if item.formType!=6 && item.queryType ==4>
                    List<Predicate> orPredicateList = new ArrayList<>();
                    String query[] = ${item["itemName"]}s.split(",");
                    for (int i = 0; i < query.length; i++) {
                        orPredicateList.add(cb.equal(root.get("${item["itemName"]}"),query[i]));
                    }
                    Predicate[] orPredicates = new Predicate[orPredicateList.size()];
                    Predicate orPredicate = cb.or(orPredicateList.toArray(orPredicates));
                    predicates.add(orPredicate);
                </#if>
                <#if item.formType==6 && item.queryType ==4>
                List<Predicate> orPredicateList = new ArrayList<>();
                String query[] =  ${entityName}.get${item["itemName"]?cap_first}().split(",");
                for (int i = 0; i < query.length; i++) {
                    orPredicateList.add(cb.like(root.get("${item["itemName"]}"),"%"+query[i]+"%"));
                }
                Predicate[] orPredicates = new Predicate[orPredicateList.size()];
                Predicate orPredicate = cb.or(orPredicateList.toArray(orPredicates));
                predicates.add(orPredicate);
                </#if>
            }
            </#if>
            <#if item.queryExp==1>
                predicates.add(cb.like(root.get("${item["itemName"]}"), "%" + ${entityName}.get${item["itemName"]?cap_first}() + "%"));
            }
            </#if>
            <#if item.queryExp==2>
                predicates.add(cb.like(root.get("${item["itemName"]}"), ${entityName}.get${item["itemName"]?cap_first}() + "%"));
            }
            </#if>
            <#if item.queryExp==3>
                predicates.add(cb.like(root.get("${item["itemName"]}"), "%" + ${entityName}.get${item["itemName"]?cap_first}() ));
            }
            </#if>
            <#if item.queryExp==4>
                predicates.add(cb.gt(root.get("${item["itemName"]}"),${entityName}.get${item["itemName"]?cap_first}()));
            }
            </#if>
            <#if item.queryExp==5>
                predicates.add(cb.lt(root.get("${item["itemName"]}"),${entityName}.get${item["itemName"]?cap_first}()));
            }
            </#if>
            <#if item.queryExp==6>
                predicates.add(cb.ge(root.get("${item["itemName"]}"),${entityName}.get${item["itemName"]?cap_first}()));
            }
            </#if>
            <#if item.queryExp==7>
                predicates.add(cb.le(root.get("${item["itemName"]}"),${entityName}.get${item["itemName"]?cap_first}()));
            }
            </#if>
            <#if item.queryExp==8>
                predicates.add(cb.between(root.get("${item["itemName"]}"),start${item["itemName"]?cap_first},end${item["itemName"]?cap_first}));
            }
            </#if>
            </#if>
            </#list>
            if(predicates.isEmpty()){
                return cq.getRestriction();
            }else{
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        specification = specification.and(DataScopeUtils.dataScope(request));
        Page<${upEntityName}> pageData =  ${entityName}Service.findPage(specification,pageable);
        return CommonResult.ok(pageData);
    }

    @RequiresPermissions("${moduleName?upper_case}:${entityName?upper_case}:LIST")
    @ResponseBody
    @GetMapping("/list")
    public CommonResult list(${upEntityName} ${entityName} <#list items as item><#if item.queryExp==8>,String start${item["itemName"]?cap_first},String end${item["itemName"]?cap_first}</#if><#if item.formType!=6 && item.queryType ==4 >,String ${item["itemName"]}s</#if></#list>) {
        String msg = queryValidate(${entityName});
        if (StrUtil.isNotEmpty(msg)) {
            return CommonResult.fail(msg);
        }
        Specification specification = (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();//查询条件集
            <#list items as item>
            <#if item.queryType!=0>
                <#if item.queryExp!=8>
                    <#if  item.queryType ==4>
                        <#if item.itemType!='String'>
            if (null != ${item["itemName"]}s) {
                        </#if>
                        <#if item.itemType=='String'>
            if (StrUtil.isNotEmpty(${item["itemName"]}s)) {
                        </#if>
                    <#else>
                        <#if item.itemType!='String'>
            if (null != ${entityName}.get${item["itemName"]?cap_first}()) {
                        </#if>
                        <#if item.itemType=='String'>
            if (StrUtil.isNotEmpty(${entityName}.get${item["itemName"]?cap_first}())) {
                        </#if>
                    </#if>
                 </#if>
            <#if item.queryExp==8>
                <#if item.itemType!='String'>
            if (null != start${item["itemName"]?cap_first}) || null != end${item["itemName"]?cap_first}) {
                </#if>
                <#if item.itemType=='String'>
            if (StrUtil.isNotEmpty(start${item["itemName"]?cap_first}) || StrUtil.isNotEmpty(end${item["itemName"]?cap_first})) {
                </#if>
            </#if>
            <#if item.queryExp==0>
                <#if item.formType!=6 && item.queryType !=4>
                predicates.add(cb.equal(root.get("${item["itemName"]}"),${entityName}.get${item["itemName"]?cap_first}()));
                </#if>
                <#if item.formType==6 && item.queryType !=4>
                predicates.add(cb.like(root.get("${item["itemName"]}"),"%"+${entityName}.get${item["itemName"]?cap_first}()+"%"));
                </#if>
                <#if item.formType!=6 && item.queryType ==4>
                    List<Predicate> orPredicateList = new ArrayList<>();
                    String query[] = ${item["itemName"]}s.split(",");
                    for (int i = 0; i < query.length; i++) {
                        orPredicateList.add(cb.equal(root.get("${item["itemName"]}"),query[i]));
                    }
                    Predicate[] orPredicates = new Predicate[orPredicateList.size()];
                    Predicate orPredicate = cb.or(orPredicateList.toArray(orPredicates));
                    predicates.add(orPredicate);
                </#if>
                <#if item.formType==6 && item.queryType ==4>
                List<Predicate> orPredicateList = new ArrayList<>();
                String query[] =  ${entityName}.get${item["itemName"]?cap_first}().split(",");
                for (int i = 0; i < query.length; i++) {
                    orPredicateList.add(cb.like(root.get("${item["itemName"]}"),"%"+query[i]+"%"));
                }
                Predicate[] orPredicates = new Predicate[orPredicateList.size()];
                Predicate orPredicate = cb.or(orPredicateList.toArray(orPredicates));
                predicates.add(orPredicate);
                </#if>
            }
            </#if>
            <#if item.queryExp==1>
                predicates.add(cb.like(root.get("${item["itemName"]}"), "%" + ${entityName}.get${item["itemName"]?cap_first}() + "%"));
            }
            </#if>
            <#if item.queryExp==2>
                predicates.add(cb.like(root.get("${item["itemName"]}"), ${entityName}.get${item["itemName"]?cap_first}() + "%"));
            }
            </#if>
            <#if item.queryExp==3>
                predicates.add(cb.like(root.get("${item["itemName"]}"), "%" + ${entityName}.get${item["itemName"]?cap_first}() ));
            }
            </#if>
            <#if item.queryExp==4>
                predicates.add(cb.gt(root.get("${item["itemName"]}"),${entityName}.get${item["itemName"]?cap_first}()));
            }
            </#if>
            <#if item.queryExp==5>
                predicates.add(cb.lt(root.get("${item["itemName"]}"),${entityName}.get${item["itemName"]?cap_first}()));
            }
            </#if>
            <#if item.queryExp==6>
                predicates.add(cb.ge(root.get("${item["itemName"]}"),${entityName}.get${item["itemName"]?cap_first}()));
            }
            </#if>
            <#if item.queryExp==7>
                predicates.add(cb.le(root.get("${item["itemName"]}"),${entityName}.get${item["itemName"]?cap_first}()));
            }
            </#if>
            <#if item.queryExp==8>
                predicates.add(cb.between(root.get("${item["itemName"]}"),start${item["itemName"]?cap_first},end${item["itemName"]?cap_first}));
            }
            </#if>
            </#if>
            </#list>
            if(predicates.isEmpty()){
                return cq.getRestriction();
            }else{
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        specification = specification.and(DataScopeUtils.dataScope(request));
        List<${upEntityName}> pageData =  ${entityName}Service.findList(specification);
        return CommonResult.ok(pageData);
    }

    @RequiresPermissions(value = {"${moduleName?upper_case}:${entityName?upper_case}:ADD", "${moduleName?upper_case}:${entityName?upper_case}:EDIT"}, logical = Logical.OR)
    @ResponseBody
    @PostMapping("/save")
    public CommonResult save(${upEntityName} ${entityName}) {
        String msg = saveValidate(${entityName});
        if (StrUtil.isNotEmpty(msg)) {
            return CommonResult.fail(msg);
        }
        if(${entityName}.isNewRecord()){
            ${entityName}Service.save(${entityName});
        }else{
            ${upEntityName} find${upEntityName} = ${entityName}Service.findById(${entityName}.getId());
            BeanUtil.copyProperties(${entityName},find${upEntityName}, CopyOptions.create().ignoreNullValue());
            ${entityName}Service.save(find${upEntityName});
        }
        return CommonResult.ok();
    }

    @RequiresPermissions("${moduleName?upper_case}:${entityName?upper_case}:DEL")
    @ResponseBody
    @PostMapping("/delete")
    public CommonResult delete(@RequestParam(value = "id[]")  String[] id) {
        ${entityName}Service.deleteAllById(Arrays.asList(id));
        return CommonResult.ok();
    }

    <#if excel==true>
    @RequiresPermissions(value = {"${moduleName?upper_case}:${entityName?upper_case}:ADD", "${moduleName?upper_case}:${entityName?upper_case}:EDIT"}, logical = Logical.OR)
    @ResponseBody
    @RequestMapping("/excel/export")
    public void excelExport(@RequestParam(value = "id[]",required = false)  String id) {
        List<${upEntityName}> list;
        if(id !=null){
            String[] idarr = id.split(",");
            list = ${entityName}Service.findAllById(Arrays.asList(idarr));
        }else{
            list = ${entityName}Service.findAll();
        }
        EasyExcel.write(getExcelStream("${entityDesc}"),${upEntityName}Excel.class)
            .sheet()
           .doWrite(${upEntityName}Excel.transform(list));
        }

    @RequiresPermissions(value = {"${moduleName?upper_case}:${entityName?upper_case}:ADD", "${moduleName?upper_case}:${entityName?upper_case}:EDIT"}, logical = Logical.OR)
    @ResponseBody
    @RequestMapping("/excel/template")
    public void excelTemplate() {
         EasyExcel.write(getExcelStream("${entityDesc}"),${upEntityName}Excel.class)
         .sheet()
         .doWrite(new ArrayList());
    }

    @RequiresPermissions("${moduleName?upper_case}:${entityName?upper_case}:ADD")
    @ResponseBody
    @PostMapping("/excel/import")
    @SneakyThrows
    public void excelImport(@RequestParam(name ="file" ) MultipartFile file) {
        EasyExcel.read(file.getInputStream(),
        ${upEntityName}Excel.class,
        new ${upEntityName}ExcelListener()).doReadAll();
    }
    </#if>
}

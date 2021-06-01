package pers.gon.web.upms;

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
import pers.gon.application.utils.DataScopeUtils;
import pers.gon.domain.upms.entity.UpmsAdmin;
import pers.gon.domain.upms.entity.UpmsDept;
import pers.gon.domain.upms.service.IUpmsDeptService;
import pers.gon.infrastructure.common.controller.BaseController;
import pers.gon.infrastructure.common.entity.CommonResult;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Slf4j
@Controller
@RequestMapping("${global.adminpath}/upms/dept")
public class UpmsDeptController extends BaseController {

    @Autowired
    IUpmsDeptService upmsDeptService;

    @RequiresPermissions("UPMS:DEPT:LIST")
    @RequestMapping("")
    public String index(Model model){
        return "modules/upms/dept/dept.html";
    }

    @RequiresPermissions(value = {"UPMS:DEPT:VIEW", "UPMS:DEPT:ADD", "UPMS:DEPT:EDIT"}, logical = Logical.OR)
    @RequestMapping("/form/{mode}")
    public String form(String id, Model model,@PathVariable String mode,String parentId) {
        if("view".equals(mode)||"edit".equals(mode)){
            model.addAttribute("upmsDept",upmsDeptService.findById(id));
        }
        if(StrUtil.isNotEmpty(parentId) && "add".equals(mode)){
            UpmsDept upmsDept =new UpmsDept();
            upmsDept.setParent(upmsDeptService.findById(parentId));
            model.addAttribute("upmsDept",upmsDept);
        }
        model.addAttribute("mode",mode);
        return "modules/upms/dept/deptForm.html";
    }

    @RequiresPermissions("UPMS:DEPT:LIST")
    @ResponseBody
    @GetMapping("/page")
    public CommonResult page(UpmsDept upmsDept, Pageable pageable) {
        String msg = queryValidate(upmsDept);
        if (StrUtil.isNotEmpty(msg)) {
            return CommonResult.fail(msg);
        }
        Specification specification = (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();//查询条件集
            if (StrUtil.isNotEmpty(upmsDept.getCode())) {
                predicates.add(cb.like(root.get("code"), "%" + upmsDept.getCode() + "%"));
            }
            if (StrUtil.isNotEmpty(upmsDept.getName())) {
                predicates.add(cb.like(root.get("name"), "%" + upmsDept.getName() + "%"));
            }
            if(predicates.isEmpty()){
                return cq.getRestriction();
            }else{
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        specification = specification.and(DataScopeUtils.dataScope(request));

        Page<UpmsDept> pageData = upmsDeptService.findPage(specification, pageable);
        return CommonResult.ok(pageData);
    }
    @RequiresPermissions("UPMS:DEPT:LIST")
    @ResponseBody
    @GetMapping("/list")
    public CommonResult list(UpmsDept upmsDept,String search){
        Specification specification = (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();//查询条件集
            if (StrUtil.isNotEmpty(upmsDept.getCode())) {
                predicates.add(cb.like(root.get("code"), "%" + upmsDept.getCode() + "%"));
            }
            if (StrUtil.isNotEmpty(upmsDept.getName())) {
                predicates.add(cb.like(root.get("name"), "%" + upmsDept.getName() + "%"));
            }
            if (StrUtil.isNotEmpty(search)) {
                predicates.add(cb.like(root.get("name"), "%" + search+ "%"));
            }
            if(predicates.isEmpty()){
                return cq.getRestriction();
            }else{
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        specification = specification.and(DataScopeUtils.dataScope(request));

        return CommonResult.ok(upmsDeptService.findList(specification));
    }

    @RequiresPermissions(value = {"UPMS:DEPT:ADD", "UPMS:DEPT:EDIT"}, logical = Logical.OR)
    @ResponseBody
    @PostMapping("/save")
    public CommonResult save(UpmsDept upmsDept) {
        String msg = saveValidate(upmsDept);
        if (StrUtil.isNotEmpty(msg)) {
            return CommonResult.fail(msg);
        }
        if(upmsDept.isNewRecord()){
            upmsDeptService.save(upmsDept);
        }else{
            UpmsDept findDept = upmsDeptService.findById(upmsDept.getId());
            BeanUtil.copyProperties(upmsDept,findDept, CopyOptions.create().ignoreNullValue());
            upmsDeptService.save(findDept);
        }
        return CommonResult.ok();
    }

    @RequiresPermissions("UPMS:DEPT:DEL")
    @ResponseBody
    @PostMapping("/delete")
    public CommonResult delete(@RequestParam(value = "id[]")  String[] id) {
        upmsDeptService.deleteAllById(Arrays.asList(id));
        return CommonResult.ok();
    }

}

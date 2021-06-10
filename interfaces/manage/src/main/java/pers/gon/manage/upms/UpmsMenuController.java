package pers.gon.manage.upms;

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
import pers.gon.domain.upms.entity.UpmsDept;
import pers.gon.domain.upms.entity.UpmsMenu;
import pers.gon.domain.upms.service.IUpmsMenuService;
import pers.gon.infrastructure.common.controller.BaseController;
import pers.gon.infrastructure.common.entity.CommonResult;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Slf4j
@Controller
@RequestMapping("${global.adminpath}/upms/menu")
public class UpmsMenuController extends BaseController {

    @Autowired
    IUpmsMenuService upmsMenuService;


    @RequiresPermissions("UPMS:MENU:LIST")
    @RequestMapping("")
    public String index(Model model) {
        return "modules/upms/menu/menu.html";
    }

    @RequiresPermissions(value = {"UPMS:MENU:VIEW", "UPMS:MENU:ADD", "UPMS:MENU:EDIT"}, logical = Logical.OR)
    @RequestMapping("/form/{mode}")
    public String form(String id, Model model, @PathVariable String mode,String parentId) {
        if ("view".equals(mode) || "edit".equals(mode)) {
            model.addAttribute("upmsMenu", upmsMenuService.findById(id));
        }
        if(StrUtil.isNotEmpty(parentId) && "add".equals(mode)){
            UpmsMenu upmsMenu =new UpmsMenu();
            upmsMenu.setParent(upmsMenuService.findById(parentId));
            model.addAttribute("upmsMenu",upmsMenu);
        }
        model.addAttribute("mode", mode);
        return "modules/upms/menu/menuForm.html";
    }

    @RequiresPermissions("UPMS:MENU:LIST")
    @ResponseBody
    @GetMapping("/page")
    public CommonResult page(UpmsMenu upmsMenu, Pageable pageable) {
        String msg = queryValidate(upmsMenu);
        if (StrUtil.isNotEmpty(msg)) {
            return CommonResult.fail(msg);
        }
        Specification specification = (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();//查询条件集
            if (StrUtil.isNotEmpty(upmsMenu.getCode())) {
                predicates.add(cb.like(root.get("code"), "%" + upmsMenu.getCode() + "%"));
            }
            if (StrUtil.isNotEmpty(upmsMenu.getName())) {
                predicates.add(cb.like(root.get("name"), "%" + upmsMenu.getName() + "%"));
            }
            if(predicates.isEmpty()){
                return cq.getRestriction();
            }else{
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        specification = specification.and(DataScopeUtils.dataScope(request));

        Page<UpmsDept> pageData = upmsMenuService.findPage(specification, pageable);
        return CommonResult.ok(pageData);
    }

    @RequiresPermissions("UPMS:MENU:LIST")
    @ResponseBody
    @GetMapping("/list")
    public CommonResult list(UpmsMenu upmsMenu,String search) {
        Specification specification = (root, cq, cb) -> {
            if (StrUtil.isNotEmpty(upmsMenu.getCode())) {
                cq.where(cb.like(root.get("code"), "%" + upmsMenu.getCode() + "%"));
            }
            if (StrUtil.isNotEmpty(upmsMenu.getName())) {
                cq.where(cb.like(root.get("name"), "%" + upmsMenu.getName() + "%"));
            }
            if (StrUtil.isNotEmpty(search)) {
                cq.where(cb.like(root.get("name"), "%" + search+ "%"));
            }
            return cq.getRestriction();
        };
        specification = specification.and(DataScopeUtils.dataScope(request));
        return CommonResult.ok(upmsMenuService.findList(specification));
    }


    @RequiresPermissions(value = {"UPMS:MENU:ADD", "UPMS:MENU:EDIT"}, logical = Logical.OR)
    @ResponseBody
    @PostMapping("/save")
    public CommonResult save(UpmsMenu upmsMenu) {
        String msg = saveValidate(upmsMenu);
        if (StrUtil.isNotEmpty(msg)) {
            return CommonResult.fail(msg);
        }
        if (upmsMenu.isNewRecord()) {
            upmsMenuService.save(upmsMenu);
        } else {
            UpmsMenu findMenu = upmsMenuService.findById(upmsMenu.getId());
            BeanUtil.copyProperties(upmsMenu, findMenu, CopyOptions.create().ignoreNullValue());
            upmsMenuService.save(findMenu);
        }
        return CommonResult.ok();
    }

    @RequiresPermissions("UPMS:MENU:DEL")
    @ResponseBody
    @PostMapping("/delete")
    public CommonResult delete(@RequestParam(value = "id[]") String[] id) {
        upmsMenuService.deleteAllById(Arrays.asList(id));
        return CommonResult.ok();
    }


}

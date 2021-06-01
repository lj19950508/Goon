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
import pers.gon.application.utils.CurrentAdminUtils;
import pers.gon.application.utils.DataScopeUtils;
import pers.gon.application.utils.DictUtils;
import pers.gon.domain.upms.entity.UpmsDatascope;
import pers.gon.domain.upms.entity.UpmsRole;
import pers.gon.domain.upms.service.IUpmsDatascopeService;
import pers.gon.domain.upms.service.IUpmsRoleService;
import pers.gon.infrastructure.common.controller.BaseController;
import pers.gon.infrastructure.common.entity.CommonResult;

import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;


@Slf4j
@Controller
@RequestMapping("${global.adminpath}/upms/role")
public class UpmsRoleController extends BaseController {

    @Autowired
    IUpmsRoleService roleService;

    @Autowired
    IUpmsDatascopeService datascopeService;

    @RequiresPermissions("UPMS:ROLE:LIST")
    @RequestMapping("")
    public String index(Model model){
        return "modules/upms/role/role.html";
    }

    @RequiresPermissions(value = {"UPMS:ROLE:VIEW", "UPMS:ROLE:ADD", "UPMS:ROLE:EDIT"}, logical = Logical.OR)
    @RequestMapping("/form/{mode}")
    public String form(String id, Model model,@PathVariable String mode) {
        if("view".equals(mode)||"edit".equals(mode)){
            UpmsRole upmsRole = roleService.findById(id);
            model.addAttribute("upmsRole", upmsRole);
            Set<String> selectMenuList =  upmsRole.getMenus().stream().map(item->{return item.getId();}).collect(Collectors.toSet());
            model.addAttribute("selectMenuList", selectMenuList);
        }
        model.addAttribute("mode",mode);
        return "modules/upms/role/roleForm.html";
    }

    @RequiresPermissions("UPMS:ROLE:LIST")
    @ResponseBody
    @GetMapping("/page")
    public CommonResult page(UpmsRole upmsRole, Pageable pageable) {
        String msg = queryValidate(upmsRole);
        if (StrUtil.isNotEmpty(msg)) {
            return CommonResult.fail(msg);
        }
        Specification specification = (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();//查询条件集
            if (StrUtil.isNotEmpty(upmsRole.getCode())) {
                predicates.add(cb.like(root.get("code"), "%" + upmsRole.getCode() + "%"));
            }
            if (StrUtil.isNotEmpty(upmsRole.getName())) {
                predicates.add(cb.like(root.get("name"), "%" + upmsRole.getName() + "%"));
            }
            if(predicates.isEmpty()){
                return cq.getRestriction();
            }else{
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        specification = specification.and(DataScopeUtils.dataScope(request));
        Page<UpmsRole> pageData =  roleService.findPage(specification,pageable);
        return CommonResult.ok(pageData);
    }
    @RequiresPermissions(value = {"UPMS:ROLE:ADD", "UPMS:ROLE:EDIT"}, logical = Logical.OR)
    @ResponseBody
    @PostMapping("/save")
    public CommonResult save(UpmsRole upmsRole) {
        String msg = saveValidate(upmsRole);
        if (StrUtil.isNotEmpty(msg)) {
            return CommonResult.fail(msg);
        }
        if(upmsRole.isNewRecord()){
            roleService.save(upmsRole);
        }else{
            UpmsRole findRole = roleService.findById(upmsRole.getId());
            //如果未选中角色则设置为空 而不是不变
            if(upmsRole.getMenus()==null){
                upmsRole.setMenus(new HashSet<>());
            }
            BeanUtil.copyProperties(upmsRole,findRole, CopyOptions.create().ignoreNullValue());
            roleService.save(upmsRole);
        }
        return CommonResult.ok();
    }

    @RequiresPermissions("UPMS:ROLE:DEL")
    @ResponseBody
    @PostMapping("/delete")
    public CommonResult delete(@RequestParam(value = "id[]")  String[] id) {
        roleService.deleteAllById(Arrays.asList(id));
        return CommonResult.ok();
    }

    //当前用户菜单的datascope名称
    @ResponseBody
    @GetMapping("/datascopeName")
    public CommonResult datascopeName(String roleId,String menuCode) {
        UpmsRole upmsRole = roleService.findById(roleId);
        return CommonResult.ok(
                DictUtils.getName("upms_datascope_type",DataScopeUtils.getDatascopeValue(upmsRole,menuCode))
        );
    }

    @ResponseBody
    @PostMapping("/setDatascopeValue")
    public CommonResult setDatascopeValue(String roleId,String menuCode,Integer dsValue) {
        UpmsRole upmsRole = roleService.findById(roleId);
        datascopeService.setDatascopeValue(upmsRole,menuCode,dsValue);
        return CommonResult.ok("修改成功,用户重新登录后生效。",null);
    }
}

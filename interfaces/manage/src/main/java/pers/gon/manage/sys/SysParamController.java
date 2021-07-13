package pers.gon.manage.sys;

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
import pers.gon.domain.sys.entity.SysParam;
import pers.gon.domain.sys.service.ISysParamService;
import pers.gon.infrastructure.common.controller.BaseController;
import pers.gon.infrastructure.common.entity.CommonResult;
import pers.gon.manage.utils.DataScopeUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Slf4j
@Controller
@RequestMapping("${global.adminpath}/sys/param")
public class SysParamController extends BaseController {
    @Autowired
    ISysParamService sysParamService;

    @RequiresPermissions("SYS:PARAM:LIST")
    @RequestMapping("")
    public String index(Model model) {
        return "modules/sys/param/param.html";
    }

    @RequiresPermissions(value = {"SYS:PARAM:VIEW", "SYS:PARAM:ADD", "SYS:PARAM:EDIT"}, logical = Logical.OR)
    @RequestMapping("/form/{mode}")
    public String form(String id, Model model,@PathVariable String mode) {
        if("view".equals(mode)||"edit".equals(mode)){
            SysParam sysParam = sysParamService.findById(id);
            model.addAttribute("sysParam",sysParam);
        }
        model.addAttribute("mode",mode);
        return "modules/sys/param/paramForm.html";
    }

    @RequiresPermissions("SYS:PARAM:LIST")
    @ResponseBody
    @GetMapping("/page")
    public CommonResult page(SysParam sysParam, Pageable pageable) {
        String msg = queryValidate(sysParam);
        if (StrUtil.isNotEmpty(msg)) {
            return CommonResult.fail(msg);
        }
        Specification specification = (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();//查询条件集
            if (StrUtil.isNotEmpty(sysParam.getCode())) {
                predicates.add(cb.like(root.get("code"), "%" + sysParam.getCode() + "%"));
            }
            if (StrUtil.isNotEmpty(sysParam.getName())) {
                predicates.add(cb.like(root.get("name"), "%" + sysParam.getName() + "%"));
            }
            if(predicates.isEmpty()){
                return cq.getRestriction();
            }else{
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        specification = specification.and(DataScopeUtils.dataScope(request));
        Page<SysParam> pageData =  sysParamService.findPage(specification,pageable);
        return CommonResult.ok(pageData);
    }

    @RequiresPermissions(value = {"SYS:PARAM:ADD", "SYS:PARAM:EDIT"}, logical = Logical.OR)
    @ResponseBody
    @PostMapping("/save")
    public CommonResult save(SysParam sysParam) {
        String msg = saveValidate(sysParam);
        if (StrUtil.isNotEmpty(msg)) {
            return CommonResult.fail(msg);
        }
        if(sysParam.isNewRecord()){
            sysParamService.save(sysParam);
        }else{
            SysParam findParam = sysParamService.findById(sysParam.getId());
            BeanUtil.copyProperties(sysParam,findParam, CopyOptions.create().ignoreNullValue());
            sysParamService.save(findParam);
        }
        return CommonResult.ok();
    }
    @RequiresPermissions("SYS:PARAM:DEL")
    @ResponseBody
    @PostMapping("/delete")
    public CommonResult delete(@RequestParam(value = "id[]")  String[] id) {
        sysParamService.deleteAllById(Arrays.asList(id));
        return CommonResult.ok();
    }


}

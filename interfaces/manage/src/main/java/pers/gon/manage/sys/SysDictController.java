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
import pers.gon.domain.sys.entity.SysDict;
import pers.gon.domain.sys.service.SysDictService;
import pers.gon.domain.sys.vo.SysDictItem;
import pers.gon.infrastructure.common.controller.BaseController;
import pers.gon.infrastructure.common.entity.CommonResult;
import pers.gon.manage.utils.DataScopeUtils;

import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Controller
@RequestMapping("${global.adminpath}/sys/dict")
public class SysDictController extends BaseController {

   @Autowired
   SysDictService sysDictService;

    @RequiresPermissions("SYS:DICT:LIST")
    @RequestMapping("")
    public String index(Model model) {
        return "modules/sys/dict/dict.html";
    }

    @RequiresPermissions(value = {"SYS:DICT:VIEW", "SYS:DICT:ADD", "SYS:DICT:EDIT"}, logical = Logical.OR)
    @RequestMapping("/form/{mode}")
    public String form(String id, Model model,@PathVariable String mode) {
        if("view".equals(mode)||"edit".equals(mode)){
            SysDict sysDict = sysDictService.findById(id);
            model.addAttribute("sysDict",sysDict);
        }
        model.addAttribute("mode",mode);
        return "modules/sys/dict/dictForm.html";
    }

    @RequiresPermissions("SYS:DICT:LIST")
    @ResponseBody
    @GetMapping("/page")
    public CommonResult page(SysDict sysDict, Pageable pageable) {
        String msg = queryValidate(sysDict);
        if (StrUtil.isNotEmpty(msg)) {
            return CommonResult.fail(msg);
        }
        Specification specification = (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();//查询条件集
            if (StrUtil.isNotEmpty(sysDict.getCode())) {
                predicates.add(cb.like(root.get("code"), "%" + sysDict.getCode() + "%"));
            }
            if (StrUtil.isNotEmpty(sysDict.getName())) {
                predicates.add(cb.like(root.get("name"), "%" + sysDict.getName() + "%"));
            }
            if(predicates.isEmpty()){
                return cq.getRestriction();
            }else{
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        specification = specification.and(DataScopeUtils.dataScope(request));

        Page<SysDict> pageData =  sysDictService.findPage(specification,pageable);
        return CommonResult.ok(pageData);
    }

    @RequiresPermissions(value = {"SYS:DICT:VIEW", "SYS:DICT:ADD", "SYS:DICT:EDIT"}, logical = Logical.OR)
    @ResponseBody
    @GetMapping("/info")
    public CommonResult info(String id) {
        SysDict sysDict = sysDictService.findById(id);
        List<SysDictItem> sysDictItems =  sysDict.getDictItems().stream().sorted(Comparator.comparing(SysDictItem::getSort)).collect(Collectors.toList());
        sysDict.setDictItems(sysDictItems);
        return CommonResult.ok(sysDict);
    }

    @RequiresPermissions(value = {"SYS:DICT:ADD", "SYS:DICT:EDIT"}, logical = Logical.OR)
    @ResponseBody
    @PostMapping("/save")
    public CommonResult save(SysDict sysDict) {
        String msg = saveValidate(sysDict);
        if (StrUtil.isNotEmpty(msg)) {
            return CommonResult.fail(msg);
        }
        if(sysDict.isNewRecord()){
            sysDictService.save(sysDict);
        }else{
            SysDict findDict = sysDictService.findById(sysDict.getId());
            BeanUtil.copyProperties(sysDict,findDict, CopyOptions.create().ignoreNullValue());
            sysDictService.save(findDict);
        }
        return CommonResult.ok();
    }

    @RequiresPermissions(value = {"SYS:DICT:ADD", "SYS:DICT:EDIT"}, logical = Logical.OR)
    @ResponseBody
    @PostMapping("/saveDictItem")
    public CommonResult saveDictItem(SysDict sysDict) {
        SysDict findDict = sysDictService.findById(sysDict.getId());
        BeanUtil.copyProperties(sysDict,findDict, CopyOptions.create().ignoreNullValue());
        sysDictService.save(findDict);
        return CommonResult.ok();
    }
    @RequiresPermissions("SYS:DICT:DEL")
    @ResponseBody
    @PostMapping("/delete")
    public CommonResult delete(@RequestParam(value = "id[]")  String[] id) {
        sysDictService.deleteAllById(Arrays.asList(id));
        return CommonResult.ok();
    }


}

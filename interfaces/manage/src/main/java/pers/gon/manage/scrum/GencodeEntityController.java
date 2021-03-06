package pers.gon.manage.scrum;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pers.gon.domain.scrum.dto.GencodeConfig;
import pers.gon.domain.scrum.entity.GencodeEntity;
import pers.gon.domain.scrum.service.IGencodeEntityService;
import pers.gon.domain.sys.entity.SysDict;
import pers.gon.domain.sys.service.ISysDictService;
import pers.gon.domain.upms.service.IUpmsMenuService;
import pers.gon.infrastructure.common.controller.BaseController;
import pers.gon.infrastructure.common.entity.CommonResult;
import pers.gon.manage.utils.DataScopeUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("${global.adminpath}/scrum/gencode/entity")
public class GencodeEntityController extends BaseController {

    @Autowired
    IGencodeEntityService gencodeEntityService;

    @Autowired
    IUpmsMenuService upmsMenuService;

    @Autowired
    ISysDictService sysDictService;

    @RequiresPermissions("SCRUM:GENCODE:LIST")
    @RequestMapping("")
    public String index(Model model) {
        return "modules/scrum/gencode/entity/entity.html";
    }

    @RequiresPermissions(value = {"SCRUM:GENCODE:VIEW", "SCRUM:GENCODE:ADD", "SCRUM:GENCODE:EDIT"}, logical = Logical.OR)
    @RequestMapping("/form/{mode}")
    public String form(String id, Model model,@PathVariable String mode) {
        if("view".equals(mode)||"edit".equals(mode)){
            GencodeEntity gencodeEntity = gencodeEntityService.findById(id);
            model.addAttribute("gencodeEntity",gencodeEntity);
        }
        List<SysDict> sysDicts = sysDictService.findAll();

        model.addAttribute("mode",mode);
        model.addAttribute("dictList",sysDicts);
        GencodeEntity gencodeEntity = new GencodeEntity();
        gencodeEntity.setTemplateType(1);
        model.addAttribute("treeList",gencodeEntityService.findList(gencodeEntity));
        return "modules/scrum/gencode/entity/entityForm.html";
    }

    @RequiresPermissions("SCRUM:GENCODE:LIST")
    @ResponseBody
    @GetMapping("/page")
    public CommonResult page(GencodeEntity gencodeEntity, Pageable pageable) {
        String msg = queryValidate(gencodeEntity);
        if (StrUtil.isNotEmpty(msg)) {
            return CommonResult.fail(msg);
        }
        Specification specification = (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();//???????????????
            if (StrUtil.isNotEmpty(gencodeEntity.getEntityName())) {
                predicates.add(cb.like(root.get("entityName"), "%" + gencodeEntity.getEntityName() + "%"));
            }
            if (StrUtil.isNotEmpty(gencodeEntity.getEntityDesc())) {
                predicates.add(cb.like(root.get("entityDesc"), "%" + gencodeEntity.getEntityDesc() + "%"));
            }
            if(predicates.isEmpty()){
                return cq.getRestriction();
            }else{
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        specification = specification.and(DataScopeUtils.dataScope(request));
        Page<GencodeEntity> pageData =  gencodeEntityService.findPage(specification,pageable);
        return CommonResult.ok(pageData);
    }


    @RequiresPermissions(value = {"SCRUM:GENCODE:ADD", "SCRUM:GENCODE:EDIT"}, logical = Logical.OR)
    @ResponseBody
    @PostMapping("/save")
    public CommonResult save(GencodeEntity gencodeEntity) {
        String msg = saveValidate(gencodeEntity);
        if (StrUtil.isNotEmpty(msg)) {
            return CommonResult.fail(msg);
        }
        if(gencodeEntity.isNewRecord()){
            gencodeEntityService.save(gencodeEntity);
        }else{
            GencodeEntity findEntity = gencodeEntityService.findById(gencodeEntity.getId());
            BeanUtil.copyProperties(gencodeEntity,findEntity, CopyOptions.create().ignoreNullValue());
            gencodeEntityService.save(findEntity);
        }
        return CommonResult.ok();
    }

    @RequiresPermissions(value = {"SCRUM:GENCODE:ADD", "SCRUM:GENCODE:EDIT"}, logical = Logical.OR)
    @ResponseBody
    @PostMapping("/generate")
    public CommonResult generate(String id, GencodeConfig config) {

        GencodeEntity gencodeEntity =gencodeEntityService.findById(id);

        gencodeEntityService.gencode(gencodeEntity,config);
        if(StringUtils.isNotEmpty(config.getMenuId())){
            gencodeEntityService.genmenu(gencodeEntity,config.getMenuId());
        }

        //???????????????????????????

        return CommonResult.ok();
    }

    @RequiresPermissions(value = {"SCRUM:GENCODE:ADD", "SCRUM:GENCODE:EDIT"}, logical = Logical.OR)
    @ResponseBody
    @GetMapping("/remove")
    public CommonResult remove(String id,boolean removeMenu,boolean removeRecord) {
        //todo ?????????????????????
        return CommonResult.ok();
    }


    @RequiresPermissions("SCRUM:GENCODE:DEL")
    @ResponseBody
    @PostMapping("/delete")
    public CommonResult delete(@RequestParam(value = "id[]")  String[] id) {
        gencodeEntityService.deleteAllById(Arrays.asList(id));
        return CommonResult.ok();
    }


    @RequiresPermissions(value = { "SCRUM:GENCODE:ADD", "SCRUM:GENCODE:EDIT"}, logical = Logical.OR)
    @RequestMapping("/genConfigForm")
    public String genConfigForm(String id, Model model) {
        GencodeEntity gencodeEntity = gencodeEntityService.findById(id);
        model.addAttribute("gencodeEntity",gencodeEntity);

        return "modules/scrum/gencode/entity/genConfig.html";
    }
}

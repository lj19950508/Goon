package pers.gon.web.scrum;

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
import pers.gon.domain.scrum.entity.GencodeEntity;
import pers.gon.domain.scrum.service.IGencodeEntityService;
import pers.gon.domain.scrum.vo.GencodeEntityItem;
import pers.gon.domain.sys.entity.SysDict;
import pers.gon.domain.upms.service.IUpmsMenuService;
import pers.gon.infrastructure.common.controller.BaseController;
import pers.gon.infrastructure.common.entity.CommonResult;

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

    @RequiresPermissions("SCRUM:GENCODE")
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
        model.addAttribute("mode",mode);
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
            List<Predicate> predicates = new ArrayList<>();//查询条件集
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
        Page<SysDict> pageData =  gencodeEntityService.findPage(specification,pageable);
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
    @GetMapping("/generate")
    public CommonResult generate(String id,boolean genMenu) {
        //1.生成代码到指定模块下 String gencodeEntityId
        //2.生成菜单
        //3.默认为admin生成菜单
        // https://blog.csdn.net/weixin_43424932/article/details/104253977
        //生成并在某个菜单下

        GencodeEntity gencodeEntity = new GencodeEntity();
        gencodeEntity.setDelFlag(false);
        gencodeEntity.setEntityDesc("测试模块");
        gencodeEntity.setModuleName("upms");
//        gencodeEntity.setParentMenu("1304090865509208064");
        gencodeEntity.setEntityName("test");
        gencodeEntity.setIsLogicDelete(true);
        gencodeEntity.setTemplateType(0);
        List<GencodeEntityItem> list = new ArrayList<>();
        GencodeEntityItem item1 = new GencodeEntityItem();
        item1.setFormType(7);
        item1.setItemDesc("测试字段name");
        item1.setItemName("testname");
        item1.setItemType("String");
        item1.setSqlType("varchar(255)");
        item1.setListLength(100);
        item1.setListShow(true);
        item1.setSort(true);
        item1.setMust(true);
        item1.setUnique(true);
        item1.setQueryType(8);
        item1.setDictCode("common_gender");
        item1.setQueryExp(0);
        list.add(item1);


        GencodeEntityItem item2 = new GencodeEntityItem();
        item2.setFormType(4);
        item2.setItemDesc("测试字段one");
        item2.setItemName("testone");
        item2.setItemType("Integer");
        item2.setSqlType("char(2)");
        item2.setListLength(50);
        item2.setListShow(true);
        item2.setSort(true);
        item2.setMust(true);
        item2.setUnique(false);
        item2.setQueryType(2);
        item2.setDictCode("common_gender");
        item2.setQueryExp(0);
        list.add(item2);

        GencodeEntityItem item3 = new GencodeEntityItem();
        item3.setFormType(5);
        item3.setItemDesc("测试字段two");
        item3.setItemName("testtwo");
        item3.setItemType("String");
        item3.setSqlType("varchar(255)");
        item3.setListLength(50);
        item3.setListShow(true);
        item3.setSort(true);
        item3.setMust(true);
        item3.setUnique(false);
        item3.setQueryType(4);
        item3.setDictCode("common_gender");
        item3.setQueryExp(0);
        list.add(item3);
        gencodeEntity.setItems(list);
        gencodeEntityService.gencode(gencodeEntity);
//        gencodeEntityService.genmenu(gencodeEntity);

        //生成菜单的增删改查

        return CommonResult.ok();
    }

    @RequiresPermissions(value = {"SCRUM:GENCODE:ADD", "SCRUM:GENCODE:EDIT"}, logical = Logical.OR)
    @ResponseBody
    @GetMapping("/remove")
    public CommonResult remove(String id,boolean removeMenu,boolean removeRecord) {
        //todo 删除代码与菜单
        return CommonResult.ok();
    }


    @RequiresPermissions("SCRUM:GENCODE:DEL")
    @ResponseBody
    @PostMapping("/delete")
    public CommonResult delete(@RequestParam(value = "id[]")  String[] id) {
        gencodeEntityService.deleteAllById(Arrays.asList(id));
        return CommonResult.ok();
    }
}

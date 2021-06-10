package pers.gon.manage.upms;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import lombok.SneakyThrows;
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
import org.springframework.web.multipart.MultipartFile;
import pers.gon.application.utils.CurrentAdminUtils;
import pers.gon.application.utils.DataScopeUtils;
import pers.gon.domain.upms.entity.UpmsAdmin;
import pers.gon.domain.upms.service.IUpmsAdminService;
import pers.gon.domain.upms.service.IUpmsDeptService;
import pers.gon.domain.upms.service.IUpmsMenuService;
import pers.gon.domain.upms.service.IUpmsRoleService;
import pers.gon.infrastructure.common.controller.BaseController;
import pers.gon.infrastructure.common.entity.CommonResult;
import pers.gon.manage.upms.excel.UpmsAdminExcel;
import pers.gon.manage.upms.excel.UpmsAdminExcelListener;

import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Controller
@RequestMapping("${global.adminpath}/upms/admin")
public class UpmsAdminController extends BaseController {

    @Autowired
    IUpmsAdminService upmsAdminService;

    @Autowired
    IUpmsDeptService upmsDeptService;

    @Autowired
    IUpmsRoleService upmsRoleService;

    @Autowired
    IUpmsMenuService upmsMenuService;

    @RequiresPermissions("UPMS:ADMIN:LIST")
    @RequestMapping("")
    public String index(Model model) {
        return "modules/upms/admin/admin.html";
    }

    @RequiresPermissions(value = {"UPMS:ADMIN:VIEW", "UPMS:ADMIN:ADD", "UPMS:ADMIN:EDIT"}, logical = Logical.OR)
    @RequestMapping("/form/{mode}")
    public String form(String id, Model model,@PathVariable String mode) {
        if("view".equals(mode)||"edit".equals(mode)){
            UpmsAdmin upmsAdmin = upmsAdminService.findById(id);
            Set<String> selectRoleList =  upmsAdmin.getRoles().stream().map(item-> item.getId()).collect(Collectors.toSet());
            model.addAttribute("upmsAdmin", upmsAdmin);
            model.addAttribute("selectRoleList", selectRoleList);
        }
        model.addAttribute("mode",mode);
        model.addAttribute("roleList", upmsRoleService.findAll());
        return "modules/upms/admin/adminForm.html";
    }

    @RequiresPermissions("UPMS:ADMIN:LIST")
    @ResponseBody
    @GetMapping("/page")
    public CommonResult page(UpmsAdmin upmsAdmin, Pageable pageable) {
        String msg = queryValidate(upmsAdmin);
        if (StrUtil.isNotEmpty(msg)) {
            return CommonResult.fail(msg);
        }
        Specification specification = (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();//查询条件集
            if (StrUtil.isNotEmpty(upmsAdmin.getAccount())) {
                predicates.add(cb.like(root.get("account"), "%" + upmsAdmin.getAccount() + "%"));
            }
            if (StrUtil.isNotEmpty(upmsAdmin.getNickname())) {
                predicates.add(cb.like(root.get("nickname"), "%" + upmsAdmin.getNickname() + "%"));
            }
            if (upmsAdmin.getDept()!=null && StrUtil.isNotEmpty(upmsAdmin.getDept().getCode())) {
                predicates.add(cb.like(root.get("dept").get("code"), "%" + upmsAdmin.getDept().getCode() + "%"));
            }
            if(predicates.isEmpty()){
                return cq.getRestriction();
            }else{
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        specification = specification.and(DataScopeUtils.dataScope(request));
        Page<UpmsAdmin> pageData = upmsAdminService.findPage(specification, pageable);
        return CommonResult.ok(pageData);
    }

    @RequiresPermissions(value = {"UPMS:ADMIN:ADD", "UPMS:ADMIN:EDIT"}, logical = Logical.OR)
    @ResponseBody
    @PostMapping("/save")
    public CommonResult save(UpmsAdmin upmsAdmin) {
        String msg = saveValidate(upmsAdmin);
        if (StrUtil.isNotEmpty(msg)) {
            return CommonResult.fail(msg);
        }
        if(upmsAdmin.isNewRecord()){
            upmsAdmin = upmsAdminService.passwordEncode(upmsAdmin);
            upmsAdminService.save(upmsAdmin);
        }else{
            UpmsAdmin findAdmin = upmsAdminService.findById(upmsAdmin.getId());

            if(findAdmin.getId().equals(CurrentAdminUtils.id())){
                return CommonResult.fail("不可修改自身信息");
            }

            //如果未选中角色则设置为空 而不是不变
            if(upmsAdmin.getRoles()==null){
                upmsAdmin.setRoles(new HashSet<>());
            }
            BeanUtil.copyProperties(upmsAdmin,findAdmin, CopyOptions.create().ignoreNullValue());
            upmsAdminService.save(findAdmin);
        }
        return CommonResult.ok();
    }

    @RequiresPermissions("UPMS:ADMIN:DEL")
    @ResponseBody
    @PostMapping("/delete")
    public CommonResult delete(@RequestParam(value = "id[]")  String[] id) {
        upmsAdminService.deleteAllById(Arrays.asList(id));
        return CommonResult.ok();
    }

    @RequiresPermissions(value = {"UPMS:ADMIN:VIEW", "UPMS:ADMIN:ADD", "UPMS:ADMIN:EDIT"}, logical = Logical.OR)
    @ResponseBody
    @RequestMapping("/excel/export")
    public void excelExport(@RequestParam(value = "id[]",required = false)  String id) {
        List<UpmsAdmin> list;
        if(id !=null){
            String[] idarr = id.split(",");
            list = upmsAdminService.findAllById(Arrays.asList(idarr));
        }else{
            list = upmsAdminService.findAll();
        }
        EasyExcel.write(getExcelStream("管理员"),UpmsAdminExcel.class)
                .sheet()
                .doWrite(UpmsAdminExcel.transform(list));
    }

    @RequiresPermissions(value = {"UPMS:ADMIN:VIEW", "UPMS:ADMIN:ADD", "UPMS:ADMIN:EDIT"}, logical = Logical.OR)
    @ResponseBody
    @RequestMapping("/excel/template")
    public void excelTemplate() {
        EasyExcel.write(getExcelStream("管理员模板"),UpmsAdminExcel.class)
                .sheet()
                .doWrite(new ArrayList());
    }

    @RequiresPermissions("UPMS:ADMIN:ADD")
    @ResponseBody
    @PostMapping("/excel/import")
    @SneakyThrows
    public void excelImport(@RequestParam(name ="file" ) MultipartFile file) {
        EasyExcel.read(file.getInputStream(),
                UpmsAdminExcel.class,
                new UpmsAdminExcelListener()).doReadAll();
    }


}

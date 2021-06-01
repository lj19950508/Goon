//package pers.gon.web.index;
//
//import cn.hutool.core.util.IdUtil;
//import cn.hutool.core.util.StrUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.shiro.crypto.hash.Md5Hash;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import pers.gon.domain.upms.entity.UpmsAdmin;
//import pers.gon.domain.upms.entity.UpmsDept;
//import pers.gon.domain.upms.entity.UpmsMenu;
//import pers.gon.domain.upms.entity.UpmsRole;
//import pers.gon.domain.upms.service.IUpmsAdminService;
//import pers.gon.domain.upms.service.IUpmsDeptService;
//import pers.gon.domain.upms.service.IUpmsMenuService;
//import pers.gon.domain.upms.service.IUpmsRoleService;
//import pers.gon.infrastructure.common.controller.BaseController;
//import pers.gon.infrastructure.common.entity.CommonResult;
//import pers.gon.infrastructure.common.valid.InsertGroup;
//
//import java.util.*;
//
//@Slf4j
//@Controller
//@RequestMapping("${global.adminpath}/test")
//public class TestController extends BaseController {
//
//    @Autowired
//    IUpmsAdminService sysAdminService;
//
//    @Autowired
//    IUpmsDeptService sysDeptService;
//
//    @Autowired
//    IUpmsRoleService sysRoleService;
//
//    @Autowired
//    IUpmsMenuService sysMenuService;
//
//    @RequestMapping("")
//    public String index(Model model){
//
//        List<Map<String,String>> checkBoxList = new ArrayList<>();
//        Map<String,String> checkBox = new HashMap<>();
//        checkBox.put("label","可爱的选项");
//        checkBox.put("value","1");
//
//        Map<String,String> checkBox2 = new HashMap<>();
//        checkBox2.put("label","可爱的选项2");
//        checkBox2.put("value","2");
//        checkBoxList.add(checkBox);
//        checkBoxList.add(checkBox2);
//        model.addAttribute("cblist", checkBoxList);
//
//        return "test.html";
//    }
//
//    @ResponseBody
//    @RequestMapping("/createAdmin")
//    public CommonResult createAdmin(){
//        UpmsAdmin upmsAdmin = new UpmsAdmin();
//        upmsAdmin.setAccount("admin01");
//        upmsAdmin.setSalt(System.currentTimeMillis()+"");
//
//        upmsAdmin.setPassword(new Md5Hash("admin","admin"+System.currentTimeMillis(),2).toString());
//        upmsAdmin.setNickname("Gon");
//
//        if(upmsAdmin.isNewRecord()){
//            String msg = validate(upmsAdmin,InsertGroup.class);
//            if(!StrUtil.isEmpty(msg)){
//                return CommonResult.fail(msg);
//            }
//        }
//        sysAdminService.save(upmsAdmin);
//        return CommonResult.ok();
//    }
//
//    @ResponseBody
//    @RequestMapping("/createDept")
//    public CommonResult createDept(){
//        UpmsDept upmsDept = new UpmsDept();
//        upmsDept.setCode("4");
//        upmsDept.setName("B-2部门");
//        UpmsDept upmsDept1 = new UpmsDept();
//        upmsDept1.setId("1307698519255158784");
//        upmsDept.setParent(upmsDept1);
//        sysDeptService.save(upmsDept);
//        return CommonResult.ok();
//    }
//
//    @ResponseBody
//    @RequestMapping("/createRole")
//    public CommonResult createRole(){
//        UpmsRole upmsRole = new UpmsRole();
//        upmsRole.setCode("superadmin");
//        upmsRole.setName("超级管理员");
//        UpmsDept upmsDept =new UpmsDept();
//        upmsDept.setId("402886b3744d9e1001744db92a7b0005");
////        upmsRole.setSysDept(upmsDept);
//
//
//        sysRoleService.save(upmsRole);
//        return CommonResult.ok();
//    }
//
//    @ResponseBody
//    @RequestMapping("/createMenu")
//    public CommonResult craeteMenu(){
//
//        UpmsMenu upmsMenu =new UpmsMenu();
//        upmsMenu.setCode("EHCACHE");
//        upmsMenu.setName("Ehcache管理");
//        upmsMenu.setPermission("DATASOURCE:EHCACHE");
//        upmsMenu.setType(0);
//        upmsMenu.setSort(0);
//        UpmsMenu p =new UpmsMenu();
//        p.setId("1304087336979664896");
//        upmsMenu.setParent(p);
//        sysMenuService.save(upmsMenu);
//        return CommonResult.ok();
//    }
//
//
//    @ResponseBody
//    @RequestMapping("/deleteMenu")
//    public CommonResult deleteMenu(){
//
//        String id = "1304090865513402373";
//        sysMenuService.deleteById(id);
//        return CommonResult.ok();
//    }
//
//    public static void main(String[] args) {
//        System.out.println(IdUtil.getSnowflake(1,1).nextIdStr());
//    }
//
//
//
//}

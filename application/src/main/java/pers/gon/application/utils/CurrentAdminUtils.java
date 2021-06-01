package pers.gon.application.utils;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.springframework.data.jpa.domain.Specification;
import pers.gon.application.shiro.AdminRealm;
import pers.gon.domain.upms.entity.UpmsAdmin;
import pers.gon.domain.upms.entity.UpmsMenu;
import pers.gon.domain.upms.entity.UpmsRole;
import pers.gon.domain.upms.service.IUpmsAdminService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static pers.gon.application.utils.DataScopeUtils.DS_ALL;
import static pers.gon.application.utils.DataScopeUtils.DS_LE_DEPT;

/**
 * @Author: Gon
 * @Date: 2020/9/1 22:40
 * 当前用户信息context
 **/

public class CurrentAdminUtils {

    static IUpmsAdminService sysAdminService = SpringUtil.getBean(IUpmsAdminService.class);

    public static List<Tree<String>> currentMenuTree(){
        UpmsAdmin currentAdmin = (UpmsAdmin) SecurityUtils.getSubject().getPrincipal();
        UpmsAdmin admin = sysAdminService.findById(currentAdmin.getId());
        Set<UpmsRole> roles =  admin.getRoles();
        Set<UpmsMenu> currentMenu = new HashSet<>();
        roles.forEach(item->{
            currentMenu.addAll(item.getMenus().stream().filter(menuItem-> !menuItem.getType().equals(DictUtils.getValue("upms_menu_type", "按钮"))).collect(Collectors.toSet()));
        });

        List<UpmsMenu> menus = new ArrayList<UpmsMenu>(currentMenu);
        return TreeUtil.build(menus,"",(treeNode, tree)->{
            if(null==treeNode.getParent()){
                tree.setParentId("");
            }else{
                tree.setParentId(treeNode.getParent().getId());
            }
            tree.put("url",treeNode.getUrl());
            tree.setId(treeNode.getId());
            tree.setWeight(treeNode.getSort());
            tree.setName(treeNode.getName());
        });
    }

    public static UpmsAdmin info(){
        return (UpmsAdmin) SecurityUtils.getSubject().getPrincipal();
    }

    public static Set<UpmsRole> roles(){
        Set<UpmsRole> upmsRoles = ((UpmsAdmin) SecurityUtils.getSubject().getPrincipal()).getRoles();
        return upmsRoles;
    }

    public static String id(){
        return ((UpmsAdmin) SecurityUtils.getSubject().getPrincipal()).getId();
    }

    public static String deptCode(){
        return ((UpmsAdmin) SecurityUtils.getSubject().getPrincipal()).getDept().getCode();
    }

    public static String deptId(){
        return ((UpmsAdmin) SecurityUtils.getSubject().getPrincipal()).getDept().getId();
    }



}

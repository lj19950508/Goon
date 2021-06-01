package pers.gon.application.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.util.Arrays;

/**
 * @Author: Gon
 * @Date: 2021/2/18
 * Shiro工具
 **/

public class ShiroUtils {

    public boolean isGuest() {
        return SecurityUtils.getSubject() == null || SecurityUtils.getSubject().getPrincipal() == null;
    }

    public boolean isUser() {
        return !isGuest();
    }

    public boolean hasRole(String roleName) {
        return SecurityUtils.getSubject() != null && SecurityUtils.getSubject().hasRole(roleName);
    }

    public boolean hasAnyRole(String... roleName) {
        boolean hasAnyRole = false;
        Subject subject = SecurityUtils.getSubject();
        if(subject!=null){
            for (int i = 0; i < roleName.length; i++) {
                if(subject.hasRole(roleName[i])){
                    hasAnyRole = true;
                    break;
                }
            }
        }
        return hasAnyRole;
    }

    public boolean hasPermission(String p) {
        return SecurityUtils.getSubject() != null && SecurityUtils.getSubject().isPermitted(p);
    }

    public boolean orPermission(String... p) {
        boolean orPermission = false;
        Subject subject = SecurityUtils.getSubject();
        if(subject!=null){
            for (int i = 0; i < p.length; i++) {
                if(subject.isPermitted(p[i])){
                    orPermission = true;
                    break;
                }
            }
        }
        return orPermission;
    }

    public boolean andPermission(String... p) {
        return SecurityUtils.getSubject() != null && SecurityUtils.getSubject().isPermittedAll(p);
    }


}

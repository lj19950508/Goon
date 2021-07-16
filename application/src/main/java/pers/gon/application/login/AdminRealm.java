package pers.gon.application.login;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import pers.gon.domain.upms.entity.UpmsAdmin;
import pers.gon.domain.upms.entity.UpmsMenu;
import pers.gon.domain.upms.entity.UpmsRole;
import pers.gon.domain.upms.repository.UpmsAdminRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Configuration
public class AdminRealm extends AuthorizingRealm {

    @Autowired
    UpmsAdminRepository upmsAdminRepository;

    /**
     * 权限信息
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        UpmsAdmin upmsAdmin = (UpmsAdmin) principalCollection.getPrimaryPrincipal(); // 获取用户名
        Set<String> roles = upmsAdmin.getRoles().stream().map(item-> item.getId()).collect(Collectors.toSet());
        Set<UpmsMenu> currentMenu = new HashSet<>();
        upmsAdmin.getRoles().forEach(item->{
            currentMenu.addAll(item.getMenus());
        });
        Set<String> permissions = currentMenu.stream().
                filter(item->item.getType()==2 || item.getType()==1 ).
                map(item->item.getPermission())
                .collect(Collectors.toSet());
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(permissions);
        simpleAuthorizationInfo.setRoles(roles);
        return simpleAuthorizationInfo;
    }

    /**
     * 基本身份信息
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String account = (String) authenticationToken.getPrincipal();
        UpmsAdmin upmsAdmin = upmsAdminRepository.findByAccount(account);
        if(upmsAdmin ==null){
            throw new AuthenticationException();
        }
        //强制触发JPA懒加载获取roles与menus
        Set<UpmsRole> roles = upmsAdmin.getRoles();
        roles.forEach(roleItem->{
            roleItem.getDatascopes().size();
            roleItem.getMenus().size();
        });
        String password = upmsAdmin.getPassword();
        String salt = upmsAdmin.getSalt();

        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(upmsAdmin, password, ByteSource.Util.bytes(upmsAdmin.getAccount()+salt), upmsAdmin.getNickname());
        return simpleAuthenticationInfo;
    }

}

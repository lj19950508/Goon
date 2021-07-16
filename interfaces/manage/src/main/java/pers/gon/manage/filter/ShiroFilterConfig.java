package pers.gon.manage.filter;


import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.gon.application.login.AdminRealm;
import pers.gon.infrastructure.common.config.global.GlobalProperties;

import java.util.LinkedHashMap;
import java.util.Map;
//还没想好怎么划分 后期可能考虑换掉shiro
@Configuration
public class ShiroFilterConfig {


    @Autowired
    GlobalProperties globalProperties;

    //这是过滤器
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        //通用静态文件夹过滤
        filterChainDefinitionMap.put("/static/**", "anon");
        //api模块过滤
        String apiPath  = globalProperties.getApiPath();
        filterChainDefinitionMap.put(apiPath+"/**", "anon");
        //app模块过滤
        String adminPath  = globalProperties.getAdminPath();
        filterChainDefinitionMap.put(adminPath+"/login", "anon");
        filterChainDefinitionMap.put(adminPath+"/logout", "anon");
        filterChainDefinitionMap.put(adminPath+"/test/**", "anon");
        filterChainDefinitionMap.put(adminPath+"/**", "authc");
        shiroFilterFactoryBean.setLoginUrl(adminPath+"/login");
        shiroFilterFactoryBean.setSuccessUrl(adminPath+"/index");
        shiroFilterFactoryBean.setUnauthorizedUrl(adminPath+"/unauth");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

}

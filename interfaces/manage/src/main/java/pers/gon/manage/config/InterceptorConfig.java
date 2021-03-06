package pers.gon.manage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pers.gon.infrastructure.common.config.global.GlobalProperties;
import pers.gon.manage.interceptor.LogInterceptor;

@Component
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    GlobalProperties globalProperties;
    @Autowired
    private LogInterceptor logInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加要拦截的url                                拦截的路径                                    放行的路径
        registry.addInterceptor(logInterceptor).addPathPatterns("/"+globalProperties.getAdminPath()+"/**").excludePathPatterns("/static/**");
    }


}
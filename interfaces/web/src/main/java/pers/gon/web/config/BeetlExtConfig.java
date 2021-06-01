package pers.gon.web.config;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.ext.fn.StringUtil;
import org.beetl.ext.web.SessionWrapper;
import org.beetl.ext.web.WebRenderExt;
import pers.gon.application.utils.CurrentAdminUtils;
import pers.gon.application.utils.DictUtils;
import pers.gon.application.utils.ShiroUtils;
import pers.gon.infrastructure.common.config.global.GlobalProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BeetlExtConfig implements WebRenderExt {


    static long version = System.currentTimeMillis();
    @Override
    public void modify(Template template, GroupTemplate groupTemplate, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        httpServletResponse.setBufferSize(1024*24);
        template.binding("version",version);
        template.binding("session", new SessionWrapper(httpServletRequest, httpServletRequest.getSession(false)));
        template.binding("request", httpServletRequest);
        template.binding("ctxPath", httpServletRequest.getContextPath());
        template.binding("ctxStatic", httpServletRequest.getContextPath()+"/static");
        template.binding("ctx", httpServletRequest.getContextPath()+SpringUtil.getBean(GlobalProperties.class).getAdminPath());
        template.binding("adminSystemName", SpringUtil.getBean(GlobalProperties.class).getAdminSystemName());
        groupTemplate.registerFunctionPackage("strutil", StrUtil.class);
        groupTemplate.registerFunctionPackage("shiro", ShiroUtils.class);
        groupTemplate.registerFunctionPackage("currentAdmin", CurrentAdminUtils.class);
        groupTemplate.registerFunctionPackage("dict", DictUtils.class);

    }
}

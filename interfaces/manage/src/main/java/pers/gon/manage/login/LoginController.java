package pers.gon.manage.login;


import lombok.extern.slf4j.Slf4j;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.gon.application.service.LoginService;
import pers.gon.infrastructure.common.config.global.GlobalProperties;
import pers.gon.infrastructure.common.controller.BaseController;


/**
 * @Author: Gon
 * @Date: 2020/8/26 20:57
 **/
@Slf4j
@Controller
@RequestMapping("${global.adminpath}")
public class LoginController extends BaseController {
	@Autowired
	GlobalProperties globalProperties;

	@Autowired
	LoginService loginService;


	@GetMapping("/login")
	public String toLogin(Model model) {
		if (null!=SecurityUtils.getSubject()  && SecurityUtils.getSubject().getPrincipal() != null) {
			return "redirect:" + globalProperties.getAdminPath();
		} else {
			return "modules/sys/login/login.html";
		}
	}


	@PostMapping("/login")
	public String doLogin(Model model, String account, String password) {
	    try{
		    loginService.login(account, password);
		    return "redirect:" + globalProperties.getAdminPath();
      }catch (AuthenticationException e){
          model.addAttribute("msg","账号或密码错误");
          return "modules/sys/login/login.html";
      }
	}

	@RequestMapping("/logout")
	public String logout(Model model) {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "redirect:" + globalProperties.getAdminPath() + "/login";
	}

	@RequestMapping("/unauth")
	public String unauth(Model model) {
		return "modules/sys/noauth/noauth.html";
	}

}

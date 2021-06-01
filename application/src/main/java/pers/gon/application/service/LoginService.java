package pers.gon.application.service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.gon.domain.upms.repository.UpmsAdminRepository;

/**
 * @Author: Gon
 * @Date: 2020/9/1 22:40
 **/
@Service
public class LoginService {

    @Autowired
    UpmsAdminRepository upmsAdminRepository;

    public void login(String account,String password) throws AuthenticationException {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken token=new UsernamePasswordToken(account,password);
        subject.login(token);
    }

}

package pers.gon.domain.upms.auditor;

import org.apache.shiro.SecurityUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import pers.gon.domain.upms.entity.UpmsAdmin;

import java.io.Serializable;
import java.util.Optional;

/**
 * @Author: Gon
 * @Date: 2020/8/31 23:55
 * 处理createBy和updateBy插入
 **/
@Component
public class AuditorAwareImpl implements AuditorAware<UpmsAdmin>, Serializable  {

    @Override
    public Optional<UpmsAdmin> getCurrentAuditor() {
        Object sysAdminObject =  SecurityUtils.getSubject().getPrincipal();
        if(sysAdminObject!=null){
            return Optional.of((UpmsAdmin) sysAdminObject);
        }else{
            return Optional.empty();
        }
    }
}

package pers.gon.domain.upms.service;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.gon.domain.upms.entity.UpmsAdmin;
import pers.gon.domain.upms.entity.UpmsDatascope;
import pers.gon.domain.upms.entity.UpmsRole;
import pers.gon.domain.upms.repository.UpmsAdminRepository;
import pers.gon.domain.upms.repository.UpmsDatascopeRepository;
import pers.gon.infrastructure.common.service.BaseService;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * @Author: Gon
 * @Date: 2020/8/27 23:14
 **/
@Service
public class UpmsDatascopeService extends BaseService<UpmsDatascopeRepository, UpmsDatascope,String> implements IUpmsDatascopeService {

    @Autowired
    UpmsRoleService roleService;

    @Override
    public void setDatascopeValue(UpmsRole upmsRole, String menuCode, Integer dsValue) {
        final boolean[] isExist = {false};
        upmsRole.getDatascopes().forEach(item->{
            if(item.getCode().equals(menuCode)){
                isExist[0] =true;
                item.setValue(dsValue);
            }
        });
        if(isExist[0] ==false){
            UpmsDatascope upmsDatascope = new UpmsDatascope();
            upmsDatascope.setValue(dsValue);
            upmsDatascope.setCode(menuCode);
            upmsDatascope.setName(menuCode);
            this.save(upmsDatascope);
            //UpmsDatascope这个要先SAVE
            Set<UpmsDatascope> datascopeSet = upmsRole.getDatascopes();
            datascopeSet.add(upmsDatascope);
            upmsRole.setDatascopes(datascopeSet);
        }
        roleService.save(upmsRole);
    }
}

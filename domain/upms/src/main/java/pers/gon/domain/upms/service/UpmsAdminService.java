package pers.gon.domain.upms.service;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import pers.gon.domain.upms.entity.UpmsAdmin;
import pers.gon.domain.upms.repository.UpmsAdminRepository;
import pers.gon.infrastructure.common.service.BaseService;


/**
 * @Author: Gon
 * @Date: 2020/8/27 23:14
 **/
@Service
public class UpmsAdminService extends BaseService<UpmsAdminRepository, UpmsAdmin,String> implements IUpmsAdminService {


    @Override
    public UpmsAdmin passwordEncode(UpmsAdmin upmsAdmin) {
        upmsAdmin.setSalt(System.currentTimeMillis()+"");
        upmsAdmin.setPassword(new Md5Hash(upmsAdmin.getPassword(), upmsAdmin.getAccount()+ upmsAdmin.getSalt(),2).toString());
        return upmsAdmin;
    }
}

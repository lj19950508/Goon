package pers.gon.domain.upms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pers.gon.domain.upms.entity.UpmsRole;
import pers.gon.domain.upms.repository.UpmsRoleRepository;
import pers.gon.infrastructure.common.service.BaseService;

/**
 * @Author: Gon
 * @Date: 2020/8/27 23:14
 **/
@Service
public class UpmsRoleService extends BaseService<UpmsRoleRepository, UpmsRole,String>  implements IUpmsRoleService {

    @Autowired
    UpmsRoleRepository upmsRoleRepository;


    @Override
    public UpmsRole findByCode(String code){
        UpmsRole upmsRole = upmsRoleRepository.findByCode(code);
        return upmsRole;
    }

}

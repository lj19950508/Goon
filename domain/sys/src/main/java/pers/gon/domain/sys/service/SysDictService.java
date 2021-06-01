package pers.gon.domain.sys.service;

import org.springframework.stereotype.Service;
import pers.gon.domain.sys.entity.SysDict;
import pers.gon.domain.sys.repository.SysDictRepository;
import pers.gon.infrastructure.common.service.BaseService;


/**
 * @Author: Gon
 * @Date: 2020/8/27 23:14
 **/
@Service
public class SysDictService extends BaseService<SysDictRepository, SysDict,String> implements ISysDictService {



    @Override
    public SysDict findByCode(String code) {
        return this.repository.findByCode(code);
    }
}

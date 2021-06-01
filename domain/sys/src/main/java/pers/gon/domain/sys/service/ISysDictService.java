package pers.gon.domain.sys.service;


import pers.gon.domain.sys.entity.SysDict;
import pers.gon.infrastructure.common.service.IBaseService;

/**
 * @Author: Gon
 * @Date: 2020/9/15 0:14
 **/

public interface ISysDictService extends IBaseService<SysDict,String>{

    SysDict findByCode(String code);

}

package pers.gon.domain.upms.service;


import pers.gon.domain.upms.entity.UpmsRole;
import pers.gon.infrastructure.common.service.IBaseService;

/**
 * @Author: Gon
 * @Date: 2020/9/15 0:14
 **/
public interface IUpmsRoleService extends IBaseService<UpmsRole,String>  {
     UpmsRole findByCode(String code);
}

package pers.gon.domain.upms.service;


import pers.gon.domain.upms.entity.UpmsAdmin;
import pers.gon.infrastructure.common.service.IBaseService;

/**
 * @Author: Gon
 * @Date: 2020/9/15 0:14
 **/

public interface IUpmsAdminService extends IBaseService<UpmsAdmin,String>{

    /**
     * 密码加密
     * @param upmsAdmin
     * @return
     */
    UpmsAdmin passwordEncode(UpmsAdmin upmsAdmin);

}

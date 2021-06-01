package pers.gon.domain.upms.service;


import pers.gon.domain.upms.entity.UpmsMenu;
import pers.gon.infrastructure.common.service.IBaseService;

/**
 * @Author: Gon
 * @Date: 2020/9/15 0:14
 **/
public interface IUpmsMenuService extends IBaseService<UpmsMenu,String> {
    UpmsMenu findByUrl(String url);
}

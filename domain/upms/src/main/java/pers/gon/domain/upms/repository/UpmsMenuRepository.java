package pers.gon.domain.upms.repository;

import pers.gon.domain.upms.entity.UpmsMenu;
import pers.gon.infrastructure.common.repository.BaseRepository;


/**
 * @Author: Gon
 * @Date: 2020/8/27 23:11
 **/
public interface UpmsMenuRepository extends BaseRepository<UpmsMenu,String> {
    UpmsMenu findByCode(String code);

    UpmsMenu findByUrl(String url);
}

package pers.gon.domain.sys.repository;

import pers.gon.domain.sys.entity.SysDict;
import pers.gon.infrastructure.common.repository.BaseRepository;


/**
 * @Author: Gon
 * @Date: 2020/8/27 23:11
 **/
public interface SysDictRepository extends BaseRepository<SysDict,String> {
    SysDict findByCode(String code);
    SysDict findByName(String name);
}

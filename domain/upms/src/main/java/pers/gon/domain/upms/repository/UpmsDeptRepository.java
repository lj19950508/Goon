package pers.gon.domain.upms.repository;

import pers.gon.domain.upms.entity.UpmsDept;
import pers.gon.infrastructure.common.repository.BaseRepository;


/**
 * @Author: Gon
 * @Date: 2020/8/27 23:11
 **/
public interface UpmsDeptRepository extends BaseRepository<UpmsDept,String> {
    UpmsDept findByCode(String code);
}

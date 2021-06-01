package pers.gon.domain.upms.repository;

import pers.gon.domain.upms.entity.UpmsRole;
import pers.gon.infrastructure.common.repository.BaseRepository;


/**
 * @Author: Gon
 * @Date: 2020/8/27 23:11
 **/
public interface UpmsRoleRepository extends BaseRepository<UpmsRole,String> {
    UpmsRole findByCode(String code);
}

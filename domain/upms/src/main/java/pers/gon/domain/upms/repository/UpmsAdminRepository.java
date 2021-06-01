package pers.gon.domain.upms.repository;

import pers.gon.domain.upms.entity.UpmsAdmin;
import pers.gon.infrastructure.common.repository.BaseRepository;

import javax.validation.Payload;


/**
 * @Author: Gon
 * @Date: 2020/8/27 23:11
 **/
public interface UpmsAdminRepository extends BaseRepository<UpmsAdmin, String>, Payload {

    UpmsAdmin findByAccount(String account);
}

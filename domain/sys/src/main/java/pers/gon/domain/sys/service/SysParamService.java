package pers.gon.domain.sys.service;


import org.springframework.stereotype.Service;
import pers.gon.domain.sys.entity.SysParam;
import pers.gon.domain.sys.repository.SysParamRepository;
import pers.gon.infrastructure.common.service.BaseService;

/**
 * @Author: Gon
 * @Date: 2020/8/27 23:14
 **/
@Service
public class SysParamService extends BaseService<SysParamRepository, SysParam,String> implements ISysParamService {


}

package pers.gon.domain.upms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import pers.gon.domain.upms.entity.UpmsDept;
import pers.gon.domain.upms.repository.UpmsDeptRepository;
import pers.gon.infrastructure.common.service.BaseService;

import java.util.List;

/**
 * @Author: Gon
 * @Date: 2020/8/27 23:14
 **/
@Service
public class UpmsDeptService extends BaseService<UpmsDeptRepository, UpmsDept,String> implements IUpmsDeptService {


}

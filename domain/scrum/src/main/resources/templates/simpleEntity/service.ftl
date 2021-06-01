package pers.gon.domain.${moduleName}.service;
import org.springframework.stereotype.Service;
import pers.gon.domain.${moduleName}.entity.${upEntityName};
import pers.gon.domain.${moduleName}.repository.${upEntityName}Repository;
import pers.gon.infrastructure.common.service.BaseService;

@Service
public class ${upEntityName}Service extends BaseService<${upEntityName}Repository, ${upEntityName},String> implements I${upEntityName}Service {


}

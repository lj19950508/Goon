package pers.gon.infrastructure.common.valid;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.springframework.data.jpa.domain.Specification;
import pers.gon.infrastructure.common.entity.BaseEntity;
import pers.gon.infrastructure.common.repository.BaseRepository;

import javax.persistence.criteria.Path;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;


public class UniqueConstraintValidator implements ConstraintValidator<Unique, BaseEntity> {
	private BaseRepository baseRepository;
	private String fieldName;


	@Override
	public void initialize(Unique unique) {
		baseRepository = SpringUtil.getBean(unique.repository());
		fieldName = unique.fieldName();
	}

	@Override
	public boolean isValid(BaseEntity entity, ConstraintValidatorContext context) {
	    //获取到注解的fieldName
		String[] paths = fieldName.split("\\.");
		Object submitField = entity;
		for(int i = 0; i < paths.length; i++) {
			submitField =  ReflectUtil.getFieldValue(submitField,paths[i]);
		}
		Object finalValue = submitField;
		long count = baseRepository.count((Specification) (root, cq, cb) -> {
			Path path = null;
			for (int i = 0; i < paths.length; i++) {
				if(i==0){
					path=root.get(paths[i]);
				}else{
					path=path.get(paths[i]);
				}
			}
			cq.where(cb.equal(path, finalValue));
		  	return null;
		});
		if(count==0){
			//如果为0则肯定不重复
			return true;
		}else if(count==1){
			//如果为1且ID为空肯定重复
			if(StrUtil.isNotEmpty(entity.getId())){
				//如果记录为原记录则不重复
				Optional baseEntity = baseRepository.findById(entity.getId());
				Object queryField = baseEntity.get();
				for (String path : paths) {
					queryField = ReflectUtil.getFieldValue(queryField, path);
				}
				return finalValue.equals(queryField);
			}else{
				return false;
			}
		}
		return false;

	}
}
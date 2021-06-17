package pers.gon.infrastructure.common.valid;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.data.jpa.domain.Specification;
import pers.gon.infrastructure.common.repository.BaseRepository;

import javax.persistence.criteria.Path;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class UniqueConstraintValidator implements ConstraintValidator<Unique, String> {
	private BaseRepository baseRepository;
	private String fieldName;


	@Override
	public void initialize(Unique unique) {
		baseRepository = SpringUtil.getBean(unique.repository());
		fieldName = unique.fieldName();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

	    //获取到注解的fieldName
		long count = baseRepository.count((Specification) (root, cq, cb) -> {
			String[] paths = fieldName.split("\\.");
			Path path = null;
			for (int i = 0; i < paths.length; i++) {
				if(i==0){
					path=root.get(paths[i]);
				}else{
					path=path.get(paths[i]);
				}
			}
			cq.where(cb.equal(path, value));
		  	return null;

		  	//当没有id时候 size为0
			//当有id时 id -> name 如果是相等且没有另外的name相等则通过
			////id
		});
		if(count==0){
			return true;
		}else if(count==1){
			//id->name  .equals(value) return ture; else return false;
			return true;
		}else{
			return false;
		}

	}
}
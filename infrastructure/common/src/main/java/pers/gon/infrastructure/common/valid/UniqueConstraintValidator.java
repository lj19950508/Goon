package pers.gon.infrastructure.common.valid;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.data.jpa.domain.Specification;
import pers.gon.infrastructure.common.repository.BaseRepository;

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
				cq.where(cb.equal(root.get(fieldName), value));
		  	return null;
		});

		return count==0;
	}
}
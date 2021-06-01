package pers.gon.infrastructure.common.valid;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.data.jpa.domain.Specification;
import pers.gon.infrastructure.common.entity.BaseEntity;
import pers.gon.infrastructure.common.repository.BaseRepository;
import pers.gon.infrastructure.common.valid.Exist;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class ExistConstraintValidator implements ConstraintValidator<Exist, BaseEntity> {
	private BaseRepository baseRepository;

	@Override
	public void initialize(Exist exist) {
		baseRepository = SpringUtil.getBean(exist.repository());
	}

	@Override
	public boolean isValid(BaseEntity entity, ConstraintValidatorContext context) {
		long count = baseRepository.count((Specification) (root, cq, cb) -> {
				cq.where(cb.equal(root.get("id"), entity.getId()));
		  	return null;
		});
		return count!=0;
	}
}
package pers.gon.infrastructure.common.valid;

import pers.gon.infrastructure.common.entity.BaseEntity;
import pers.gon.infrastructure.common.repository.BaseRepository;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueConstraintValidator.class)
public @interface Unique {
	String message() default "数据重复";

	Class<?>[] groups() default { };

	String fieldName();

	Class<? extends BaseRepository> repository();

	Class<? extends Payload>[] payload() default { };
}

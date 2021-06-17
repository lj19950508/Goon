package pers.gon.infrastructure.common.valid;

import pers.gon.infrastructure.common.entity.BaseEntity;
import pers.gon.infrastructure.common.repository.BaseRepository;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Null;
import java.lang.annotation.*;

@Repeatable(Uniques.class)
@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueConstraintValidator.class)
public @interface Unique {
	String message() default "数据重复";

	Class<?>[] groups() default { };

	String fieldName();

	Class<? extends BaseRepository> repository();

	Class<? extends Payload>[] payload() default { };
}

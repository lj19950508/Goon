package pers.gon.infrastructure.common.valid;

import pers.gon.infrastructure.common.repository.BaseRepository;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Null;
import java.lang.annotation.*;

@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Uniques {
	Unique[] value();
}

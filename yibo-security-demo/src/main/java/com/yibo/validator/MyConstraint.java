package com.yibo.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: huangyibo
 * @Date: 2019/7/27 1:28
 * @Description:
 */

@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MyConstraintValidator.class)
public @interface MyConstraint {

    String message() default "{校验不通过}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

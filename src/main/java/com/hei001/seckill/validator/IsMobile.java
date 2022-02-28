package com.hei001.seckill.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 *
 * 自定义注解
 * @author HEI001
 * @date 2022/2/28 13:17
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {IsMobileValidator.class}
)
public @interface IsMobile {
    boolean required() default true;//默认不能为空

    String message() default "手机号码格式错误";//校验不通过输出信息

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

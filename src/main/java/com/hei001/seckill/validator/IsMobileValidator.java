package com.hei001.seckill.validator;

import com.hei001.seckill.utils.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义规则
 * @author HEI001
 * @date 2022/2/28 13:19
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {
    private boolean required = false;

    //初始化
    @Override
    public void initialize(IsMobile isMobile) {
        required = isMobile.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (required) {
            return ValidatorUtil.isMobile(value);
        } else {
            if (StringUtils.isEmpty(value)) {
                return true;
            } else {
                return ValidatorUtil.isMobile(value);
            }
        }
    }

}

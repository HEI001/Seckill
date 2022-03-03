package com.hei001.seckill.vo;

import com.hei001.seckill.validator.IsMobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

/**
 * @author HEI001
 * @date 2022/2/28 12:57
 */
@Data
public class LoginVo {
    @NotNull
    @IsMobile
    private String mobile;
    @NotNull
    @Length(min = 32)
    private String password;
}

package com.hei001.seckill.config;

import com.hei001.seckill.pojo.User;
import com.hei001.seckill.service.IUserService;
import com.hei001.seckill.utils.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * 自定义用户参数
 * @author HEI001
 * @date 2022/2/28 22:45
 */
@Component
public class UserArgumentResolve implements HandlerMethodArgumentResolver {
    @Autowired
    private IUserService userService;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //进行一层条件判断，返回true才会执行下面的方法
        Class<?> clazz = parameter.getParameterType();
        return clazz == User.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return UserContext.getUser();
    }
}

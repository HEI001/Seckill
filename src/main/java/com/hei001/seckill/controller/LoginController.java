package com.hei001.seckill.controller;

import com.hei001.seckill.service.IUserService;
import com.hei001.seckill.vo.LoginVo;
import com.hei001.seckill.vo.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author HEI001
 * @date 2022/2/28 12:57
 */
@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {
    @Autowired
    IUserService userService;
    /**
     * 跳转登录界面
     * @return
     */
    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    /**
     * 登录功能
     * @param loginVo
     *
     * @return
     */
    @RequestMapping("/doLogin")
    @ResponseBody
    public RespBean doLogin(HttpServletRequest request,HttpServletResponse response, @Valid LoginVo loginVo){

        log.info("{}",loginVo);
        return userService.doLogin(request,response,loginVo);

    }
}

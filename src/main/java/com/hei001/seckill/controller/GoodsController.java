package com.hei001.seckill.controller;

import com.hei001.seckill.pojo.User;
import com.hei001.seckill.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author HEI001
 * @date 2022/2/28 14:03
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private IUserService userService;

    @RequestMapping("/to_list")
    public String toList( Model model,User user){
/*        //判断是否登录
        if (StringUtils.isEmpty(ticket)) {
            return "login";
        }
        //不通过session，通过redis
        //User user = (User) session.getAttribute(ticket);
        User user = userService.getUserByCookie(response, ticket);
        if(null==user){
            return "login";
        }*/
        model.addAttribute("user",user);
        return "goods_list";

    }

}

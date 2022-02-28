package com.hei001.seckill.controller;

import com.hei001.seckill.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;

/**
 * @author HEI001
 * @date 2022/2/28 14:03
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    @RequestMapping("/to_list")
    public String toList(HttpSession session, Model model, @CookieValue("userTicket") String ticket){
        //判断是否登录
        if (StringUtils.isEmpty(ticket)) {
            return "login";
        }
        User user = (User) session.getAttribute(ticket);
        if(null==user){
            return "login";
        }
        model.addAttribute("user",user);
        return "goods_list";

    }

}

package com.lzc.pet.controller;

import com.lzc.pet.domain.User;
import com.lzc.pet.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@RequestMapping("/pet")
@RestController
public class UserController {

    @Autowired
private UserServiceImpl userService;
    @PostMapping(value ="/User/email")
    @ResponseBody
    public String  email(@RequestParam("email") String email,HttpSession session){//请求发送邮件(验证邮箱是否被注册，邮箱地址问题)
        session.setAttribute("email",email);
        String result=userService.email(email);
        if(result.equals("-1"))
            return "发送邮件错误";
        else if(result.equals("1"))
            return "邮箱已被注册";
        else {
            session.setAttribute("check",result);
            return "注册验证邮件已发送,"+result;
        }
    }
    @RequestMapping("/bbs")
    public ModelAndView Lookbbs(){//打开bbs页面
        ModelAndView modelAndView=new ModelAndView("/bbs");//这里需要添加页面属性
        return modelAndView;
    }
    @PostMapping(value="/User/register")
    @ResponseBody
    public String register(User user,HttpSession session) {//注册请求（验证邮箱是否一致，是否有重名）
        String email=(String)session.getAttribute("email");
        String result=userService.checked(user,email);
            return result;
    }
}

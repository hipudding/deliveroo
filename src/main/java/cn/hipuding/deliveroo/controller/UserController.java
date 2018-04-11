package cn.hipuding.deliveroo.controller;

import cn.hipuding.deliveroo.entity.User;
import cn.hipuding.deliveroo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    UserService userService;


    @RequestMapping(value = "/user/login", method = RequestMethod.GET)
    public String login(){
        return "userLogin";
    }

    @RequestMapping(value = "/user/userInfo", method = RequestMethod.GET)
    public String getUserInfo(Model model, HttpServletRequest request){
        HttpSession session = request.getSession(true);
        String userName = session.getAttribute("userName").toString();
        User user = userService.getUser(userName);
        model.addAttribute("user",user);
        return "userInfo";
    }



}

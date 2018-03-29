package cn.hipuding.deliveroo.controller;

import cn.hipuding.deliveroo.entity.User;
import cn.hipuding.deliveroo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public void register(@RequestBody User user){
        userService.saveUser(user);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main(){
        return "Hello World!";
    }

    @RequestMapping(value = "check", method = RequestMethod.POST)
    public String check(@RequestBody User user){
        return userService.checkUserPassword(user.getId(), user.getPassword()) ? "OK" : "ERROR";
    }

}

package cn.hipuding.deliveroo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {


    @RequestMapping(value = "/user/login", method = RequestMethod.GET)
    public String login(){
        return "userLogin";
    }

    @RequestMapping(value = "/user/userInfo", method = RequestMethod.GET)
    public String getUserInfo(Model model){
//        List<EventListType> eventList = eventService.getEventList();
//        model.addAttribute("eventList", eventList);
        return "userInfo";
    }


}

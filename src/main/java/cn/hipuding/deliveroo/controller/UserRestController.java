package cn.hipuding.deliveroo.controller;

import cn.hipuding.deliveroo.entity.User;
import cn.hipuding.deliveroo.response.BaseResponse;
import cn.hipuding.deliveroo.response.ResponseCodeEnum;
import cn.hipuding.deliveroo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class UserRestController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(){
        return "Hello World!,this is deliveroo system !";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void root(HttpServletResponse response) throws IOException {
        response.sendRedirect("/user/login");
    }

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public BaseResponse register(@RequestBody User user){
        userService.saveUser(user);
        BaseResponse ret = new BaseResponse();
        ret.setCode(ResponseCodeEnum.OK.getCode());
        ret.setReason(ResponseCodeEnum.OK.getDesc());
        return ret;
    }


    @RequestMapping(value = "/user/doLogin", method = RequestMethod.POST)
    public BaseResponse doLogin(@RequestBody User user, HttpServletRequest request){
        boolean checkPWD = userService.checkUserPassword(user.getUserName(), user.getPassword());
        BaseResponse ret = new BaseResponse();
        if(checkPWD == true)
        {
            HttpSession session = request.getSession(true);
            session.setAttribute("userName",user.getUserName());
            ret.setCode(ResponseCodeEnum.OK.getCode());
            ret.setReason(ResponseCodeEnum.OK.getDesc());
        }
        else
        {
            ret.setCode(ResponseCodeEnum.PWDERROR.getCode());
            ret.setReason(ResponseCodeEnum.PWDERROR.getDesc());
        }
        return ret;
    }

    @RequestMapping(value = "/user/addToCart", method = RequestMethod.POST)
    public BaseResponse addToCart(@RequestBody User user, HttpServletRequest request){
        return new BaseResponse();
    }

    @RequestMapping(value = "/user/clearCart", method = RequestMethod.POST)
    public BaseResponse clearCart(@RequestBody User user, HttpServletRequest request){
        return new BaseResponse();
    }

    @RequestMapping(value = "/user/submit", method = RequestMethod.POST)
    public BaseResponse submit(@RequestBody User user, HttpServletRequest request){
        return new BaseResponse();
    }

    @RequestMapping(value = "/user/order", method = RequestMethod.POST)
    public BaseResponse order(@RequestBody User user, HttpServletRequest request){
        return new BaseResponse();
    }

    @RequestMapping(value = "/user/cart", method = RequestMethod.POST)
    public BaseResponse cart(@RequestBody User user, HttpServletRequest request){
        return new BaseResponse();
    }

}

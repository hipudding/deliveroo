package cn.hipuding.deliveroo.controller;

import cn.hipuding.deliveroo.entity.Goods;
import cn.hipuding.deliveroo.entity.Order;
import cn.hipuding.deliveroo.entity.User;
import cn.hipuding.deliveroo.response.BaseResponse;
import cn.hipuding.deliveroo.response.OrderResponse;
import cn.hipuding.deliveroo.response.ResponseCodeEnum;
import cn.hipuding.deliveroo.response.UserResponse;
import cn.hipuding.deliveroo.service.GoodsService;
import cn.hipuding.deliveroo.service.OrderService;
import cn.hipuding.deliveroo.service.UserService;
import cn.hipuding.deliveroo.utils.OrderStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
public class UserRestController {

    @Autowired
    UserService userService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

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
        BaseResponse ret = new BaseResponse();
        if(user == null || StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassword()) || StringUtils.isEmpty(user.getPhoneNumber())){
            ret.setCode(ResponseCodeEnum.PARAMETER_ERROR.getCode());
            ret.setReason(ResponseCodeEnum.PARAMETER_ERROR.getDesc());
        }

        userService.saveUser(user);
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
            ret.setCode(ResponseCodeEnum.PWD_ERROR.getCode());
            ret.setReason(ResponseCodeEnum.PWD_ERROR.getDesc());
        }
        return ret;
    }

    @RequestMapping(value = "/user/addCart/{goodsName}", method = RequestMethod.POST)
    public BaseResponse addCart(@PathVariable String goodsName, HttpServletRequest request){
        HttpSession session = request.getSession(true);
        String userName = session.getAttribute("userName").toString();

        return orderService.addCart(userName,goodsName);

    }

    @RequestMapping(value = "/user/removeCart/{orderId}", method = RequestMethod.POST)
    public BaseResponse removeCart(@PathVariable String orderId, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String userName = session.getAttribute("userName").toString();

        return orderService.removeCart(userName,orderId);

    }


    @RequestMapping(value = "/user/orders", method = RequestMethod.GET)
    public OrderResponse getOrders(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String userName = session.getAttribute("userName").toString();

        return orderService.getUserOrder(userName);

    }

    @RequestMapping(value = "/user/submit", method = RequestMethod.POST)
    public BaseResponse submitCart(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String userName = session.getAttribute("userName").toString();

        return orderService.submitCart(userName);

    }

    @RequestMapping(value = "/user/cart", method = RequestMethod.GET)
    public OrderResponse getCart(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String userName = session.getAttribute("userName").toString();

        return orderService.getUserCart(userName);

    }

    @RequestMapping(value = "/user/clear", method = RequestMethod.POST)
    public BaseResponse clearCart(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String userName = session.getAttribute("userName").toString();

        return orderService.clearCart(userName);

    }



}

package cn.hipuding.deliveroo.controller;

import cn.hipuding.deliveroo.entity.Goods;
import cn.hipuding.deliveroo.entity.Order;
import cn.hipuding.deliveroo.entity.Seller;
import cn.hipuding.deliveroo.entity.User;
import cn.hipuding.deliveroo.response.OrderResponse;
import cn.hipuding.deliveroo.response.ResponseCodeEnum;
import cn.hipuding.deliveroo.response.SellerResponse;
import cn.hipuding.deliveroo.response.UserResponse;
import cn.hipuding.deliveroo.service.OrderService;
import cn.hipuding.deliveroo.service.SellerService;
import cn.hipuding.deliveroo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;
    @Autowired
    SellerService sellerService;


    @RequestMapping(value = "/user/login", method = RequestMethod.GET)
    public String login() {
        return "userLogin";
    }

    @RequestMapping(value = "/user/userInfo", method = RequestMethod.GET)
    public String getUserInfo(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String userName = session.getAttribute("userName").toString();

        UserResponse ret = new UserResponse();
        User user = userService.getUser(userName);
        ret.setCode(ResponseCodeEnum.OK.getCode());
        ret.setReason(ResponseCodeEnum.OK.getDesc());
        ret.setUser(user);

        model.addAttribute("response", ret);
        return "userInfo";
    }

    @RequestMapping(value = "/user/shoppingCart", method = RequestMethod.GET)
    public String getShoppingCart(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String userName = session.getAttribute("userName").toString();
        OrderResponse ret = orderService.getUserCart(userName);
        model.addAttribute("response", ret);
        return "shoppingCart";
    }

    @RequestMapping(value = "/user/foodPlant", method = RequestMethod.GET)
    public String getFootPlant(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String userName = session.getAttribute("userName").toString();
        SellerResponse ret = new SellerResponse();
        Map<Seller, List<Goods>> allGoodsOfSeller = sellerService.getGoodsOfSeller();
        ret.setAllGoodsOfSeller(allGoodsOfSeller);
        model.addAttribute("response", ret);
        return "foodPlant";
    }

    @RequestMapping(value = "/user/orderInfo", method = RequestMethod.GET)
    public String getOrderInfo(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String userName = session.getAttribute("userName").toString();
        OrderResponse ret = orderService.getUserOrder(userName);
        model.addAttribute("response", ret);
        return "orderInfo";
    }

}

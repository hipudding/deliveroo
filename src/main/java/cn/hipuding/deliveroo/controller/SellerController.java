package cn.hipuding.deliveroo.controller;

import cn.hipuding.deliveroo.entity.Seller;
import cn.hipuding.deliveroo.entity.User;
import cn.hipuding.deliveroo.response.*;
import cn.hipuding.deliveroo.service.OrderService;
import cn.hipuding.deliveroo.service.SellerService;
import cn.hipuding.deliveroo.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SellerController {

    @Autowired
    SellerService sellerService;

    @Autowired
    OrderService orderService;


    @RequestMapping(value = "/seller/login", method = RequestMethod.GET)
    public String login(){
        return "sellerLogin";
    }

    @RequestMapping(value = "/seller/sellerInfo", method = RequestMethod.GET)
    public String getSellerInfo(Model model, HttpServletRequest request){
        HttpSession session = request.getSession(true);
        String userName = session.getAttribute("sellerName").toString();

        SellerResponse ret = new SellerResponse();
        Seller seller = sellerService.getSeller(userName);
        ret.setCode(ResponseCodeEnum.OK.getCode());
        ret.setReason(ResponseCodeEnum.OK.getDesc());
        ret.setSeller(seller);

        model.addAttribute("response",ret);
        return "sellerInfo";
    }

    @RequestMapping(value = "/seller/orderInfo", method = RequestMethod.GET)
    public String getSellerOrder(Model model, HttpServletRequest request){
        HttpSession session = request.getSession(true);
        String sellerName = session.getAttribute("sellerName").toString();

        OrderResponse ret =  orderService.getSellerPendingOrder(sellerName);
        model.addAttribute("response", ret);
        return "orderList";
    }



}

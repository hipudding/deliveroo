package cn.hipuding.deliveroo.controller;

import cn.hipuding.deliveroo.entity.Seller;
import cn.hipuding.deliveroo.entity.User;
import cn.hipuding.deliveroo.response.ResponseCodeEnum;
import cn.hipuding.deliveroo.response.SellerResponse;
import cn.hipuding.deliveroo.response.UserResponse;
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

@Controller
public class SellerController {

    @Autowired
    SellerService sellerService;


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
        List<Seller> sellerList = new ArrayList<>();
        sellerList.add(seller);
        ret.setCode(ResponseCodeEnum.OK.getCode());
        ret.setReason(ResponseCodeEnum.OK.getDesc());
        ret.setSellerList(sellerList);

        model.addAttribute("response",ret);
        return "sellerInfo";
    }



}

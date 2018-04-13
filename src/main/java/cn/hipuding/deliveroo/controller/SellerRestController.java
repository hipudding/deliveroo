package cn.hipuding.deliveroo.controller;

import cn.hipuding.deliveroo.entity.Seller;
import cn.hipuding.deliveroo.response.BaseResponse;
import cn.hipuding.deliveroo.response.ResponseCodeEnum;
import cn.hipuding.deliveroo.response.SellerResponse;
import cn.hipuding.deliveroo.service.OrderService;
import cn.hipuding.deliveroo.service.SellerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class SellerRestController {

    @Autowired
    SellerService sellerService;

    @Autowired
    OrderService orderService;


    @RequestMapping(value = "/seller/register", method = RequestMethod.POST)
    public BaseResponse register(@RequestBody Seller seller){
        BaseResponse ret = new BaseResponse();
        if(seller == null || StringUtils.isEmpty(seller.getSellerName()) || StringUtils.isEmpty(seller.getPassword())){
            ret.setCode(ResponseCodeEnum.PARAMETER_ERROR.getCode());
            ret.setReason(ResponseCodeEnum.PARAMETER_ERROR.getDesc());
            return ret;
        }

        sellerService.saveSeller(seller);
        ret.setCode(ResponseCodeEnum.OK.getCode());
        ret.setReason(ResponseCodeEnum.OK.getDesc());
        return ret;
    }


    @RequestMapping(value = "/seller/doLogin", method = RequestMethod.POST)
    public BaseResponse doLogin(@RequestBody Seller seller, HttpServletRequest request){
        boolean checkPWD = sellerService.checkSellerPassword(seller.getSellerName(), seller.getPassword());
        BaseResponse ret = new BaseResponse();
        if(checkPWD == true)
        {
            HttpSession session = request.getSession(true);
            session.setAttribute("sellerName",seller.getSellerName());
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

    @RequestMapping(value = "/seller/all", method = RequestMethod.GET)
    public SellerResponse getSellers(){
        SellerResponse ret = new SellerResponse();
        List<Seller> sellerList = sellerService.getAllSeller();
        ret.setCode(ResponseCodeEnum.OK.getCode());
        ret.setReason(ResponseCodeEnum.OK.getDesc());
        ret.setSellerList(sellerList);
        return ret;
    }

    @RequestMapping(value = "/seller/finish/{id}", method = RequestMethod.POST)
    public BaseResponse finishItem(@PathVariable String id, HttpServletRequest request){
        HttpSession session = request.getSession(true);
        //String sellerName = session.getAttribute("sellerName").toString();

        String sellerName = "shitang";

        return orderService.sellerFinishItem(sellerName,id);
    }

    @RequestMapping(value = "/seller/cancel/{id}", method = RequestMethod.POST)
    public BaseResponse cancelItem(@PathVariable String id, HttpServletRequest request){
        HttpSession session = request.getSession(true);
        //String sellerName = session.getAttribute("sellerName").toString();
        String sellerName = "shitang";

        return orderService.sellerCancelItem(sellerName,id);
    }

}

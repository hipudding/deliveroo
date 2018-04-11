package cn.hipuding.deliveroo.controller;

import cn.hipuding.deliveroo.entity.Seller;
import cn.hipuding.deliveroo.response.BaseResponse;
import cn.hipuding.deliveroo.response.ResponseCodeEnum;
import cn.hipuding.deliveroo.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class SellerRestController {

    @Autowired
    SellerService sellerService;


    @RequestMapping(value = "/seller/register", method = RequestMethod.POST)
    public BaseResponse register(@RequestBody Seller seller){
        sellerService.saveSeller(seller);
        BaseResponse ret = new BaseResponse();
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
            ret.setCode(ResponseCodeEnum.PWDERROR.getCode());
            ret.setReason(ResponseCodeEnum.PWDERROR.getDesc());
        }
        return ret;
    }

    @RequestMapping(value = "/sellers", method = RequestMethod.GET)
    public List<Seller> getSellers(){
        List<Seller> sellers = sellerService.getAllSeller();
        return sellers;
    }





}

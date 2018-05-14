package cn.hipuding.deliveroo.controller;

import cn.hipuding.deliveroo.entity.Goods;
import cn.hipuding.deliveroo.response.GoodsResponse;
import cn.hipuding.deliveroo.response.ResponseCodeEnum;
import cn.hipuding.deliveroo.service.GoodsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GoodsController {
    @Autowired
    GoodsService goodsService;

    @RequestMapping(value = "/seller/foodInfo", method = RequestMethod.GET)
    public String getSellerFoodInfo(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String sellerName = session.getAttribute("sellerName").toString();


        GoodsResponse ret = new GoodsResponse();
        List<Goods> goodsList = new ArrayList<>();
        ret.setGoodsList(goodsList);
        ret.setCode(ResponseCodeEnum.OK.getCode());
        ret.setReason(ResponseCodeEnum.OK.getDesc());

        ret.setGoodsList(goodsService.getGoodsBySeller(sellerName));

        model.addAttribute("response",ret);

        return "goodsList";

    }

}

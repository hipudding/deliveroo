package cn.hipuding.deliveroo.controller;

import cn.hipuding.deliveroo.entity.Goods;
import cn.hipuding.deliveroo.response.BaseResponse;
import cn.hipuding.deliveroo.response.GoodsResponse;
import cn.hipuding.deliveroo.response.ResponseCodeEnum;
import cn.hipuding.deliveroo.service.GoodsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
public class GoodsRestController {

    @Autowired
    GoodsService goodsService;


    @RequestMapping(value = "/goods/{type}/{value}", method = RequestMethod.GET)
    public GoodsResponse getGoodsBySeller(@PathVariable String value, @PathVariable String type){
        GoodsResponse ret = new GoodsResponse();
        List<Goods> goodsList = new ArrayList<>();
        ret.setGoodsList(goodsList);
        ret.setCode(ResponseCodeEnum.OK.getCode());
        ret.setReason(ResponseCodeEnum.OK.getDesc());

        if(StringUtils.isEmpty(value) || StringUtils.isEmpty(type) || (!type.equals("seller") && !type.equals("name"))){
            ret.setCode(ResponseCodeEnum.PARAMETER_ERROR.getCode());
            ret.setReason(ResponseCodeEnum.PARAMETER_ERROR.getDesc());
            return ret;
        }

        if(type.equals("seller")){
            ret.setGoodsList(goodsService.getGoodsBySeller(value));
        }
        else{
            Goods goods = goodsService.getGoods(value);
            goodsList.add(goods);
        }

        return ret;

    }

    @RequestMapping(value = "/goods/all", method = RequestMethod.GET)
    public GoodsResponse getGoods(){
        GoodsResponse ret = new GoodsResponse();
        ret.setGoodsList(goodsService.getAllGoods());
        ret.setCode(ResponseCodeEnum.OK.getCode());
        ret.setReason(ResponseCodeEnum.OK.getDesc());

        return ret;
    }


    @RequestMapping(value = "/goods/add", method = RequestMethod.POST)
    public GoodsResponse addGoods(@RequestBody Goods good, HttpServletRequest request){
        GoodsResponse ret = new GoodsResponse();
        HttpSession session = request.getSession(true);
        String sellerName = session.getAttribute("sellerName").toString();
        if(StringUtils.isEmpty(sellerName)){
            ret.setCode(ResponseCodeEnum.PARAMETER_ERROR.getCode());
            ret.setReason(ResponseCodeEnum.PARAMETER_ERROR.getDesc());
            return ret;
        }

        ret.setCode(ResponseCodeEnum.OK.getCode());
        ret.setReason(ResponseCodeEnum.OK.getDesc());
        goodsService.saveGoods(good);

        return ret;
    }


}

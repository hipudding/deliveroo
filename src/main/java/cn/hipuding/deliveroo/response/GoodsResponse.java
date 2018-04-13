package cn.hipuding.deliveroo.response;

import cn.hipuding.deliveroo.entity.Goods;

import java.util.List;

public class GoodsResponse extends BaseResponse{

    List<Goods> goodsList;

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }
}

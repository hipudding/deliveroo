package cn.hipuding.deliveroo.response;

import cn.hipuding.deliveroo.entity.Goods;
import cn.hipuding.deliveroo.entity.Seller;
import cn.hipuding.deliveroo.entity.User;

import java.util.List;
import java.util.Map;

public class SellerResponse extends BaseResponse{

    List<Seller> sellerList;
    Map<Seller, List<Goods>> allGoodsOfSeller;

    public List<Seller> getSellerList() {
        return sellerList;
    }

    public void setSellerList(List<Seller> sellerList) {
        this.sellerList = sellerList;
    }

    public Map<Seller, List<Goods>> getAllGoodsOfSeller() {
        return allGoodsOfSeller;
    }

    public void setAllGoodsOfSeller(Map<Seller, List<Goods>> allGoodsOfSeller) {
        this.allGoodsOfSeller = allGoodsOfSeller;
    }
}

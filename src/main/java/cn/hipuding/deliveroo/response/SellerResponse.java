package cn.hipuding.deliveroo.response;

import cn.hipuding.deliveroo.entity.Seller;
import cn.hipuding.deliveroo.entity.User;

import java.util.List;

public class SellerResponse extends BaseResponse{

    List<Seller> sellerList;

    public List<Seller> getSellerList() {
        return sellerList;
    }

    public void setSellerList(List<Seller> sellerList) {
        this.sellerList = sellerList;
    }
}

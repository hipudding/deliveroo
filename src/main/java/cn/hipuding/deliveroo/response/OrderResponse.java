package cn.hipuding.deliveroo.response;

import cn.hipuding.deliveroo.entity.Goods;
import cn.hipuding.deliveroo.entity.Order;

import java.util.List;

public class OrderResponse extends BaseResponse{

    List<Order> orderList;

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }
}

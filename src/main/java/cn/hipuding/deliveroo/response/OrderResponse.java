package cn.hipuding.deliveroo.response;

import cn.hipuding.deliveroo.entity.Goods;
import cn.hipuding.deliveroo.entity.Order;

import java.util.List;
import java.util.Map;

public class OrderResponse extends BaseResponse{

    List<Order> orderList;

    Map<String,List<Order>> orderMap;

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public Map<String, List<Order>> getOrderMap() {
        return orderMap;
    }

    public void setOrderMap(Map<String, List<Order>> orderMap) {
        this.orderMap = orderMap;
    }
}

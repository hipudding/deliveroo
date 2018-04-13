package cn.hipuding.deliveroo.service;

import cn.hipuding.deliveroo.dao.OrderDao;
import cn.hipuding.deliveroo.entity.Goods;
import cn.hipuding.deliveroo.entity.Order;
import cn.hipuding.deliveroo.entity.Seller;
import cn.hipuding.deliveroo.entity.User;
import cn.hipuding.deliveroo.response.BaseResponse;
import cn.hipuding.deliveroo.response.OrderResponse;
import cn.hipuding.deliveroo.response.ResponseCodeEnum;
import cn.hipuding.deliveroo.utils.OrderStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    GoodsService goodsService;

    @Autowired
    UserService userService;

    @Autowired
    SellerService sellerService;

    public void saveOrder(Order order){
        orderDao.saveOrUpdate(order);
    }


    public Order getOrder(String orderId){
        return orderDao.findById(orderId);
    }


    public void changeStatusById(String id, int status){
        String hql = "UPDATE orders SET status = ?0 WHERE id = ?1";
        orderDao.executeHQL(hql,status,id);
    }

    public void changeStatusByStatus(String userName, int oldStatus, int newStatus){
        String hql = "UPDATE orders SET status = ?0 WHERE status = ?1 and userName = ?2";
        orderDao.executeHQL(hql,newStatus,oldStatus,userName);
    }

    public void refreshCartTime(String userName){
        String hql = "UPDATE orders SET order_time = ?0 WHERE status = ?1 and userName = ?2";
        orderDao.executeHQL(hql,new Date(),OrderStatus.IN_CART,userName);
    }

    public List<Order> getOrderByUser(String userName,int status){
        String hql = "FROM orders WHERE user_name = ?0 and status = ?1";
        return orderDao.findByHQL(hql,userName,status);
    }


    public List<Order> getOrderBySeller(String sellerName,int status){
        String hql = "FROM orders WHERE seller_name = ?0 and status = ?1";
        return orderDao.findByHQL(hql,sellerName,status);
    }

    public BaseResponse addCart(String userName, String goodsName){
        BaseResponse ret = new BaseResponse();
        if(StringUtils.isEmpty(goodsName) || StringUtils.isEmpty(userName)){
            ret.setCode(ResponseCodeEnum.PARAMETER_ERROR.getCode());
            ret.setReason(ResponseCodeEnum.PARAMETER_ERROR.getDesc());
            return ret;
        }

        Goods goods = goodsService.getGoods(goodsName);
        if(goods == null){
            ret.setCode(ResponseCodeEnum.ITEM_NOT_EXIST.getCode());
            ret.setReason(ResponseCodeEnum.ITEM_NOT_EXIST.getDesc());
            return ret;
        }

        Order order = new Order();
        List<Order> orderInCart = getUserCart(userName).getOrderList();
        if(orderInCart.size() == 0){
            order.setOrderId(UUID.randomUUID().toString());
        }
        else {
            order.setOrderId(orderInCart.get(0).getOrderId());
        }

        order.setId(UUID.randomUUID().toString());
        order.setGoodsName(goodsName);
        order.setOrderTime(new Date());
        order.setSellerName(goods.getSellerName());
        order.setUserName(userName);
        order.setPrice(goods.getPrice());
        order.setStatus(OrderStatus.IN_CART);

        saveOrder(order);

        ret.setCode(ResponseCodeEnum.OK.getCode());
        ret.setReason(ResponseCodeEnum.OK.getDesc());
        return ret;
    }

    public BaseResponse removeCart(String userName, String id){
        BaseResponse ret = new BaseResponse();
        if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(id)){
            ret.setCode(ResponseCodeEnum.PARAMETER_ERROR.getCode());
            ret.setReason(ResponseCodeEnum.PARAMETER_ERROR.getDesc());
            return ret;
        }

        Order order = getOrder(id);
        if(order == null){
            ret.setCode(ResponseCodeEnum.ITEM_NOT_EXIST.getCode());
            ret.setReason(ResponseCodeEnum.ITEM_NOT_EXIST.getDesc());
            return ret;
        }

        changeStatusById(id,OrderStatus.DELETE);
        ret.setCode(ResponseCodeEnum.OK.getCode());
        ret.setReason(ResponseCodeEnum.OK.getDesc());
        return ret;
    }

    public OrderResponse getUserOrder(String userName){
        OrderResponse ret = new OrderResponse();
        if(StringUtils.isEmpty(userName)){
            ret.setCode(ResponseCodeEnum.PARAMETER_ERROR.getCode());
            ret.setReason(ResponseCodeEnum.PARAMETER_ERROR.getDesc());
            return ret;
        }

        List<Order> orderList = new ArrayList<>();
        orderList.addAll(getOrderByUser(userName,OrderStatus.CANCLED));
        orderList.addAll(getOrderByUser(userName,OrderStatus.PENGDIN));
        orderList.addAll(getOrderByUser(userName,OrderStatus.FINISHED));

        orderList.sort((Order o1, Order o2) -> {
            if (o1.getOrderTime().getTime() > o2.getOrderTime().getTime()) {
                return 1;
            } else if (o1.getOrderTime().getTime() < o2.getOrderTime().getTime()) {
                return -1;
            } else {
                if (o1.getStatus() >= o2.getStatus()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        ret.setCode(ResponseCodeEnum.OK.getCode());
        ret.setReason(ResponseCodeEnum.OK.getDesc());
        ret.setOrderList(orderList);
        return ret;
    }

    public OrderResponse getUserCart(String userName){
        OrderResponse ret = new OrderResponse();
        if(StringUtils.isEmpty(userName)){
            ret.setCode(ResponseCodeEnum.PARAMETER_ERROR.getCode());
            ret.setReason(ResponseCodeEnum.PARAMETER_ERROR.getDesc());
            return ret;
        }

        List<Order> orderList = getOrderByUser(userName,OrderStatus.IN_CART);


        orderList.sort((Order o1, Order o2) -> {
            if (o1.getOrderTime().getTime() >= o2.getOrderTime().getTime()) {
                return 1;
            } else{
                return -1;
            }
        });

        ret.setCode(ResponseCodeEnum.OK.getCode());
        ret.setReason(ResponseCodeEnum.OK.getDesc());
        ret.setOrderList(orderList);
        return ret;
    }

    public BaseResponse submitCart(String userName){
        BaseResponse ret = new BaseResponse();
        if(StringUtils.isEmpty(userName)){
            ret.setCode(ResponseCodeEnum.PARAMETER_ERROR.getCode());
            ret.setReason(ResponseCodeEnum.PARAMETER_ERROR.getDesc());
            return ret;
        }

        refreshCartTime(userName);
        changeStatusByStatus(userName,OrderStatus.IN_CART,OrderStatus.PENGDIN);

        ret.setCode(ResponseCodeEnum.OK.getCode());
        ret.setReason(ResponseCodeEnum.OK.getDesc());
        return ret;

    }

    public BaseResponse clearCart(String userName){
        BaseResponse ret = new BaseResponse();
        if(StringUtils.isEmpty(userName)){
            ret.setCode(ResponseCodeEnum.PARAMETER_ERROR.getCode());
            ret.setReason(ResponseCodeEnum.PARAMETER_ERROR.getDesc());
            return ret;
        }

        changeStatusByStatus(userName,OrderStatus.IN_CART,OrderStatus.DELETE);

        ret.setCode(ResponseCodeEnum.OK.getCode());
        ret.setReason(ResponseCodeEnum.OK.getDesc());
        return ret;

    }

    public OrderResponse getSellerPendingOrder(String sellerName){
        OrderResponse ret = new OrderResponse();
        if(StringUtils.isEmpty(sellerName)){
            ret.setCode(ResponseCodeEnum.PARAMETER_ERROR.getCode());
            ret.setReason(ResponseCodeEnum.PARAMETER_ERROR.getDesc());
            return ret;
        }

        List<Order> orderList = getOrderBySeller(sellerName,OrderStatus.PENGDIN);

        ret.setCode(ResponseCodeEnum.OK.getCode());
        ret.setReason(ResponseCodeEnum.OK.getDesc());
        ret.setOrderList(orderList);
        return ret;
    }

    @Transactional
    public BaseResponse sellerFinishItem(String sellerName, String id){
        BaseResponse ret = new BaseResponse();
        if(StringUtils.isEmpty(id)){
            ret.setCode(ResponseCodeEnum.PARAMETER_ERROR.getCode());
            ret.setReason(ResponseCodeEnum.PARAMETER_ERROR.getDesc());
            return ret;
        }

        ret.setCode(ResponseCodeEnum.ITEM_NOT_EXIST.getCode());
        ret.setReason(ResponseCodeEnum.ITEM_NOT_EXIST.getDesc());
        Order order = getOrder(id);
        if(order == null){
            return ret;
        }
        Seller seller = sellerService.getSeller(order.getSellerName());
        if(seller == null){
            return ret;
        }

        if(!order.getSellerName().equals(sellerName)){
            ret.setCode(ResponseCodeEnum.ACCESS_DENY.getCode());
            ret.setReason(ResponseCodeEnum.ACCESS_DENY.getDesc());
            return ret;
        }

        order.setStatus(OrderStatus.FINISHED);
        saveOrder(order);
        seller.setAccount(seller.getAccount() + order.getPrice());
        sellerService.saveSeller(seller);

        ret.setCode(ResponseCodeEnum.OK.getCode());
        ret.setReason(ResponseCodeEnum.OK.getDesc());

        return  ret;
    }

    @Transactional
    public BaseResponse sellerCancelItem(String sellerName, String id){
        BaseResponse ret = new BaseResponse();
        if(StringUtils.isEmpty(id)){
            ret.setCode(ResponseCodeEnum.PARAMETER_ERROR.getCode());
            ret.setReason(ResponseCodeEnum.PARAMETER_ERROR.getDesc());
            return ret;
        }

        ret.setCode(ResponseCodeEnum.ITEM_NOT_EXIST.getCode());
        ret.setReason(ResponseCodeEnum.ITEM_NOT_EXIST.getDesc());
        Order order = getOrder(id);
        if(order == null){
            return ret;
        }
        User user = userService.getUser(order.getUserName());
        if(user == null){
            return ret;
        }

        if(!order.getSellerName().equals(sellerName)){
            ret.setCode(ResponseCodeEnum.ACCESS_DENY.getCode());
            ret.setReason(ResponseCodeEnum.ACCESS_DENY.getDesc());
            return ret;
        }

        order.setStatus(OrderStatus.CANCLED);
        saveOrder(order);
        user.setAccount(user.getAccount() + order.getPrice());
        userService.saveUser(user);

        ret.setCode(ResponseCodeEnum.OK.getCode());
        ret.setReason(ResponseCodeEnum.OK.getDesc());

        return  ret;
    }


}

package cn.hipuding.deliveroo.service;

import cn.hipuding.deliveroo.dao.GoodsDao;
import cn.hipuding.deliveroo.dao.SellerDao;
import cn.hipuding.deliveroo.entity.Goods;
import cn.hipuding.deliveroo.entity.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GoodsService {

    @Autowired
    GoodsDao goodsDao;

    public void saveGoods(Goods goods){
        goodsDao.saveOrUpdate(goods);
    }

    public Goods getGoods(String goodsName){
        return goodsDao.findById(goodsName);
    }

    public List<Goods> getAllGoods(){
        return goodsDao.findAll();
    }

    public List<Goods> getGoodsBySeller(String sellerName){
        String hql = "FROM goods WHERE seller_name = ?0";
        return goodsDao.findByHQL(hql,sellerName);
    }

    public void deleteGoods(String id)
    {
        goodsDao.delete(id);
    }

}

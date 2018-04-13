package cn.hipuding.deliveroo.service;

import cn.hipuding.deliveroo.dao.SellerDao;
import cn.hipuding.deliveroo.entity.Goods;
import cn.hipuding.deliveroo.entity.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SellerService {

    @Autowired
    SellerDao sellerDao;
    @Autowired
    GoodsService goodsService;

    public void saveSeller(Seller seller) {
        sellerDao.saveOrUpdate(seller);
    }

    public boolean checkSellerPassword(String sellerName, String password) {
        Seller seller = sellerDao.findById(sellerName);
        if (seller == null)
            return false;
        return seller.getPassword().equals(password);
    }

    public Seller getSeller(String userName) {
        return sellerDao.findById(userName);
    }

    public List<Seller> getAllSeller() {
        return sellerDao.findAll();
    }

    public Map<Seller, List<Goods>> getGoodsOfSeller() {
        Map<Seller, List<Goods>> goodsOfSeller = new HashMap();
        List<Seller> sellers = getAllSeller();
        for (Seller seller : sellers) {
            List<Goods> goods = goodsService.getGoodsBySeller(seller.getSellerName());
            goodsOfSeller.put(seller, goods);
        }
        return goodsOfSeller;
    }

}

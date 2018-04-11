package cn.hipuding.deliveroo.service;

import cn.hipuding.deliveroo.dao.SellerDao;
import cn.hipuding.deliveroo.entity.Seller;
import cn.hipuding.deliveroo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SellerService {

    @Autowired
    SellerDao sellerDao;

    public void saveSeller(Seller seller){
        sellerDao.saveOrUpdate(seller);
    }

    public boolean checkSellerPassword(String sellerName, String password){
        Seller seller = sellerDao.findById(sellerName);
        if(seller == null)
            return false;
        return seller.getPassword().equals(password);
    }

    public Seller getSeller(String userName){
        return sellerDao.findById(userName);
    }

    public List<Seller> getAllSeller(){
        return sellerDao.findAll();
    }

}

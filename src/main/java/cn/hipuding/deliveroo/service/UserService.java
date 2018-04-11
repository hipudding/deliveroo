package cn.hipuding.deliveroo.service;

import cn.hipuding.deliveroo.dao.UserDao;
import cn.hipuding.deliveroo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    UserDao userDao;

    public void saveUser(User user){
        userDao.saveOrUpdate(user);
    }

    public boolean checkUserPassword(String userName, String password){
        User user = userDao.findById(userName);
        if(user == null)
            return false;
        return user.getPassword().equals(password);
    }

    public User getUser(String userName){
        return userDao.findById(userName);
    }

}

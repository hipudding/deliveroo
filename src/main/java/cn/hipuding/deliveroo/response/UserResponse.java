package cn.hipuding.deliveroo.response;

import cn.hipuding.deliveroo.entity.Goods;
import cn.hipuding.deliveroo.entity.User;

import java.util.List;

public class UserResponse extends BaseResponse{

    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

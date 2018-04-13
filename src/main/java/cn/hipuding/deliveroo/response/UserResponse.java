package cn.hipuding.deliveroo.response;

import cn.hipuding.deliveroo.entity.Goods;
import cn.hipuding.deliveroo.entity.User;

import java.util.List;

public class UserResponse extends BaseResponse{

    List<User> userList;

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}

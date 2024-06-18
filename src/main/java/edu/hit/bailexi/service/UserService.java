package edu.hit.bailexi.service;
import edu.hit.bailexi.domain.User;

public interface UserService {
    /**
     * 根据前端输入的用户信息进行登录
     * @param InputedFromFrontSide 前端页面输入的用户信息封装的对象
     * @return 数据库中查询的对象
     */
    User login(User InputedFromFrontSide);

    boolean regist(User user);
}

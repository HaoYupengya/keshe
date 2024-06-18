package edu.hit.bailexi.service.impl;

import edu.hit.bailexi.dao.UserDao;
import edu.hit.bailexi.dao.impl.UserDaoImpl;
import edu.hit.bailexi.domain.User;
import edu.hit.bailexi.service.UserService;

public class UserServiceImpl implements UserService {

    private UserDao dao= (UserDao) new UserDaoImpl();

    @Override  //注册
    public boolean regist(User user) {
        dao.save(user);
        return true;
    }

    @Override
    public User login(User InputedFromFrontSide) {  //登录
        User user = dao.findUserByUsernameAndPassword(InputedFromFrontSide.getUsername(), InputedFromFrontSide.getPassword());

        return user;
    }


}

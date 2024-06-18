package edu.hit.bailexi.dao;

import edu.hit.bailexi.domain.User;

public interface UserDao {

    /**
     * 根据用户名和密码查询用户
     * @param username 用户名
     * @param password 密码
     * @return 查询出的用户对象
     */
    User findUserByUsernameAndPassword(String username, String password);

    /**
     * 保存用户信息
     * @return: void
     */
    void save(User user);
}

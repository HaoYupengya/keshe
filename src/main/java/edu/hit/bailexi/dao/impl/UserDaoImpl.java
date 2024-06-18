package edu.hit.bailexi.dao.impl;

import edu.hit.bailexi.dao.UserDao;
import edu.hit.bailexi.util.JDBCUtils;
import edu.hit.bailexi.domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource()); //获取数据库连接
    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        String sql = "select * from tab_user where username = ? and password = ?";
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
        }catch (Exception e){
        }
        return user;
    }

    public void save(User user) {
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email) values(?,?,?,?,?,?,?)";

        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getName(),
                user.getBirthday(), user.getSex(), user.getTelephone(), user.getEmail()
               );
    }
}

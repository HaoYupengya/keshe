package edu.hit.bailexi.dao.impl;

import edu.hit.bailexi.dao.SellerDao;
import edu.hit.bailexi.domain.Seller;
import edu.hit.bailexi.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class SellerDaoImpl implements SellerDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public Seller findById(int id) {
        String sql = "select * from tab_seller where sid = ?";
        return  template.queryForObject(sql,new BeanPropertyRowMapper<Seller >(Seller.class),id);
    }
}

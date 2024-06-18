package edu.hit.bailexi.dao.impl;

import edu.hit.bailexi.dao.CategoryDao;
import edu.hit.bailexi.domain.Category;
import edu.hit.bailexi.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<Category> findAll() {
        String sql = "select * from tab_category";
        return  template.query(sql,new BeanPropertyRowMapper<Category>(Category.class));
    }

    @Override
    public Category findByCid(int cid) {
        String sql = "select * from tab_category where cid = ?";
        return  template.queryForObject(sql,new BeanPropertyRowMapper<Category>(Category.class),cid);
    }
}

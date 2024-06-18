package edu.hit.bailexi.dao.impl;

import edu.hit.bailexi.dao.RouteImgDao;
import edu.hit.bailexi.domain.RouteImg;
import edu.hit.bailexi.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteImgDaoImpl implements RouteImgDao {

    private final JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<RouteImg> findByRid(int rid) {
        String sql = "select * from tab_route_img where rid = ?";
        return  template.query(sql, new BeanPropertyRowMapper<>(RouteImg.class),rid);
    }
}

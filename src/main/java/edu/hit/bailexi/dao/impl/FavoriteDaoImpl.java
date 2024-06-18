package edu.hit.bailexi.dao.impl;

import edu.hit.bailexi.dao.FavoriteDao;
import edu.hit.bailexi.domain.Favorite;
import edu.hit.bailexi.domain.TabFavourite;
import edu.hit.bailexi.util.JDBCUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

public class FavoriteDaoImpl implements FavoriteDao {
    private final JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public void add(String rid, int uid) {
        String sql ="insert into tab_favorite values(?,?,?)";

        template.update(sql,rid,new Date(),uid);

    }

    @Override
    public Favorite findFavouriteByRidAndUid(String rid, int uid) {
        String sql = "select * from tab_favorite where rid=? and uid=?";
        try {
            return template.queryForObject(sql, new BeanPropertyRowMapper<>(Favorite.class),rid,uid);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    //收藏量
    @Override
    public int findFavoriteCount(String rid) {
        String sql = "select count(*) from tab_favorite where rid=?";
        Integer result;
        if ((result = template.queryForObject(sql,Integer.class,rid)) != null) {
            return result;
        }
        return 0;
    }

    @Override
    public void removeFavourite(String rid, int uid) {
        String sql = "delete from tab_favorite where rid=? and uid=?";

        template.update(sql, rid, uid);
    }

    //查询总线路条数
    public int findTotalCountByUid(int uid) {
        String sql = "select count(*) from tab_favorite where uid = ?";
        Integer result;
        if ((result = template.queryForObject(sql,Integer.class,uid)) != null) {
            return result;
        }
        return 0;
    }
    //分页查询出用户收藏TabFavorite信息
    public List<TabFavourite> findByUid(int uid, int start, int pageSize) {
        String sql = "select * from tab_favorite where uid = ? limit ?, ?";
        return template.query(sql,new BeanPropertyRowMapper<>(TabFavourite.class),uid,start,pageSize);
    }


}

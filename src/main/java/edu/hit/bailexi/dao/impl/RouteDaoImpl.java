package edu.hit.bailexi.dao.impl;

import edu.hit.bailexi.dao.RouteDao;
import edu.hit.bailexi.domain.Route;
import edu.hit.bailexi.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {

    private final JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int findTotalCount(int cid,String rname) {
        String sql="select count(*) from tab_route ";
        StringBuilder sb=new StringBuilder(sql);
        Integer result;

        if(cid!=0){
            sb.append(" where cid = ? ");
            sql=sb.toString();

            if ((result = template.queryForObject(sql, Integer.class, cid)) != null) {
                return result;
            }
        }
        if(!rname.equals("null") && rname.length()>0){
            sb.append(" where rname like ? ");
            rname = "%"+rname+"%";
            sql=sb.toString();
            if ((result = template.queryForObject(sql, Integer.class, rname)) != null) {
                return result;
            }
        }

        return 0;
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize,String rname) {
        //String sql = "select * from tab_route where cid = ? limit ? , ?";
        //定义sql模板
        String sql="select * from tab_route ";
        StringBuilder sb=new StringBuilder(sql);

        List<Object> params=new ArrayList<>();
        if(cid!=0){
            sb.append(" where cid = ? ");
            params.add(cid);
        }
        if(!rname.equals("null") && rname.length()>0){
            sb.append(" where rname like ? ");
            params.add("%"+rname+"%");
        }
        sb.append(" limit ? , ? ");//分页条件
        params.add(start);
        params.add(pageSize);
        sql=sb.toString();
        return template.query(sql,new BeanPropertyRowMapper<>(Route.class),params.toArray());
    }

    @Override
    public Route findOne(int rid) {
        String sql = "select * from tab_route where rid = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<>(Route.class),rid);
    }

    @Override
    public void updateFavoriteCount(String rid,Integer count) {
        String sql = "update tab_route set count= ? where rid=? ";

        template.update(sql,count,rid);
    }

    @Override
    public List<Route> findFavouriteRank(int pagesize) {
        String sql = "SELECT rid, rname, price, routeIntroduce, rflag, rdate, \n" +
                "isThemeTour, count, cid, rimage, sid, sourceId FROM \n" +
                "tab_route tabr NATURAL JOIN (SELECT rid, count(*) \n" +
                "as num_favor FROM tab_favorite GROUP BY rid) cour \n" +
                "ORDER BY num_favor DESC LIMIT 0, ?;";

        return template.query(sql,new BeanPropertyRowMapper<>(Route.class), pagesize);
    }

    @Override
    public void save(Route route) {
        String sql ="insert into tab_route(rname,price,rflag,routeIntroduce,cid,sid,junzi) values(?,?,?,?,?,?,?)";
        template.update(sql,route.getRname(),route.getPrice(),route.getRflag(),route.getRouteIntroduce(),route.getCid(),route.getSid(),route.getJunzi());
//        insert into tab_user(username,password,name,birthday,sex,telephone,email) values(?,?,?,?,?,?,?)
    }

    @Override
    public Route findNew() {
        String sql = "select * from tab_route order by rid desc limit 1";
        return  template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class));
    }

    public List<Route> findByjunzi(int rid){
        String sql = "select * from tab_route where junzi = (select junzi from tab_route where rid = ?)";
//        String sql = "select * from tab_route where rid = ?";
        return template.query(sql, new BeanPropertyRowMapper<>(Route.class),rid);
    }

    @Override
    public List<Route> findByUser(int user) {
        String sql = "select * from tab_route where sid =  ? ";
        return template.query(sql, new BeanPropertyRowMapper<>(Route.class),user);

    }
}

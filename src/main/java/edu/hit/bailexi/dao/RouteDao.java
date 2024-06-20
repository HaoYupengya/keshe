package edu.hit.bailexi.dao;

import edu.hit.bailexi.domain.Route;

import java.util.List;

public interface RouteDao {

    //根据cid查询总记录数
    int findTotalCount(int cid,String rname);

    //根据cid，start，pageSize查询当前页数据集合
    List<Route> findByPage(int cid, int start, int pageSize,String rname);

    /**
     * 根据id查询
     * @return rid
     */
    Route findOne(int rid);

    void updateFavoriteCount(String rid,Integer count);

    List<Route> findFavouriteRank(int pagesize);
    void save(Route route);

    Route findNew();
    public List<Route> findByjunzi(int rid);
    public List<Route> findByUser(int user);
}

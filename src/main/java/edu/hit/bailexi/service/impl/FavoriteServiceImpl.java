package edu.hit.bailexi.service.impl;

import edu.hit.bailexi.dao.FavoriteDao;
import edu.hit.bailexi.dao.RouteDao;
import edu.hit.bailexi.dao.RouteImgDao;
import edu.hit.bailexi.dao.SellerDao;
import edu.hit.bailexi.dao.impl.FavoriteDaoImpl;
import edu.hit.bailexi.dao.impl.RouteDaoImpl;
import edu.hit.bailexi.dao.impl.RouteImgDaoImpl;
import edu.hit.bailexi.dao.impl.SellerDaoImpl;
import edu.hit.bailexi.domain.*;
import edu.hit.bailexi.service.FavoriteService;

import java.util.ArrayList;
import java.util.List;

public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteDao favoriteDao = new FavoriteDaoImpl();
    private final RouteDao routeDao = new RouteDaoImpl();
    private final RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private final SellerDao sellerDao = new SellerDaoImpl();

    @Override
    public void add(String rid, int uid) {
        favoriteDao.add(rid, uid);
        int count = favoriteDao.findFavoriteCount(rid);
        routeDao.updateFavoriteCount(rid, count);
    }

    @Override
    public Boolean isFavourite(String rid, int uid) {
        Favorite favorite = favoriteDao.findFavouriteByRidAndUid(rid, uid);
        boolean flag;
        flag = favorite != null;
        return flag;
    }

    @Override
    public void removeFavourite(String rid, int uid) {
        favoriteDao.removeFavourite(rid, uid);
        int count = favoriteDao.findFavoriteCount(rid);
        routeDao.updateFavoriteCount(rid, count);
    }

    /**
     * 查询用户的所有收藏线路,响应pageBean对象给客户端
     */
    @Override
    public PageBean<Favorite> myFavorite(User user, int currentPage, int pageSize) {
        // 定义PageBean对象
        PageBean<Favorite> pageBean = new PageBean<>();
        List<Favorite> myFavorite = new ArrayList<>();

        // 调用favoriteDao中查询总线路条数
        int totalCount = favoriteDao.findTotalCountByUid(user.getUid());

        // 计算总页面数
        int totalPage = (totalCount % pageSize == 0)?(totalCount / pageSize):(totalCount / pageSize + 1);

        // 计算开始查询位置
        int start = (currentPage - 1) * pageSize;

        // 通过favoriteDao调用uid查询tab_favorite表,返回list集合
        List<TabFavourite> list = favoriteDao.findByUid(user.getUid(),start,pageSize);

        // 遍历集合;
        Route route;    // 定义旅游路线对象
        for (TabFavourite tab_favorite : list) {
            // 调用RouteDao中通过rid查询tab_route表
            route = routeDao.findOne(tab_favorite.getRid());
            //根据route的id 查询图片集合信息
            List<RouteImg> routeImgList = routeImgDao.findByRid(route.getRid());
            //将集合设置到route对象
            route.setRouteImgList(routeImgList);
            //根据route的sid（商家id）查询商家对象
            Seller seller = sellerDao.findById(route.getSid());
            route.setSeller(seller);
            //通过rid查询tab_favorite表中该路线的收藏次数
            route.setCount(favoriteDao.findFavoriteCount(String.valueOf(tab_favorite.getRid())));
            Favorite favorite = new Favorite(route,tab_favorite.getDate(),user);
            myFavorite.add(favorite);
        }

        // 封装pageBean对象
        pageBean.setTotalCount(totalCount);
        pageBean.setTotalPage(totalPage);
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);
        pageBean.setList(myFavorite);
        return pageBean;
    }

    @Override
    public PageBean<Route> favouriteRank(int pageSize) {
        PageBean<Route> pageBean = new PageBean<>();
        List<Route> favouriteRankList;
        favouriteRankList = routeDao.findFavouriteRank(pageSize);

        pageBean.setTotalCount(1);
        pageBean.setTotalPage(1);
        pageBean.setCurrentPage(1);
        pageBean.setPageSize(pageSize);
        pageBean.setList(favouriteRankList);
        return pageBean;
    }


}

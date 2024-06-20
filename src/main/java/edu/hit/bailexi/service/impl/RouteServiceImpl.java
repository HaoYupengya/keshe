package edu.hit.bailexi.service.impl;

import edu.hit.bailexi.dao.CategoryDao;
import edu.hit.bailexi.dao.RouteDao;
import edu.hit.bailexi.dao.RouteImgDao;
import edu.hit.bailexi.dao.SellerDao;
import edu.hit.bailexi.dao.impl.CategoryDaoImpl;
import edu.hit.bailexi.dao.impl.RouteDaoImpl;
import edu.hit.bailexi.dao.impl.RouteImgDaoImpl;
import edu.hit.bailexi.dao.impl.SellerDaoImpl;
import edu.hit.bailexi.domain.*;
import edu.hit.bailexi.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private CategoryDao categoryDao = new CategoryDaoImpl();
    @Override
    public PageBean<Route> PageQuery(int cid, int currentPage, int pageSize, String rname) {
        //封装PageBean
        PageBean<Route> pb = new PageBean<>();
        //设置当前页码
        pb.setCurrentPage(currentPage);
        //设置每页显示条数
        pb.setPageSize(pageSize);

        //设置总记录数
        int totalCount = routeDao.findTotalCount(cid,rname);
        pb.setTotalCount(totalCount);

        //设置当前页显示的数据集合
        int start = (currentPage - 1) * pageSize;//开始的记录数
        List<Route> list = routeDao.findByPage(cid,start,pageSize,rname);
        pb.setList(list);

        //设置总页数 = 总记录数/每页的显示条数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize :(totalCount / pageSize) + 1;
        pb.setTotalPage(totalPage);


        return pb;
    }

    @Override
    public Route findOne(String rid) {
        if (rid != null){
            //1.根据id去route中查询route对象
            Route route = routeDao.findOne(Integer.parseInt(rid));
            //2.根据route的id查询图片集合信息
            List<RouteImg>routeImgList = routeImgDao.findByRid(Integer.parseInt(rid));
            //2.2将集合设置到route对象
            route.setRouteImgList(routeImgList);
            //3.根据route的sid(商家)查询卖家的信息
            Seller seller = sellerDao.findById(route.getSid());
            route.setSeller(seller);
            //4.根据route的cid查找分类
            Category category = categoryDao.findByCid(route.getCid());
            route.setCategory(category);
            return route;
        }
        return null;
    }

    @Override
    public int upload(Route route) {
        routeDao.save(route);
        return routeDao.findNew().getRid();
    }

    public List<Route> findByjunzi(String rid){
        if (rid != null){
            //1.根据id去route中查询route对象
            List<Route> route = routeDao.findByjunzi(Integer.parseInt(rid));
            return route;
        }
        return null;
    }

    @Override
    public PageBean<Route>findByUser(int user) {
        List<Route> route =  routeDao.findByUser(user);
        PageBean<Route> pageBean = new PageBean<>();
        pageBean.setTotalCount(1);
        pageBean.setTotalPage(1);
        pageBean.setCurrentPage(1);
        pageBean.setPageSize(route.size());
        pageBean.setList(route);
        return pageBean;
    }


}

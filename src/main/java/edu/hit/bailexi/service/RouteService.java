package edu.hit.bailexi.service;

import edu.hit.bailexi.domain.PageBean;
import edu.hit.bailexi.domain.Route;

import java.util.List;

public interface RouteService {
    PageBean<Route> PageQuery(int cid, int currentPage, int pageSize, String rname);

    /**
     * 根据id查询
     * @param rid
     * @return
     */
    public Route findOne(String rid);
    public int upload(Route route);
    public List<Route> findByjunzi(String rid);
}

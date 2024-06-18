package edu.hit.bailexi.service;

import edu.hit.bailexi.domain.Favorite;
import edu.hit.bailexi.domain.PageBean;
import edu.hit.bailexi.domain.Route;
import edu.hit.bailexi.domain.User;

public interface FavoriteService {
    void add(String rid, int uid);

    Boolean isFavourite(String rid, int uid);

    void removeFavourite(String rid, int uid);

    PageBean<Favorite> myFavorite(User user, int currentPage, int pageSize);

    PageBean<Route> favouriteRank(int pageSize);
}
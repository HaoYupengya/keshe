package edu.hit.bailexi.dao;

import edu.hit.bailexi.domain.Favorite;
import edu.hit.bailexi.domain.TabFavourite;
import edu.hit.bailexi.domain.Route;

import java.util.List;

public interface FavoriteDao {
    void add(String rid, int uid);


    Favorite findFavouriteByRidAndUid(String rid, int uid);

    int findFavoriteCount(String rid);

    void removeFavourite(String rid, int uid);

    int findTotalCountByUid(int uid);

    List<TabFavourite> findByUid(int uid, int start, int pageSize);

}

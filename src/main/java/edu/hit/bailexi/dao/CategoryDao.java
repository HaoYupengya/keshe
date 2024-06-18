package edu.hit.bailexi.dao;
import edu.hit.bailexi.domain.Category;

import java.util.List;

public interface CategoryDao {

    public List<Category> findAll();
    public Category findByCid(int cid);
}

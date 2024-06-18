package edu.hit.bailexi.service.impl;

import edu.hit.bailexi.domain.Category;
import edu.hit.bailexi.dao.CategoryDao;
import edu.hit.bailexi.dao.impl.CategoryDaoImpl;
import edu.hit.bailexi.service.CategoryService;

import java.util.Comparator;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        List<Category> cs = categoryDao.findAll();
        cs.sort(Comparator.comparingInt(Category::getCid));
        return cs;
    }
}

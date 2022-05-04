package org.java.shopping.service;

import org.java.shopping.entity.Category;
import org.java.shopping.enums.ShoppingExceptionEnum;
import org.java.shopping.exception.ShoppingException;
import org.java.shopping.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 根据商品上一级的类别，查询该商品属于什么分类
     * @param pid 该商品类型的父级
     * @return
     */
    public List<Category> selectByPid(int pid){
        //创建一个category对象，封装查询条件
        Category category = new Category();
        //设置上级查询id
        category.setParentId(pid);

        List<Category> categoryList = categoryMapper.select(category);

        //如果找不到对应的商品类型，抛出异常
        //判断List集合是否为空时，可以使用CollectionsUtils.IsEmpty()方法
        if(CollectionUtils.isEmpty(categoryList)){
            //抛出自定义异常
            throw new ShoppingException(ShoppingExceptionEnum.CATEGORY_LIST_NOT_FOUND);
        }
        return categoryList;
    }

    /**
     * 查询所有
     * @return
     */
    public List<Category> selectAll(){

        List<Category> categoryList = categoryMapper.selectAll();

        return categoryList;
    }
}

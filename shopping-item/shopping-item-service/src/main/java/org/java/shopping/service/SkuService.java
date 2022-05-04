package org.java.shopping.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.java.shopping.entity.Sku;
import org.java.shopping.enums.ShoppingExceptionEnum;
import org.java.shopping.exception.ShoppingException;
import org.java.shopping.mapper.SkuMapper;
import org.java.shopping.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

@Service
public class SkuService {

    @Autowired
    private SkuMapper skuMapper;

    /**
     * 加载全部商品信息
     * @param page 分页工具所需的参数，代表当前页数
     * @param limit 分页工具所需的参数，代表每页显示多少条
     * @return PageResult<Sku> 全部的商品数据信息
     */
    public PageResult<Sku> loadSku(Integer page,Integer limit){

        //设置起始页与每页显示多少条
        PageHelper.startPage(page,limit);
        //将查询的数据赋给PageInfo
        PageInfo<Sku> pageInfo = new PageInfo(skuMapper.selectAll());
        //将Info的数据交给List
        List list = pageInfo.getList();
        //检查List是否为空，若为空则抛出异常
        if(CollectionUtils.isEmpty(list)){
            throw new ShoppingException(ShoppingExceptionEnum.SKU_LIST_NOT_FOUND);
        }
        //不为空则创建PageResult对象，并将其返回出去
        PageResult<Sku> pageResult = new PageResult<>();
        pageResult.setData(list);
        pageResult.setCode(0);
        pageResult.setCount(pageInfo.getTotal());
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setMaxPage(pageInfo.getPages());


        return pageResult;
    }

    /**
     * 根据tiele，商品的关键字来查询对应的商品集合
     * @param page 当前是第几页
     * @param limit 一页显示多少条
     * @param title 查询条件
     * @return
     */
    public PageResult<Sku> getListByTitle(Integer page,Integer limit,String title){


        //创建一个Example对象,用于封装查询条件
        Example example =new Example(Sku.class);
        //通过Criteria来封装查询条件
        Example.Criteria criteria = example.createCriteria();
        //如果title不为空，则将查询条件注入
        if(!StringUtil.isEmpty(title)){
            criteria.andLike("title","%"+title+"%");
        }
        //设置起始页与每页显示多少条
        PageHelper.startPage(page,limit);
        //将查询的数据赋给PageInfo
        PageInfo<Sku> pageInfo = new PageInfo(skuMapper.selectByExample(example));
        //将Info的数据交给Listi
        List list = pageInfo.getList();
        //检查List是否为空，若为空则抛出异常
        if(CollectionUtils.isEmpty(list)){
            throw new ShoppingException(ShoppingExceptionEnum.SKU_LIST_NOT_FOUND);
        }
        //不为空则创建PageResult对象，并将其返回出去
        PageResult<Sku> pageResult = new PageResult<>();
        pageResult.setData(list);
        pageResult.setCode(0);
        pageResult.setCount(pageInfo.getTotal());
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setMaxPage(pageInfo.getPages());


        return pageResult;
    }

    /**
     * 向数据库添加Sku的方法
     * @param sku 从前端返回的sku对象
     */
    @Transactional
    public void saveSku(Sku sku){

        int count =skuMapper.insert(sku);

        //如果返回0，说明添加失败
        if(count==0){
            throw new ShoppingException(ShoppingExceptionEnum.SKU_ADD_ERROR);
        }

    }

    /**
     * 删除的方法
     * @param id
     */
    public void delSkuById(Long id){

        int count = skuMapper.deleteByPrimaryKey(id);

        //如果返回0，说明删除失败
        if(count==0){
            throw new ShoppingException(ShoppingExceptionEnum.SKU_REMOVE_ERROR);
        }

    }


    /**
     * 根据id查询显示商品的详细信息
     * @param id
     * @return
     */
    public Sku loadSkuDetail(Long id){

        Sku sku =skuMapper.selectByPrimaryKey(id);

        return sku;
    }


}

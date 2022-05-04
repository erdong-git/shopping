package org.java.shopping.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.java.shopping.entity.Brand;
import org.java.shopping.enums.ShoppingExceptionEnum;
import org.java.shopping.exception.ShoppingException;
import org.java.shopping.mapper.BrandMapper;
import org.java.shopping.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;

    /**
     * 加载全部品牌
     * @param page  当前页
     * @param limit 分页显示的数量
     * @return
     */
    public PageResult<Brand> loadBrand(Integer page , Integer limit){

        //调用PageHelper的Start方法来设置当前页与每页需要显示的数量
        PageHelper.startPage(page,limit);
        //使用Pageinfo方法来创建一个新的Pageinfo，并将查询的数据赋给它
        PageInfo<Brand> info = new PageInfo<>(brandMapper.selectAll());
        //创建一个List，获取Info的GetList结果
        List list = info.getList();
        //判断该List是否为空
        if(CollectionUtils.isEmpty(list)){
            throw new ShoppingException(ShoppingExceptionEnum.BRAND_LIST_NOT_FOUND);
        }
        //不为空则创建PageResult对象，并将其返回出去
        PageResult<Brand> pageResult = new PageResult<>();
        pageResult.setData(list);
        pageResult.setCode(0);
        pageResult.setCount(info.getTotal());

        return pageResult;
    }


    /**
     * 根据ID删除一条数据，如果删除成功则不做返回，删除失败则抛出异常
     * @return
     */
    public void delBrandById(Integer id){

        int count = brandMapper.deleteByPrimaryKey(id);

        if(count==0){
            //删除失败
            throw new ShoppingException(ShoppingExceptionEnum.BRAND_REMOVE_ERROR);
        }
    }

    @Transactional
    public void saveBrand(Brand brand){
        //insertSelective 将对象中的非空属性，赋值到数据库表中
        //insert 将对象中的所有属性赋值到数据库表中

        int count =brandMapper.insertSelective(brand);

        if(count==0){
                throw new ShoppingException(ShoppingExceptionEnum.BRAND_ADD_ERROR);
        }
    }

}

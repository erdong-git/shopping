package org.java.shopping.mapper;

import org.java.shopping.entity.Brand;
import tk.mybatis.mapper.common.Mapper;

//我们已经将Interface的坐标引进，所以可以直接使用Interface中的类
public interface BrandMapper extends Mapper<Brand> {
}

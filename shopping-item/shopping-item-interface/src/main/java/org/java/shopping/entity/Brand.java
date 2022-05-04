package org.java.shopping.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name  ="tb_brand")
//当类名与数据库的名称不一致时，需要添加此备注，将该实体类与数据库的表形成映射关系
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Brand implements Serializable {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id; //品牌编号
    private String name; //品牌名称
    private String image; //品牌图片地址
    private String letter; //品牌首字母
}

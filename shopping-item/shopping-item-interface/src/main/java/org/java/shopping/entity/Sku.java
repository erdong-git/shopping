package org.java.shopping.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_sku")
public class Sku implements Serializable {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id; //商品编号
    private Integer spuId=1; //商品明细,暂时不涉及此功能,给予默认值。
    private String title; //商品标题
    private String images; //商品图片
    private Long price; //商品价格,使用分为单位,使用时需要/100
    private String indexes=""; //商品规格,暂时不涉及此功能,给予默认值。
    private String ownSpec; //商品参数
    private Integer enable=1;//商品是否上架 1：上架 0：未上架
    private Date createTime; //商品创建的时间
    private Date lastUpdateTime; //商品最后修改的时间



}

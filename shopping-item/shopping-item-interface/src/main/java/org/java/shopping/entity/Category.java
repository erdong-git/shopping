package org.java.shopping.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * Serializable是一个标识，告知JVM，这个类我不做序列化，你替我做序列化
 * 当我们需要把对象的状态信息通过网络进行传输，
 * 或者需要将对象的状态信息持久化，以便将来使用时都需要把对象进行序列化
 * 序列化是将对象状态转换为可保持或传输的格式的过程，成为可以传输的格式的过程也称为流，
 * 将流转换为对象称为反序列化
 */
@Table(name = "tb_category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category implements Serializable {
    private Integer id;//商品类型编号
    private String name; //商品类型名称
    private Integer parentId; //所属上一级元素的编号
    private Integer isParent; //当前是父节点，0为否，1为是
    private Integer sort; //排序
}

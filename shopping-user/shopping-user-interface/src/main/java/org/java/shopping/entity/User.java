package org.java.shopping.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_user")

/**
 * tb_user表对应的实体类
 */
public class User implements Serializable {
    /**
     * 问题：如果其他微服务调用方法获得user，user对象的数据将会序列化，转换成json返回,但，密码，salt都是敏感数据，
     * 在序列化成json时，这些敏感信息，都不应该 进行序列化
     */

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id; //用户编号

    @Length(min = 2 ,max =20,message = "姓名的长度必须在2-20位之间")
    private String username; //用户名

    @JsonIgnore //在序列化时，忽略该属性
    @Length(min=4,max=12,message = "密码的长度必须在4-12位之间")
    private String password; //用户密码

    @Pattern(regexp = "^1[356789]\\d{9}$",message = "手机号码格式错误")
    private String phone; //用户手机
    private Date created; //用户创建时间

    @JsonIgnore//在序列化时，忽略该属性
    private String salt; //加密使用的盐


}

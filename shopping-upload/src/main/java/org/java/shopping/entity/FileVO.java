package org.java.shopping.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileVO {

    private int code; //状态码 成功为1；失败为0
    private String msg; //返回的信息
    private String dataFilePath; //文件的保存地址

}

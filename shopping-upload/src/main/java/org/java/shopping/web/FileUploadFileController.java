package org.java.shopping.web;


import com.netflix.ribbon.proxy.annotation.Http;
import org.csource.fastdfs.*;
import org.java.shopping.entity.FileVO;
import org.csource.common.NameValuePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RequestMapping("/upload")
@RestController

public class FileUploadFileController {

    //获取服务器端储存的地址
    @Value("${up.file-path}")
    private String path;


    @RequestMapping("/img")
    public ResponseEntity<FileVO> uploadbyimg(MultipartFile file) throws IOException {

        //获取上传的文件名
        String fileName =file.getOriginalFilename();

        //通过截取.后面的字符串，获取文件的类型
        String fileType = fileName.substring(fileName.lastIndexOf(".")+1);

        //为了防止服务器端文件重名，使用uuid为文件命名。
        String newFileName = UUID.randomUUID()+"."+fileType;

        //FastDfs上传需要先将文件保存至本地，再把本地的文件上传到FastDfs
        File newFile = new File(path,newFileName);
        file.transferTo(newFile);

        //定义变量，用于保存，存放到fastdfs中的id名称
        String fileId="";

        try {

            //加载fastdfs配置文件
            ClientGlobal.initByProperties("conf/fastdfs-client.properties");
            System.out.println("network_timeout=" + ClientGlobal.g_network_timeout + "ms");
            System.out.println("charset=" + ClientGlobal.g_charset);

            //创建tracker客户端
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();

            //创建storage客户端
            StorageServer storageServer = null;
            StorageClient1 client = new StorageClient1(trackerServer, storageServer);

            //配置原信息（上传文件的原始信息 ）
            NameValuePair[] metaList = new NameValuePair[1];
            //fileName:上传的文件的名称，使用UUID拼接的文件名
            metaList[0] = new NameValuePair("fileName", newFileName);
            //newFile.getAbsolutePath()用于获得上传的文件，传到FastDfs的位置
            //获取上传文件的类型，以及使用UUID拼接的文件名
            fileId = client.upload_file1(newFile.getAbsolutePath(), fileType, metaList);
            //上传成功后，返回当前文件在FastDfs的文件位置 fileId：当前文件位置
            System.out.println("upload success. file id is: " + fileId);

            //仅通过地址无法访问到图片,还需要加上FastDfs主机的ip,
            //一般是通过域名访问,所以通过SwitchHosts来将主机Ip替换为域名

            //关闭tracker
            trackerServer.close();

            //创建fileVO对象来返回信息
            FileVO fileVO = new FileVO();
            fileVO.setCode(1); //代表上传成功
            fileVO.setMsg("上传成功");
            fileVO.setDataFilePath("http://img.shopping.com/"+fileId);

            return ResponseEntity.status(HttpStatus.OK).body(fileVO);

        } catch (Exception ex) {

            FileVO fileVO = new FileVO();
            fileVO.setCode(0); //代表上传成功
            fileVO.setMsg("失败");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fileVO);
        }

    }

       /**本地上传的方法
        *  //获取图片的名称
        String fname = file.getOriginalFilename();

        //在上传地址创建一个File文件，但此时是一个空文件，但名称是图片上传时的名称
        File newFile = new File(path,fname);

        //判断当前路径是否存在，不存在就创建
        if(!newFile.getParentFile().exists()){

            newFile.getParentFile().mkdirs();
        }
        //封装FileVO对象，返回上传的结果
        FileVO fileVO = new FileVO();

        try {
            file.transferTo(newFile);
            fileVO.setCode(1); //设置状态码
            fileVO.setMsg("上传成功");
            fileVO.setDataFilePath(newFile.getAbsolutePath());//获得上传的路径

            return ResponseEntity.status(HttpStatus.OK).body(fileVO);
        } catch (IOException e) {
            e.printStackTrace();
            fileVO.setCode(0); //设置状态码
            fileVO.setMsg("上传失败");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fileVO);
        }


    }
        */

}

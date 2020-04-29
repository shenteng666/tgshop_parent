package com.bigdata.controller;

import com.bigdata.mypojo.Result;
import com.bigdata.util.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author caowei
 * @date 2019/1/27 14:17
 * @description
 */

@RestController
@RequestMapping("upload")
public class UploadFileController {

    @Value("${FILE_SERVER_URL}")
    private String FILE_SERVER_URL;
    @Value("${CONF_PATH}")
    private String CONF_PATH;

    //http://localhost:9102/upload/uploadFile.do
    //file
    //result

    @RequestMapping("uploadFile")
    public Result uploadFile(MultipartFile file){
        try {
            FastDFSClient client = new FastDFSClient(CONF_PATH);
            String path = client.uploadFile(file.getBytes(), file.getOriginalFilename(), file.getSize());
            String url = FILE_SERVER_URL + path;
            System.out.println("文件上传成功后的url"+url);
            return new Result(true,url);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"上传失败");
        }
    }
}
















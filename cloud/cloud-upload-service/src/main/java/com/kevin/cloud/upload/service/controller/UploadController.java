package com.kevin.cloud.upload.service.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.kevin.cloud.platform.dto.ResponseResult;
import com.kevin.cloud.upload.feign.dto.FileInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传服务
 * <p>
 */
@RestController
@RequestMapping(value = "upload")
public class UploadController {

    private static final String ENDPOINT = "oss-cn-beijing.aliyuncs.com";
    private static final String ACCESS_KEY_ID = "LTAITt8cJnR46yLg";
    private static final String ACCESS_KEY_SECRET = "Mfd3WTFllGEkwooYFxbpSyai5gImcj";
    private static final String BUCKET_NAME = "kevin-cloud-dubbo";

    /**
     * 文件上传
     *
     * @param multipartFile @{code MultipartFile}
     * @return {@link ResponseResult<FileInfo>} 文件上传路径
     */
    @PostMapping(value = "doUpload")
    public ResponseResult<FileInfo> upload(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String newName = UUID.randomUUID() + "." + suffix;

        OSS client = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);

        try {
            client.putObject(new PutObjectRequest(BUCKET_NAME, newName, new ByteArrayInputStream(multipartFile.getBytes())));
            // 上传文件路径 = http://BUCKET_NAME.ENDPOINT/自定义路径/fileName
            return new ResponseResult<FileInfo>(ResponseResult.CodeStatus.OK, "文件上传成功", new FileInfo("http://" + BUCKET_NAME + "." + ENDPOINT + "/" + newName));
        } catch (IOException e) {
            return new ResponseResult<FileInfo>(ResponseResult.CodeStatus.FAIL, "文件上传失败，请重试");
        } finally {
            client.shutdown();
        }
    }

}

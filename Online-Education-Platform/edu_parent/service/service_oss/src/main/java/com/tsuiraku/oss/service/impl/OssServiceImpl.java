package com.tsuiraku.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.tsuiraku.oss.service.OssService;
import com.tsuiraku.oss.utils.ConstantPropertiesUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    /**
     * @Author: tsuiraku
     * @Date: 2021/8/28 上午12:09
     * @Description: 上传头像
     */
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // 工具类获取值
        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try {
            // 创建OSS实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 获取上传文件输入流
            InputStream inputStream = file.getInputStream();

            // 获取文件名称
            String fileName = file.getOriginalFilename();

            // 在文件名称里面添加随机唯一的值 UUID
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");

            fileName = uuid + fileName;

            // 上传至 /edu/avatar
            fileName = "edu/image/" + fileName;


            /*
             *   bucketName：Bucket名称
             *   fileName：上传到oss文件路径和文件名称
             *   inputStream：上传文件输入流
             */
            ossClient.putObject(bucketName, fileName, inputStream);

            // 关闭OSSClient
            ossClient.shutdown();

            // 返回路径
            String url = "https://" + bucketName + "." + endpoint + "/" + fileName;

            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

package com.tsuiraku.oss.controller;

import com.tsuiraku.oss.service.OssService;
import com.tsuiraku.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "上传文件至OSS")
@RestController
@CrossOrigin
@RequestMapping("/eduoss")
public class OssController {
    @Autowired
    private OssService ossService;

    @ApiOperation("上传头像")
    @PostMapping("avatarUpload")
    public R uploadAvatar(MultipartFile file) {
        String url = ossService.uploadFileAvatar(file);
        return R.success().msg("success").data("url",url);
    }
}

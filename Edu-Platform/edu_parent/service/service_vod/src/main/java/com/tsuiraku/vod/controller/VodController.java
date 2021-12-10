package com.tsuiraku.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.tsuiraku.servicebase.exceptionhandler.MyException;
import com.tsuiraku.utils.R;
import com.tsuiraku.vod.service.VodService;
import com.tsuiraku.vod.utils.ConstantPropertiesUtils;
import com.tsuiraku.vod.utils.InitVodClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags = "课程视频模块")
@RestController
@RequestMapping("eduvod/video")
@CrossOrigin
public class VodController {
    @Autowired
    private VodService vodService;

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/9/4 下午5:39
     *  @Description: 上传视频至阿里云
     */
    @ApiOperation("上传视频")
    @PostMapping("uploadVideo")
    public R uploadVideo(MultipartFile file) {
        String videoId = vodService.uploadVideoToAly(file);
        return R.success().msg("success").data("videoId", videoId);
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/9/5 上午12:04
     *  @Description: 根据视频id删除视频
     */
    @ApiOperation("删除视频")
    @DeleteMapping("removeVideoById/{id}")
    public R removeVideoById(@PathVariable String id ) {
        vodService.removeVideoById(id);
        return R.success().msg("success");
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/9/5 上午12:04
     *  @Description: 删除某个课程下所有视频
     */
    @ApiOperation("删除多个视频")
    @DeleteMapping("deleteVideoBatch")
    public R deleteVideoBatch(@RequestParam("videoList") List<String> videoList) {
        vodService.removeVideoByBatch(videoList);
        return R.success().msg("success");
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/11/12 上午10:24
     *  @Description: 根据视频id获取视频凭证
     */
    @ApiOperation("根据视频id获取凭证")
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id) {
        try {
            DefaultAcsClient client = InitVodClient.initVodClient("LTAI5tKMopgA33oS3WXX5o5v", "xovYkSalvMxlP3Mk66VTYiuP2sOpF5");

//            DefaultAcsClient client = InitVodClient.initVodClient(ConstantPropertiesUtils.ACCESS_KEY_ID, ConstantPropertiesUtils.ACCESS_KEY_SECRET);

            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

            request.setVideoId(id); // 转码加密的视频
            response = client.getAcsResponse(request);
            String auth = response.getPlayAuth();
            System.out.println("playAuth:" + response.getPlayAuth());
            return R.success().data("auth", auth);

        } catch (ClientException e) {
            throw new MyException(20001, "获取凭证失败");
        }
    }
}

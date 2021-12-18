package com.tsuiraku.vod;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.tsuiraku.vod.utils.ConstantPropertiesUtils;

import java.util.List;

public class VodTest {
    public static void main(String[] args) throws Exception {
//        VodTest.getPlayUrl();
        System.out.println("===============================================================");
        VodTest.getPlayAuth();
    }
    /**
     *  @Author: tsuiraku
     *  @Date: 2021/9/4 下午9:11
     *  @Description: 根据视频iD获取视频播放地址
     */
    public static void getPlayUrl() throws Exception {
        // 创建初始化对象
        DefaultAcsClient client = InitVodClient.initVodClient("LTAI5tKMopgA33oS3WXX5o5v", "xovYkSalvMxlP3Mk66VTYiuP2sOpF5");

        // 创建获取视频地址request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response;

        // 向request对象里面设置视频id
        request.setVideoId("610879b5732f45c994d4c0a6c8b81550");

        // 调用初始化对象里面的方法，传递request，获取数据
        response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        // 播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        // Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }
    /**
     *  @Author: tsuiraku
     *  @Date: 2021/9/4 下午9:54
     *  @Description: 根据视频iD获取视频播放凭证
     */
    public static void getPlayAuth() throws Exception{
        DefaultAcsClient client = InitVodClient.initVodClient("LTAI5tKMopgA33oS3WXX5o5v", "xovYkSalvMxlP3Mk66VTYiuP2sOpF5");

        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        request.setVideoId("a48a401205784830b4a83233e51f49d9"); // 转码加密的视频

        response = client.getAcsResponse(request);
        System.out.println("playAuth:"+response.getPlayAuth());
    }
}

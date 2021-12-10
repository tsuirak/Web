import com.alibaba.fastjson.JSONObject;
import com.aliyun.vod.upload.impl.UploadAttachedMediaImpl;
import com.aliyun.vod.upload.impl.UploadImageImpl;
import com.aliyun.vod.upload.impl.UploadM3u8FileImpl;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.*;
import com.aliyun.vod.upload.resp.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


/**
 * 以下Java示例代码演示了如何在服务端上传媒资文件至视频点播，媒资类型支持音频、视频和图片。
 * <p>
 * 一、音视频上传目前支持4种方式上传：
 *
 * 1.上传本地文件，使用分片上传，并支持断点续传，参见testUploadVideo函数。
 * 1.1 当断点续传关闭时，最大支持上传任务执行时间为3000秒，具体可上传文件大小与您的网络带宽及磁盘读写能力有关。
 * 1.2 当断点续传开启时，最大支持48.8TB的单个文件，注意，断点续传开启后，上传任务执行过程中，同时会将当前上传位置写入本地磁盘文件，影响您上传文件的速度，请您根据文件大小选择是否开启
 *
 * 2.上传网络流，可指定文件URL进行上传，支持断点续传，最大支持48.8TB的单个文件。
 *   该上传方式需要先将网络文件下载到本地磁盘，再进行上传，所以要保证本地磁盘有充足的空间。参见testUploadURLStream函数。
 *
 * 3.上传文件流，可指定本地文件进行上传，不支持断点续传，最大支持5GB的单个文件。参见testUploadFileStream函数。
 *
 * 4.流式上传，可指定输入流进行上传，支持文件流和网络流等，不支持断点续传，最大支持5GB的单个文件。参见testUploadStream函数。
 * <p>
 *
 * 二、图片上传目前支持2种方式上传：
 * 1.上传本地文件，不支持断点续传，最大支持5GB的单个文件，参见testUploadImageLocalFile函数
 * 2.上传文件流和网络流，InputStream参数必选，不支持断点续传，最大支持5GB的单个文件。参见testUploadImageStream函数。
 * 注：图片上传完成后，会返回图片ID和图片地址，也可通过GetImageInfo查询图片信息，参见接口文档 https://help.aliyun.com/document_detail/89742.html
 * <p>
 *
 * 三、m3u8文件上传目前支持2种方式：
 * 1.上传本地m3u8音视频文件（包括所有分片文件）到点播，需指定本地m3u8索引文件地址和所有分片地址。
 * 2.上传网络m3u8音视频文件（包括所有分片文件）到点播，需指定m3u8索引文件和分片文件的URL地址。
 *
 * 注：
 * 1) 上传网络m3u8音视频文件时需要保证地址可访问，如果有权限限制，请设置带签名信息的地址，且保证足够长的有效期，防止地址无法访问导致上传失败
 * 2) m3u8文件上传暂不支持进度回调
 * <p>
 *
 * 四、上传进度回调通知：
 * 1.默认上传进度回调函数：视频点播上传SDK内部默认开启上传进度回调函数，输出不同事件通知的日志，您可以设置关闭该上传进度通知及日志输出；
 * 2.自定义上传进度回调函数：您可根据自已的业务场景重新定义不同事件处理的方式，只需要修改上传回调示例函数即可。
 * <p>
 *
 * 五、辅助媒资上传目前支持2种方式：
 * 1.上传本地文件，不支持断点续传，最大支持5GB的单个文件，参见testUploadAttachedMediaLocalFile函数
 * 2.上传文件流和网络流，InputStream参数必选，不支持断点续传，最大支持5GB的单个文件。参见testUploadAttachedMediaStream函数。
 * <p>
 *
 * 六、支持STS方式上传：
 * 1.您需要实现VoDRefreshSTSTokenListener接口的onRefreshSTSToken方法，用于生成STS信息，
 * 当文件上传时间超过STS过期时间时，SDK内部会定期调用此方法刷新您的STS信息进行后续文件的上传。
 * <p>
 *
 * 七、可指定上传脚本部署的ECS区域(设置Request的EcsRegionId参数，取值参考存储区域标识：https://help.aliyun.com/document_detail/98194.html)，
 * 如果与点播存储（OSS）区域相同，则自动使用内网上传文件至存储，上传更快且更省公网流量
 * 由于点播API只提供外网域名访问，因此部署上传脚本的ECS服务器必须具有访问外网的权限。
 *
 * 注意：
 * 请替换示例中的必选参数，示例中的可选参数如果您不需要设置，请将其删除，以免设置无效参数值与您的预期不符。
 */

public class UploadVideoDemo {
    //账号AK信息请填写(必选)
    private static final String accessKeyId = "<Your accessKeyId>";
    //账号AK信息请填写(必选)
    private static final String accessKeySecret = "<Your accessKeySecret>";

    public static void main(String[] args) {
        // 一、视频文件上传
        // 视频标题(必选)
        String title = "测试标题";
        // 1.本地文件上传和文件流上传时，文件名称为上传文件绝对路径，如:/User/sample/文件名称.mp4 (必选)
        // 2.网络流上传时，文件名称为源文件名，如文件名称.mp4(必选)。
        // 3.流式上传时，文件名称为源文件名，如文件名称.mp4(必选)。
        // 任何上传方式文件名必须包含扩展名
        String fileName = "/Users/test/video/test.mp4";
        // 本地文件上传
        testUploadVideo(accessKeyId, accessKeySecret, title, fileName);

        // 待上传视频的网络流地址
        String url = "http://test.aliyun.com/video/test.mp4";

        // 2.网络流上传
        // 文件扩展名，当url中不包含扩展名时，需要设置该参数
        String fileExtension = "mp4";
        testUploadURLStream(accessKeyId, accessKeySecret, title, url, fileExtension);

        // 3.文件流上传
        // testUploadFileStream(accessKeyId, accessKeySecret, title, fileName);

        // 4.流式上传，如文件流和网络流
        InputStream inputStream = null;
        // 4.1 文件流
        try {
            inputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 4.2 网络流
        try {
            inputStream = new URL(url).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        testUploadStream(accessKeyId, accessKeySecret, title, fileName, inputStream);

        // 二、图片上传

        // 1.图片上传-本地文件上传
        // testUploadImageLocalFile(accessKeyId, accessKeySecret);

        // 2.图片上传-流式上传(文件流和网络流)
        // testUploadImageStream(accessKeyId, accessKeySecret);

        // 三、m3u8文件上传

        // 1.上传本地m3u8音视频文件
         testUploadLocalM3u8(accessKeyId, accessKeySecret);
        // 2.上传网络m3u8音视频文件
         testUploadWebM3u8(accessKeyId, accessKeySecret);
        // 四、辅助媒资上传
        // 辅助媒资-本地文件上传
//        testUploadAttachedMediaLocalFile(accessKeyId, accessKeySecret);
        // 辅助媒资-流式上传(文件流和网络流)
//        testUploadAttachedMediaStream(accessKeyId, accessKeySecret);
    }

    /**
     * 本地文件上传接口
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @param title
     * @param fileName
     */
    private static void testUploadVideo(String accessKeyId, String accessKeySecret, String title, String fileName) {
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);
        /* 是否开启断点续传, 默认断点续传功能关闭。当网络不稳定或者程序崩溃时，再次发起相同上传请求，可以继续未完成的上传任务，适用于超时3000秒仍不能上传完成的大文件。
        注意: 断点续传开启后，会在上传过程中将上传位置写入本地磁盘文件，影响文件上传速度，请您根据实际情况选择是否开启*/
        //request.setEnableCheckpoint(false);
        /* OSS慢请求日志打印超时时间，是指每个分片上传时间超过该阈值时会打印debug日志，如果想屏蔽此日志，请调整该阈值。单位: 毫秒，默认为300000毫秒*/
        //request.setSlowRequestsThreshold(300000L);
        /* 可指定每个分片慢请求时打印日志的时间阈值，默认为300s*/
        //request.setSlowRequestsThreshold(300000L);
        /* 是否显示水印(可选)，指定模板组ID时，根据模板组配置确定是否显示水印*/
        //request.setIsShowWaterMark(true);
        /* 自定义消息回调设置(可选)，参数说明参考文档 https://help.aliyun.com/document_detail/86952.html#UserData */
        // request.setUserData("{\"Extend\":{\"test\":\"www\",\"localId\":\"xxxx\"},\"MessageCallback\":{\"CallbackURL\":\"http://test.test.com\"}}");
        /* 视频分类ID(可选) */
        //request.setCateId(0);
        /* 视频标签,多个用逗号分隔(可选) */
        //request.setTags("标签1,标签2");
        /* 视频描述(可选) */
        //request.setDescription("视频描述");
        /* 封面图片(可选) */
        //request.setCoverURL("http://cover.sample.com/sample.jpg");
        /* 模板组ID(可选) */
        //request.setTemplateGroupId("8c4792cbc8694e7084fd5330e56a33d");
        /* 工作流ID(可选) */
        //request.setWorkflowId("d4430d07361f0*be1339577859b0177b");
        /* 存储区域(可选) */
        //request.setStorageLocation("in-201703232118266-5sejdln9o.oss-cn-shanghai.aliyuncs.com");
        /* 开启默认上传进度回调 */
        //request.setPrintProgress(false);
        /* 设置自定义上传进度回调 (必须继承 VoDProgressListener) */
        //request.setProgressListener(new PutObjectProgressListener());
        /* 设置您实现的生成STS信息的接口实现类*/
        // request.setVoDRefreshSTSTokenListener(new RefreshSTSTokenImpl());
        /* 设置应用ID*/
        //request.setAppId("app-1000000");
        /* 点播服务接入点 */
        //request.setApiRegionId("cn-shanghai");
        /* ECS部署区域*/
        // request.setEcsRegionId("cn-shanghai");
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    /**
     * URL网络流上传。支持断点续传，最大支持48.8TB的单个文件。
     * 该上传方式需要先将网络文件下载到本地磁盘，再进行上传，所以要保证本地磁盘有充足的空间。
     * 当您设置的URL中不包括文件扩展名时，需要单独设置fileExtension，表示文件扩展名。
     * @param accessKeyId
     * @param accessKeySecret
     * @param title
     * @param fileName
     * @param url
     */
    private static void testUploadURLStream(String accessKeyId, String accessKeySecret, String title, String url, String fileExtension) {
        UploadURLStreamRequest request = new UploadURLStreamRequest(accessKeyId, accessKeySecret, title, url);

        /* 文件扩展名*/
        request.setFileExtension(fileExtension);
        /* 网络文件下载连接超时，单位毫秒，0-表示不限制*/
        request.setDownloadConnectTimeout(1000);
        /* 网络文件下载读取超时，单位毫秒，0-表示不限制*/
        request.setDownloadReadTimeout(0);
        /* 网络文件下载后保存的本地目录*/
        request.setLocalDownloadFilePath("/Users/download");
        /* 是否显示水印(可选)，指定模板组ID时，根据模板组配置确定是否显示水印*/
        //request.setShowWaterMark(true);
        /* 自定义消息回调设置(可选)，参数说明参考文档 https://help.aliyun.com/document_detail/86952.html#UserData */
        // request.setUserData("{\"Extend\":{\"test\":\"www\",\"localId\":\"xxxx\"},\"MessageCallback\":{\"CallbackURL\":\"http://test.test.com\"}}");
        /* 视频分类ID(可选) */
        //request.setCateId(0);
        /* 视频标签,多个用逗号分隔(可选) */
        //request.setTags("标签1,标签2");
        /* 视频描述(可选) */
        //request.setDescription("视频描述");
        /* 封面图片(可选) */
        //request.setCoverURL("http://cover.sample.com/sample.jpg");
        /* 模板组ID(可选) */
        //request.setTemplateGroupId("8c4792cbc8694e7084fd5330e56a33d");
        /* 工作流ID(可选) */
        //request.setWorkflowId("d4430d07361f0*be1339577859b0177b");
        /* 存储区域(可选) */
        //request.setStorageLocation("in-201703232118266-5sejdln9o.oss-cn-shanghai.aliyuncs.com");
        /* 开启默认上传进度回调 */
        //request.setPrintProgress(true);
        /* 设置自定义上传进度回调 (必须继承 VoDProgressListener) */
        //request.setProgressListener(new PutObjectProgressListener());
        /* 设置应用ID*/
        //request.setAppId("app-1000000");
        /* 点播服务接入点 */
        //request.setApiRegionId("cn-shanghai");
        /* ECS部署区域*/
        // request.setEcsRegionId("cn-shanghai");
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadURLStreamResponse response = uploader.uploadURLStream(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n"); //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    /**
     * 文件流上传接口
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @param title
     * @param fileName
     */
    private static void testUploadFileStream(String accessKeyId, String accessKeySecret, String title, String fileName) {
        UploadFileStreamRequest request = new UploadFileStreamRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 是否使用默认水印(可选)，指定模板组ID时，根据模板组配置确定是否使用默认水印*/
        //request.setShowWaterMark(true);
        /* 自定义消息回调设置，参数说明参考文档 https://help.aliyun.com/document_detail/86952.html#UserData */
        //request.setUserData(""{\"Extend\":{\"test\":\"www\",\"localId\":\"xxxx\"},\"MessageCallback\":{\"CallbackURL\":\"http://test.test.com\"}}"");
        /* 视频分类ID(可选) */
        //request.setCateId(0);
        /* 视频标签,多个用逗号分隔(可选) */
        //request.setTags("标签1,标签2");
        /* 视频描述(可选) */
        //request.setDescription("视频描述");
        /* 封面图片(可选) */
        //request.setCoverURL("http://cover.sample.com/sample.jpg");
        /* 模板组ID(可选) */
        //request.setTemplateGroupId("8c4792cbc8694e7084fd5330e56a33d");
        /* 工作流ID(可选) */
        //request.setWorkflowId("d4430d07361f0*be1339577859b0177b");
        /* 存储区域(可选) */
        //request.setStorageLocation("in-201703232118266-5sejdln9o.oss-cn-shanghai.aliyuncs.com");
        /* 开启默认上传进度回调 */
        //request.setPrintProgress(true);
        /* 设置自定义上传进度回调 (必须继承 VoDProgressListener) */
        //request.setProgressListener(new PutObjectProgressListener());
        /* 设置应用ID*/
        //request.setAppId("app-1000000");
        /* 点播服务接入点 */
        //request.setApiRegionId("cn-shanghai");
        /* ECS部署区域*/
        // request.setEcsRegionId("cn-shanghai");
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadFileStreamResponse response = uploader.uploadFileStream(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n"); //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    /**
     * 流式上传接口
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @param title
     * @param fileName
     * @param inputStream
     */
    private static void testUploadStream(String accessKeyId, String accessKeySecret, String title, String fileName, InputStream inputStream) {
        UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);
         /* 是否使用默认水印(可选)，指定模板组ID时，根据模板组配置确定是否使用默认水印*/
        //request.setShowWaterMark(true);
        /* 自定义消息回调设置，参数说明参考文档 https://help.aliyun.com/document_detail/86952.html#UserData */
        //request.setUserData(""{\"Extend\":{\"test\":\"www\",\"localId\":\"xxxx\"},\"MessageCallback\":{\"CallbackURL\":\"http://test.test.com\"}}"");
        /* 视频分类ID(可选) */
        //request.setCateId(0);
        /* 视频标签,多个用逗号分隔(可选) */
        //request.setTags("标签1,标签2");
        /* 视频描述(可选) */
        //request.setDescription("视频描述");
        /* 封面图片(可选) */
        //request.setCoverURL("http://cover.sample.com/sample.jpg");
        /* 模板组ID(可选) */
        //request.setTemplateGroupId("8c4792cbc8694e7084fd5330e56a33d");
        /* 工作流ID(可选) */
        //request.setWorkflowId("d4430d07361f0*be1339577859b0177b");
        /* 存储区域(可选) */
        //request.setStorageLocation("in-201703232118266-5sejdln9o.oss-cn-shanghai.aliyuncs.com");
        /* 开启默认上传进度回调 */
        // request.setPrintProgress(true);
        /* 设置自定义上传进度回调 (必须继承 VoDProgressListener) */
        // request.setProgressListener(new PutObjectProgressListener());
         /* 设置应用ID*/
        //request.setAppId("app-1000000");
        /* 点播服务接入点 */
        //request.setApiRegionId("cn-shanghai");
        /* ECS部署区域*/
        // request.setEcsRegionId("cn-shanghai");
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    /**
     * 图片上传接口，本地文件上传示例
     * 参数参考文档 https://help.aliyun.com/document_detail/55619.html
     *
     * @param accessKeyId
     * @param accessKeySecret
     */
    private static void testUploadImageLocalFile(String accessKeyId, String accessKeySecret) {
        /* 图片类型（必选）取值范围：default（默认)，cover（封面），watermark（水印）*/
        String imageType = "cover";
        UploadImageRequest request = new UploadImageRequest(accessKeyId, accessKeySecret, imageType);
        request.setImageType("cover");
        /* 图片文件扩展名（可选）取值范围：png，jpg，jpeg */
        //request.setImageExt("png");
        /* 图片标题（可选）长度不超过128个字节，UTF8编码 */
        //request.setTitle("图片标题");
        /* 图片标签（可选）单个标签不超过32字节，最多不超过16个标签，多个用逗号分隔，UTF8编码 */
        //request.setTags("标签1,标签2");
        /* 存储区域（可选）*/
        //request.setStorageLocation("out-4f3952f78c0211e8b3020013e7.oss-cn-shanghai.aliyuncs.com");
        /* 流式上传时，InputStream为必选，fileName为源文件名称，如:文件名称.png(可选)*/
        String fileName = "/Users/demo/png/test.png";
        request.setFileName(fileName);
        /* 开启默认上传进度回调 */
        //request.setPrintProgress(false);
        /* 设置自定义上传进度回调 (必须继承 VoDProgressListener) */
        // request.setProgressListener(new PutObjectProgressListener());
        /* 设置应用ID*/
        //request.setAppId("app-1000000");
        /* 点播服务接入点 */
        //request.setApiRegionId("cn-shanghai");
        UploadImageImpl uploadImage = new UploadImageImpl();
        UploadImageResponse response = uploadImage.upload(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");
        if (response.isSuccess()) {
            System.out.print("ImageId=" + response.getImageId() + "\n");
            System.out.print("ImageURL=" + response.getImageURL() + "\n");
        } else {
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }

    }

    /**
     * 图片上传接口，流式上传示例（支持文件流和网络流）
     * 参数参考文档 https://help.aliyun.com/document_detail/55619.html
     *
     * @param accessKeyId
     * @param accessKeySecret
     */
    private static void testUploadImageStream(String accessKeyId, String accessKeySecret) {
         /* 图片类型（必选）取值范围：default（默认)，cover（封面），watermark（水印）*/
        String imageType = "cover";
        UploadImageRequest request = new UploadImageRequest(accessKeyId, accessKeySecret, imageType);
        /* 图片文件扩展名（可选）取值范围：png，jpg，jpeg */
        //request.setImageExt("png");
        /* 图片标题（可选）长度不超过128个字节，UTF8编码 */
        //request.setTitle("图片标题");
        /* 图片标签（可选）单个标签不超过32字节，最多不超过16个标签，多个用逗号分隔，UTF8编码 */
        //request.setTags("标签1,标签2");
        /* 存储区域（可选）*/
        //request.setStorageLocation("out-4f3952f78c0211e8b3020013e7.oss-cn-shanghai.aliyuncs.com");
        /* 流式上传时，InputStream为必选，fileName为源文件名称，如:文件名称.png(可选)*/
        //request.setFileName("测试文件名称.png");
        /* 开启默认上传进度回调 */
        // request.setPrintProgress(true);
        /* 设置自定义上传进度回调 (必须继承 VoDProgressListener) */
        // request.setProgressListener(new PutObjectProgressListener());
        /* 设置应用ID*/
        //request.setAppId("app-1000000");
        // 1.文件流上传
        // InputStream fileStream = getFileStream(request.getFileName());
        // if (fileStream != null) {
        //     request.setInputStream(fileStream);
        // }

        // 2.网络流上传
        String url = "http://test.aliyun.com/image/default/test.png";
        InputStream urlStream = getUrlStream(url);
        if (urlStream != null) {
            request.setInputStream(urlStream);
        }

        // 开始上传图片
        UploadImageImpl uploadImage = new UploadImageImpl();
        UploadImageResponse response = uploadImage.upload(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");
        if (response.isSuccess()) {
            System.out.print("ImageId=" + response.getImageId() + "\n");
            System.out.print("ImageURL=" + response.getImageURL() + "\n");
        } else {
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    /**
     * 上传本地m3u8视频或音频文件到点播，m3u8文件和分片文件默认在同一目录(sliceFilenames为空时，会按照同一目录去解析分片地址)
     *
     * @param request 本地m3u8上传请求类
     *                m3u8Filename: 本地m3u8索引文件的绝对路径，且m3u8文件的分片信息必须是相对路径，不能含有URL或本地绝对路径
     *                sliceFilenames: ts分片文件的绝对路径列表，如指定则以此为准，若不指定，则自动解析 m3u8Filename 里的m3u8文件
     */
    private static void testUploadLocalM3u8(String accessKeyId, String accessKeySecret) {
        String title = "test_upload_local_m3u8";
        String m3u8Filename = "/Users/test/0e9ecfc6da934d1887ed7bdfc925ecf5/cc38da35c7b24de0abe58619cdd7a8-6479a12446b994719838e0307f6458-ld.m3u8";
        UploadLocalM3u8Request request = new UploadLocalM3u8Request(accessKeyId, accessKeySecret, title, m3u8Filename);
        String[] sliceFilenames = new String[]{
                "/Users/test/0e9ecfc6da934d1887ed7bdfc925ecf5/slices/cc38da35c7b24de0abe58619cdd7a896-c45797a1ad6e75fbb9d1a84937034742-ld-00001.ts",
                "/Users/test/0e9ecfc6da934d1887ed7bdfc925ecf5/slices/cc38da35c7b24de0abe58619cdd7a896-c45797a1ad6e75fbb9d1a84937034742-ld-00002.ts",
                "/Users/test/0e9ecfc6da934d1887ed7bdfc925ecf5/slices/cc38da35c7b24de0abe58619cdd7a896-c45797a1ad6e75fbb9d1a84937034742-ld-00003.ts",
                "/Users/test/0e9ecfc6da934d1887ed7bdfc925ecf5/slices/cc38da35c7b24de0abe58619cdd7a896-c45797a1ad6e75fbb9d1a84937034742-ld-00004.ts",
                "/Users/test/0e9ecfc6da934d1887ed7bdfc925ecf5/slices/cc38da35c7b24de0abe58619cdd7a896-c45797a1ad6e75fbb9d1a84937034742-ld-00005.ts"
        };
        // ts分片文件列表，可选，不指定时，直接解析m3u8FileURL获取分片地址
        request.setSliceFilenames(sliceFilenames);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);
        /* 是否显示水印(可选)，指定模板组ID时，根据模板组配置确定是否显示水印*/
        //request.getShowWaterMark(true);
        /* 自定义消息回调设置(可选)，参数说明参考文档 https://help.aliyun.com/document_detail/86952.html#UserData */
        // request.setUserData("{\"Extend\":{\"test\":\"www\",\"localId\":\"xxxx\"},\"MessageCallback\":{\"CallbackURL\":\"http://test.test.com\"}}");
        /* 视频分类ID(可选) */
        //request.setCateId(-1L);
        /* 视频标签,多个用逗号分隔(可选) */
        //request.setTags("标签1,标签2");
        /* 视频描述(可选) */
        //request.setDescription("视频描述");
        /* 封面图片(可选) */
        //request.setCoverURL("http://cover.sample.com/sample.jpg");
        /* 模板组ID(可选) */
        //request.setTemplateGroupId("8c4792cbc8694e7084fd5330e56a33d");
        /* 工作流ID(可选) */
        //request.setWorkflowId("d4430d07361f0*be1339577859b0177b");
        /* 存储区域(可选) */
        //request.setStorageLocation("in-201703232118266-5sejdln9o.oss-cn-shanghai.aliyuncs.com");
        /* 设置应用ID*/
        // request.setAppId("app-1000000");
        /* 点播服务接入点 */
        // request.setApiRegionId("cn-shanghai");
        /* ECS部署区域*/
        // request.setEcsRegionId("cn-shanghai");
        UploadM3u8FileImpl uploadM3u8File = new UploadM3u8FileImpl();
        UploadLocalM3u8Response uploadLocalM3u8Response = uploadM3u8File.uploadLocalM3u8(request);
        System.out.println("code = " + uploadLocalM3u8Response.getCode());
        System.out.println("message = " + uploadLocalM3u8Response.getMessage());
        System.out.println("videoId = " + uploadLocalM3u8Response.getVideoId());
        System.out.println("requestId = " + uploadLocalM3u8Response.getRequestId());
    }

    /**
     * 上传网络m3u8视频或音频文件到点播，需本地磁盘空间足够，会先下载到本地临时目录，再上传到点播存储
     *
     * @param request 网络m3u8上传请求类
     *                m3u8FileURL: 网络m3u8索引文件的URL地址，且m3u8文件的分片信息必须是相对地址，不能含有URL或本地绝对路径
     *                sliceFileURLs: ts分片文件的URL地址列表；需自行拼接ts分片的URL地址列表
     */
    private static void testUploadWebM3u8(String accessKeyId, String accessKeySecret) {
        String title = "test_upload_web_m3u8";
        String m3u8FileURL = "http://test.aliyun.com/f0d644abc547129e957b386f772c72/a0e1e2817ab9425aa558fe67a90e717f-538087dcf2c201c31ce4324bf76af691-ld.m3u8";
        UploadWebM3u8Request request = new UploadWebM3u8Request(accessKeyId, accessKeySecret, title, m3u8FileURL);
        String[] sliceFileURLs = new String[]{
                "http://test.aliyun.com/f0d644abc547129e957b386f772c72/a0e1e2817ab9425aa558fe67a90e717f-822598b9c170a8c6dad985e20cd9c27d-ld-00001.ts",
                "http://test.aliyun.com/f0d644abc547129e957b386f772c72/a0e1e2817ab9425aa558fe67a90e717f-822598b9c170a8c6dad985e20cd9c27d-ld-00002.ts",
                "http://test.aliyun.com/f0d644abc547129e957b386f772c72/a0e1e2817ab9425aa558fe67a90e717f-822598b9c170a8c6dad985e20cd9c27d-ld-00003.ts",
                "http://test.aliyun.com/f0d644abc547129e957b386f772c72/a0e1e2817ab9425aa558fe67a90e717f-822598b9c170a8c6dad985e20cd9c27d-ld-00004.ts",
                "http://test.aliyun.com/f0d644abc547129e957b386f772c72/a0e1e2817ab9425aa558fe67a90e717f-822598b9c170a8c6dad985e20cd9c27d-ld-00005.ts"
        };
        // ts分片地址，可选，不指定时，直接解析m3u8FileURL获取分片地址
        request.setSliceFileURLs(sliceFileURLs);
        /* 下载文件的临时存储目录，可自定义，如不指定则保存到程序所运行的目录下*/
        // request.setGlobalLocalFilePath("/User/download/");
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);
        /* 是否显示水印(可选)，指定模板组ID时，根据模板组配置确定是否显示水印*/
        //request.setShowWaterMark(true);
        /* 自定义消息回调设置(可选)，参数说明参考文档 https://help.aliyun.com/document_detail/86952.html#UserData */
        //request.setUserData("{\"Extend\":{\"test\":\"www\",\"localId\":\"xxxx\"},\"MessageCallback\":{\"CallbackURL\":\"http://test.test.com\"}}");
        /* 视频分类ID(可选) */
        //request.setCateId(-1L);
        /* 视频标签,多个用逗号分隔(可选) */
        //request.setTags("标签1,标签2");
        /* 视频描述(可选) */
        //request.setDescription("视频描述");
        /* 封面图片(可选) */
        //request.setCoverURL("http://cover.sample.com/sample.jpg");
        /* 模板组ID(可选) */
        //request.setTemplateGroupId("8c4792cbc8694e7084fd5330e56a33d");
        /* 工作流ID(可选) */
        //request.setWorkflowId("d4430d07361f0*be1339577859b0177b");
        /* 存储区域(可选) */
        //request.setStorageLocation("in-201703232118266-5sejdln9o.oss-cn-shanghai.aliyuncs.com");
        /* 设置应用ID*/
        //request.setAppId("app-1000000");
        /* 点播服务接入点 */
        //request.setApiRegionId("cn-shanghai");
        /* ECS部署区域*/
        // request.setEcsRegionId("cn-shanghai");
        UploadM3u8FileImpl uploadM3u8File = new UploadM3u8FileImpl();
        UploadWebM3u8Response uploadWebM3u8Response = uploadM3u8File.uploadWebM3u8(request);
        System.out.println("code = " + uploadWebM3u8Response.getCode());
        System.out.println("message = " + uploadWebM3u8Response.getMessage());
        System.out.println("videoId = " + uploadWebM3u8Response.getVideoId());
        System.out.println("requestId = " + uploadWebM3u8Response.getRequestId());
    }

    /**
     * 辅助媒资上传接口，本地文件上传示例
     * 参数参考文档 https://help.aliyun.com/document_detail/98467.html
     *
     * @param accessKeyId
     * @param accessKeySecret
     */
    private static void testUploadAttachedMediaLocalFile(String accessKeyId, String accessKeySecret) {
        /* 业务类型 */
        String businessType = "watermark";
         /* 文件扩展名 */
        String mediaExt = "png";
        String filename = "/Users/demo/png/test.png";
        UploadAttachedMediaRequest request = new UploadAttachedMediaRequest(accessKeyId, accessKeySecret, businessType, mediaExt);
        request.setFileName(filename);
        /* 标题*/
        request.setTitle("test_attached_media");
        /* 分类ID */
        request.setCateId(-1L);
        /* 标签,多个用逗号分隔(可选) */
        request.setTags("tag1,tag2");
        /* 描述(可选) */
        request.setDescription("test_desc");
        /* 存储区域(可选) */
        request.setStorageLocation("out-20170323225118266-5l3hs5gqwa.oss-cn-shanghai.aliyuncs.com");
        /* 点播服务接入点 */
        request.setApiRegionId("cn-shanghai");
        /* 设置应用ID*/
        //request.setAppId("app-1000000");
        UploadAttachedMediaImpl uploader = new UploadAttachedMediaImpl();
        UploadAttachedMediaResponse response = uploader.upload(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");
        if (response.isSuccess()) {
            System.out.print("MediaId=" + response.getMediaId() + "\n");
            System.out.print("MediaURL=" + response.getMediaURL() + "\n");
            System.out.print("FileURL=" + response.getFileURL() + "\n");
        } else {
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }

    }

    /**
     * 辅助媒资上传接口，流式上传示例（支持文件流和网络流）
     * 参数参考文档 https://help.aliyun.com/document_detail/98467.html
     *
     * @param accessKeyId
     * @param accessKeySecret
     */
    private static void testUploadAttachedMediaStream(String accessKeyId, String accessKeySecret) {
          /* 业务类型 */
        String businessType = "watermark";
         /* 文件扩展名 */
        String mediaExt = "png";
        String filename = "http://test.aliyun.com/test.png";
        UploadAttachedMediaRequest request;
        // 1.文件流上传
        InputStream fileStream = getFileStream(filename);
        request = new UploadAttachedMediaRequest(accessKeyId, accessKeySecret, businessType, mediaExt);
        request.setInputStream(fileStream);

        // 2.网络流上传
//        String url = "http://test.aliyun.com/image//test.png";
//        InputStream urlStream = getUrlStream(url);
//        request = new UploadAttachedMediaRequest(accessKeyId, accessKeySecret, businessType, mediaExt);
//        request.setInputStream(urlStream);

        /* 标题*/
        request.setTitle("test_attached_media");
        /* 分类ID */
        request.setCateId(-1L);
        /* 标签,多个用逗号分隔(可选) */
        request.setTags("tag1,tag2");
        /* 描述(可选) */
        request.setDescription("test_desc");
        /* 存储区域(可选) */
        request.setStorageLocation("out-20170323225118266-5l3****wa.oss-cn-shanghai.aliyuncs.com");
        /* 点播服务接入点 */
        request.setApiRegionId("cn-shanghai");
        /* 设置应用ID*/
        // request.setAppId("app-1000000");
        // 开始上传
        UploadAttachedMediaImpl uploader = new UploadAttachedMediaImpl();
        UploadAttachedMediaResponse response = uploader.upload(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");
        if (response.isSuccess()) {
            System.out.print("MediaId=" + response.getMediaId() + "\n");
            System.out.print("MediaURL=" + response.getMediaURL() + "\n");
            System.out.print("FileURL=" + response.getFileURL() + "\n");
        } else {
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    private static InputStream getFileStream(String fileName) {
        try {
            return new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static InputStream getUrlStream(String url) {
        try {
            return new URL(url).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
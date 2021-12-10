import com.aliyun.oss.event.ProgressEvent;
import com.aliyun.oss.event.ProgressEventType;
import com.aliyun.vod.upload.impl.VoDProgressListener;

/**
 * 上传进度回调方法类
 * 当您开启上传进度回调时该事件回调才会生效。
 * OSS分片上传成功或失败均触发相应的回调事件，您可根据业务逻辑处理相应的事件回调。
 * 当创建音视频信息成功后，此上传进度回调中的videoId为本次上传生成的视频ID，您可以根据视频ID进行音视频管理。
 * 当创建图片信息成功后，此上传进度回调中的ImageId为本次上传生成的图片ID，您可以根据视频ID进行图片管理。
 */

public class PutObjectProgressListener implements VoDProgressListener {
    /**
     * 已成功上传至OSS的字节数
     */
    private long bytesWritten = 0;
    /**
     * 原始文件的总字节数
     */
    private long totalBytes = -1;
    /**
     * 本次上传成功标记
     */
    private boolean succeed = false;
    /**
     * 视频ID
     */
    private String videoId;
    /**
     * 图片ID
     */
    private String imageId;

    public void progressChanged(ProgressEvent progressEvent) {
        long bytes = progressEvent.getBytes();
        ProgressEventType eventType = progressEvent.getEventType();
        switch (eventType) {
            // 开始上传事件
            case TRANSFER_STARTED_EVENT:
                if (videoId != null) {
                    System.out.println("qqqqStart to upload videoId " + videoId + "......");
                }
                if (imageId != null) {
                    System.out.println("aaaStart to upload imageId " + imageId + "......");
                }
                break;
            // 计算待上传文件总大小事件通知，只有调用本地文件方式上传时支持该事件
            case REQUEST_CONTENT_LENGTH_EVENT:
                this.totalBytes = bytes;
                System.out.println(this.totalBytes + "bytes in total will be uploaded to OSS.");
                break;
            // 已经上传成功文件大小事件通知
            case REQUEST_BYTE_TRANSFER_EVENT:
                this.bytesWritten += bytes;
                if (this.totalBytes != -1) {
                    int percent = (int) (this.bytesWritten * 100.0 / this.totalBytes);
                    System.out.println(bytes + " bytes have been written at this time, upload progress: " +
                            percent + "%(" + this.bytesWritten + "/" + this.totalBytes + ")");
                } else {
                    System.out.println(bytes + " bytes have been written at this time, upload sub total : " +
                            "(" + this.bytesWritten + ")");
                }
                break;
            // 文件全部上传成功事件通知
            case TRANSFER_COMPLETED_EVENT:
                this.succeed = true;
                if (videoId != null) {
                    System.out.println("Succeed to upload videoId " + videoId + " , " + this.bytesWritten + " bytes have been transferred in total.");
                }
                if (imageId != null) {
                    System.out.println("Succeed to upload imageId " + imageId + " , " + this.bytesWritten + " bytes have been transferred in total.");
                }
                break;
            // 文件上传失败事件通知
            case TRANSFER_FAILED_EVENT:
                if (videoId != null) {
                    System.out.println("Failed to upload videoId " + videoId + " , " + this.bytesWritten + " bytes have been transferred.");
                }
                if (imageId != null) {
                    System.out.println("Failed to upload imageId " + imageId + " , " + this.bytesWritten + " bytes have been transferred.");
                }
                break;

            default:
                break;
        }
    }

    public boolean isSucceed() {
        return succeed;
    }

    public void onVidReady(String videoId) {
        setVideoId(videoId);
    }

    public void onImageIdReady(String imageId) {
        setImageId(imageId);
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
}


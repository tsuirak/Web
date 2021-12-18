import com.aliyun.vod.upload.dto.STSTokenDTO;
import com.aliyun.vod.upload.impl.VoDRefreshSTSTokenListener;

/**
 * @author vod
 * 生成STS信息实现类
 * @date 2019/6/5
 */
public class RefreshSTSTokenImpl implements VoDRefreshSTSTokenListener {

    public STSTokenDTO onRefreshSTSToken() {
        STSTokenDTO stsTokenDTO = new STSTokenDTO();
        stsTokenDTO.setAccessKeyId("<your sts AccessKeyId>");
        stsTokenDTO.setAccessKeySecret("<your sts AccessKeySecret>");
        stsTokenDTO.setSecurityToken("<your sts SecurityToken>");
        return stsTokenDTO;
    }

}

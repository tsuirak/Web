package com.tsuiraku.msmservice.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tencentcloud.sms")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendSmsProperties {
    /* 腾讯云账户密钥对secretId，secretKey */
    private String secretId;
    private String secretKey;

    /* 短信应用ID: 短信SdkAppId在 [短信控制台] 添加应用后生成的实际SdkAppId，示例如1400006666
     *  SDK AppID是短信应用的唯一标识，调用短信API接口时，需要提供该参数 */
    private String sdkAppId;
}

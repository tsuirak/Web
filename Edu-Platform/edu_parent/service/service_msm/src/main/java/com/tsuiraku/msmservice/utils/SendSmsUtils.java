package com.tsuiraku.msmservice.utils;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import com.tsuiraku.msmservice.config.SendSmsProperties;
import com.tsuiraku.msmservice.config.Sms;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 *  @Author: tsuiraku
 *  @Date: 2021/11/4 下午11:02
 *  @Description:  Tencent Cloud Sms Sendsms
 */
@Component
@Configuration
@EnableConfigurationProperties({SendSmsProperties.class})
public class SendSmsUtils {
    private SendSmsProperties sendSms;

    public SendSmsUtils(SendSmsProperties sendSms) {
        this.sendSms = sendSms;
    }

    public boolean send(Sms sms) {
        try {

            System.out.println("SecretId：" + sendSms.getSecretId());
            System.out.println("SecretKey：" + sendSms.getSecretKey());
            System.out.println("SdkAppId：" + sendSms.getSdkAppId());
            System.out.println("SignName：" + sms.getSignName());
            System.out.println("TemplateId：" + sms.getTemplateId());
            System.out.println("PhoneNumber：");
            for (String s : sms.getPhoneNumber()) {
                System.out.println(s);
            }

            /*
             * 实例化一个认证对象，入参需要传入腾讯云账户密钥对secretId，secretKey */
            Credential cred = new Credential(sendSms.getSecretId(), sendSms.getSecretKey());

            /*
             * 实例化一个客户端配置对象，可以指定超时时间等配置 */
            ClientProfile clientProfile = new ClientProfile();

            /* 实例化要请求产品(以sms为例)的client对象
             * 第二个参数是地域信息，可以直接填写字符串ap-guangzhou，或者引用预设的常量 */
            SmsClient client = new SmsClient(cred, "ap-guangzhou", clientProfile);

            /* 实例化一个请求对象，根据调用的接口和实际情况，可以进一步设置请求参数
             * 你可以直接查询SDK源码确定接口有哪些属性可以设置
             * 属性可能是基本类型，也可能引用了另一个数据结构
             * 推荐使用IDE进行开发，可以方便的跳转查阅各个接口和数据结构的文档说明 */
            SendSmsRequest req = new SendSmsRequest();

            /*
             * SDK appId 短信-应用管理-应用列表 */
            req.setSmsSdkAppId(sendSms.getSdkAppId());

            /*
             * 签名 短信-国内短信-签名管理，必须是审核通过的 */
            req.setSignName(sms.getSignName());

            /*
             * 模板ID 短信-国内短信-正文模板管理，必须是审核通过的 */
            req.setTemplateId(sms.getTemplateId());

            /*
             * 下发手机号码，采用 E.164 标准，+[国家或地区码][手机号]
             * 如+8613711112222， 其中前面有一个+号 ，86为国家码，13711112222为手机号 */
            String[] phoneNumberSet = new String[sms.getPhoneNumber().length];
            String[] array = sms.getPhoneNumber();
            for(int i = 0; i < phoneNumberSet.length; i++){
                phoneNumberSet[i] = "+86"+array[i];
            }
            req.setPhoneNumberSet(phoneNumberSet);

            /*
             * 模板参数{} */
            String[] templateParams = sms.getParams();
            req.setTemplateParamSet(templateParams);

            /* 通过 client 对象调用 SendSms 方法发起请求。注意请求方法名与请求对象是对应的
             * 返回的 res 是一个 SendSmsResponse 类的实例，与请求对象对应 */
            SendSmsResponse res = client.SendSms(req);

            return true;
        } catch (TencentCloudSDKException e) {
            System.out.printf("=============================================================");
            System.out.printf("发送短信失败！");
            e.printStackTrace();
            return false;
        }
    }



}

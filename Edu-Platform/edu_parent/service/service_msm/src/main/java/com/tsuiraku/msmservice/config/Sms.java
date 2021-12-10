package com.tsuiraku.msmservice.config;

import lombok.Data;

@Data
public class Sms {
    /* 模板 ID: 必须填写已审核通过的模板 ID。模板ID可登录 [短信控制台] 查看 */
    private String templateId;

    /* 短信签名内容: 使用 UTF-8 编码，必须填写已审核通过的签名，签名信息可登录 [短信控制台] 查看 */
    private String signName;

    /* 需要发送短信的号码
    *  可以发送多个号码*/
    private String[] phoneNumber;

    /* 模板参数: 若无模板参数，则设置为空 */
    private String[] params;

    public Sms(String templateId, String signName, String[] phoneNumber, String[] params) {
        this.templateId = templateId;
        this.signName = signName;
        this.phoneNumber = phoneNumber;
        this.params = params;
    }

    public Sms() {
    }


}

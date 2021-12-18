package com.tsuiraku.hospmanage.util;
import lombok.Getter;

/**
 * 统一返回结果状态信息类
 *
 * @author qy
 */
@Getter
public enum ResultCodeEnum {

    SUCCESS(200,"成功"),
    FAIL(201, "失败"),
    SERVICE_ERROR(202, "服务异常"),
    DATA_ERROR(204, "数据异常"),

    SIGN_ERROR(300, "签名错误"),

    PAY_PASSWORD_ERROR(401, "支付密码错误"),
    REPEAT_ERROR(402, "重复提交"),

    INVEST_AMMOUNT_MORE_ERROR(501, "出借金额已经多余标的金额"),
    RETURN_AMMOUNT_MORE_ERROR(502, "还款金额不正确"),
    PROJECT_AMMOUNT_ERROR(503, "标的金额不一致")
    ;

    private Integer code;

    private String message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

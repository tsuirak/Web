package com.tsuiraku.modelservice.vo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(description = "Order")
public class OrderQueryVo {


	@ApiModelProperty(value = "会员id")
	private Long userId;
	
	@ApiModelProperty(value = "订单交易号")
	private String outTradeNo;

	@ApiModelProperty(value = "就诊人id")
	private Long patientId;

	@ApiModelProperty(value = "就诊人")
	private String patientName;

	@ApiModelProperty(value = "医院名称")
	private String keyword;

	@ApiModelProperty(value = "订单状态")
	private String orderStatus;

	@ApiModelProperty(value = "安排日期")
	private String reserveDate;

	@ApiModelProperty(value = "创建时间")
	private String createTimeBegin;
	private String createTimeEnd;

}


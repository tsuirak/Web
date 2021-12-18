package com.tsuiraku.modelservice.vo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "OrderCountVo")
public class OrderCountVo {
	
	@ApiModelProperty(value = "安排日期")
	private String reserveDate;

	@ApiModelProperty(value = "预约单数")
	private Integer count;

}


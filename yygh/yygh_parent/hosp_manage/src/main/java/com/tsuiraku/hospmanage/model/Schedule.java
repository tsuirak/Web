package com.tsuiraku.hospmanage.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * Schedule
 * </p>
 *
 * @author qy
 */
@Data
@ApiModel(description = "Schedule")
@TableName("schedule")
public class Schedule extends BaseNoAutoEntity {
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "医院编号")
	@TableField("hoscode")
	private String hoscode;

	@ApiModelProperty(value = "科室编号")
	@TableField("depcode")
	private String depcode;

	@ApiModelProperty(value = "职称")
	@TableField("title")
	private String title;

	@ApiModelProperty(value = "医生名称")
	@TableField("docname")
	private String docname;

	@ApiModelProperty(value = "擅长技能")
	@TableField("skill")
	private String skill;

	@ApiModelProperty(value = "安排日期")
	@TableField("work_date")
	private String workDate;

	@ApiModelProperty(value = "安排时间（0：上午 1：下午）")
	@TableField("work_time")
	private Integer workTime;

	@ApiModelProperty(value = "可预约数")
	@TableField("reserved_number")
	private Integer reservedNumber;

	@ApiModelProperty(value = "剩余预约数")
	@TableField("available_number")
	private Integer availableNumber;

	@ApiModelProperty(value = "挂号费")
	@TableField("amount")
	private String amount;

	@ApiModelProperty(value = "排班状态（-1：停诊 0：停约 1：可约）")
	@TableField("status")
	private Integer status;
}


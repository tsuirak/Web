package com.tsuiraku.modelservice.vo.hosp;

import com.tsuiraku.modelservice.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * RegisterRule
 * </p>
 *
 * @author qy
 */
@Data
@ApiModel(description = "可预约排班规则数据")
public class BookingScheduleRuleVo {
	
	@ApiModelProperty(value = "可预约日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date workDate;

	@ApiModelProperty(value = "可预约日期")
	@JsonFormat(pattern = "MM月dd日")
	private Date workDateMd; //方便页面使用

	@ApiModelProperty(value = "周几")
	private String dayOfWeek;

	@ApiModelProperty(value = "就诊医生人数")
	private Integer docCount;

	@ApiModelProperty(value = "科室可预约数")
	private Integer reservedNumber;

	@ApiModelProperty(value = "科室剩余预约数")
	private Integer availableNumber;

	@ApiModelProperty(value = "状态 0：正常 1：即将放号 -1：当天已停止挂号")
	private Integer status;
}


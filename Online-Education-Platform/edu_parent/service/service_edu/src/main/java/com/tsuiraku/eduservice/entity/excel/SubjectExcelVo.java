package com.tsuiraku.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class SubjectExcelVo {
    @ExcelProperty(index = 0)
    private String subjectOne;
    @ExcelProperty(index = 1)
    private String subjectTwo;
}

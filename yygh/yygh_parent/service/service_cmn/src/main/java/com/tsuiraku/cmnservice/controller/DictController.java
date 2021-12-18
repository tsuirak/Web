package com.tsuiraku.cmnservice.controller;

import com.tsuiraku.cmnservice.service.DictService;
import com.tsuiraku.commonutil.result.Result;
import com.tsuiraku.modelservice.model.cmn.Dict;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/cmn/dict")
//@CrossOrigin
public class DictController {
    @Autowired
    private DictService dictService;

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/12/12 上午9:43
     *  @Description: 根据数据id查询子数据列表
     */
    @GetMapping("findChildData/{id}")
    public Result findChildData(@PathVariable Long id) {
        List<Dict> list = dictService.findChlidData(id);
        return Result.ok(list);
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/12/13 下午3:19
     *  @Description: 根据dictcode和value查询
     */
    @GetMapping("getName/{dictCode}/{value}")
    public String getName(@PathVariable String dictCode,
                          @PathVariable String value) {
        String dictName = dictService.getDictName(dictCode,value);
        return dictName;
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/12/13 下午3:19
     *  @Description: 根据value查询
     */
    @GetMapping("getName/{value}")
    public String getName(@PathVariable String value) {
        String dictName = dictService.getDictName("", value);
        return dictName;
    }

    /**
     *  @Author: tsuiraku
     *  @Date: 2021/12/13 下午7:39
     *  @Description: 根据dictCode获取下级节点
     */
    @GetMapping("findByDictCode/{dictCode}")
    public Result findByDictCode(@PathVariable String dictCode) {
        List<Dict> list = dictService.findByDictCode(dictCode);
        return Result.ok(list);
    }

}

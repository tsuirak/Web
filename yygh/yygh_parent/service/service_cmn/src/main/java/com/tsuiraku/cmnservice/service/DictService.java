package com.tsuiraku.cmnservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tsuiraku.modelservice.model.cmn.Dict;

import java.util.List;

public interface DictService extends IService<Dict> {
    List<Dict> findChlidData(Long id);

    /* 根据dictcode和value查询 */
    String getDictName(String dictCode, String value);

    List<Dict> findByDictCode(String dictCode);
}

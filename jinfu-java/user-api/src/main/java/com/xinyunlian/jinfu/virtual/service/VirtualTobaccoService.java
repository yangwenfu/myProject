package com.xinyunlian.jinfu.virtual.service;

import com.xinyunlian.jinfu.virtual.dto.VirtualTobaccoDto;
import com.xinyunlian.jinfu.virtual.enums.ETakeStatus;

import java.util.List;

/**
 * 虚拟烟草证Service
 * @author jll
 * @version 
 */

public interface VirtualTobaccoService {
    /**
     * 已领用虚拟烟草证个数
     * @param status
     * @return
     */
    Long countByTakeStatus(ETakeStatus status);

    /**
     * 已使用虚拟烟草证个数
     * @return
     */
    Long countUsed();

    /**
     * 虚拟烟草证领用
     * @param virtualTobaccoDto
     * @param takeNum
     * @return
     */
    List<VirtualTobaccoDto> take(VirtualTobaccoDto virtualTobaccoDto, int takeNum);

}

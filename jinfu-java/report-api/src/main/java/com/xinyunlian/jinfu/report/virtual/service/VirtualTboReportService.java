package com.xinyunlian.jinfu.report.virtual.service;

import com.xinyunlian.jinfu.report.virtual.dto.VirtualTboSearchDto;

/**
 * 虚拟烟草证Service
 * @author jll
 * @version 
 */

public interface VirtualTboReportService {
    VirtualTboSearchDto getPage(VirtualTboSearchDto searchDto);
}

package com.xinyunlian.jinfu.crm.service;

import com.xinyunlian.jinfu.crm.dto.CallSearchDto;
import com.xinyunlian.jinfu.crm.dto.CrmCallDto;
import com.xinyunlian.jinfu.crm.dto.CrmCallNoteDto;

import java.util.List;

/**
 * 客户通话记录Service
 * @author jll
 * @version 
 */

public interface CrmCallService {
    CrmCallDto save(CrmCallDto crmCallDto);

    CallSearchDto getCallPage(CallSearchDto callSearchDto);

    CrmCallDto findByCallId(Long callId);

    void addNote(CrmCallNoteDto callNoteDto);

    List<CrmCallNoteDto> listNotes(Long callId);

    Long countByCallTypeIdIn(List<Long> callTypeIds);
}

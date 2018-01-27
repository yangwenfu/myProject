package com.xinyunlian.jinfu.dealer.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.dealer.dto.DealerUserNoteDto;
import com.xinyunlian.jinfu.dealer.dto.DealerUserNoteSearchDto;

/**
 * Created by menglei on 2016年08月30日.
 */
public interface DealerUserNoteService {

    DealerUserNoteSearchDto getNotePage(DealerUserNoteSearchDto dealerUserNoteSearchDto);

    DealerUserNoteDto createNote(DealerUserNoteDto dealerUserNoteDto) throws BizServiceException;

    void deleteNote(Long noteId) throws BizServiceException;

    long getCount(DealerUserNoteDto dealerUserNoteDto);

}

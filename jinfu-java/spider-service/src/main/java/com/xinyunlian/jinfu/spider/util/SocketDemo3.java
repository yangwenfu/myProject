package com.xinyunlian.jinfu.spider.util;

import com.xinyunlian.jinfu.spider.dto.SocketConfigDto;

/**
 * Created by carrot on 2017/7/31.
 * 福建省-泉州市
 */
public class SocketDemo3 {

    public static void main(String[] args) {
        SocketConfigDto configDto = new SocketConfigDto();
        configDto.setLoginReq("2600112702000000");
        configDto.setAuthIp("61.131.48.222");
        configDto.setAuthPort(8090);
        configDto.setEncodePwd(false);
        SocketSpider spider = new SocketSpider(configDto);
        spider.spider("350521107146", "350523");
    }
}

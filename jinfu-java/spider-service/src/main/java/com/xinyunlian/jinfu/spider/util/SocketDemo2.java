package com.xinyunlian.jinfu.spider.util;

import com.xinyunlian.jinfu.spider.dto.SocketConfigDto;

/**
 * Created by carrot on 2017/7/31.
 * 福建省-三明市
 */
public class SocketDemo2 {

    public static void main(String[] args) {
        SocketConfigDto configDto = new SocketConfigDto();
        configDto.setLoginReq("2700112702000000");
        configDto.setAuthIp("218.86.120.180");
        configDto.setAuthPort(18001);
        configDto.setEncodePwd(false);
        SocketSpider spider = new SocketSpider(configDto);
        spider.spider("350425102193", "7279755");
    }

}

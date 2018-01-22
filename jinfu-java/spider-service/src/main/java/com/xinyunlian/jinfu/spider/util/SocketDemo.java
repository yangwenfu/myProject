package com.xinyunlian.jinfu.spider.util;

import com.xinyunlian.jinfu.spider.dto.SocketConfigDto;

/**
 * Created by carrot on 2017/7/31.
 * 福建省-漳州市
 */
public class SocketDemo {

    public static void main(String[] args) {
        SocketConfigDto configDto = new SocketConfigDto();
        configDto.setLoginReq("3000112702000000");
        configDto.setAuthIp("218.6.93.66");
        configDto.setAuthPort(18001);
        configDto.setEncodePwd(true);
        SocketSpider spider = new SocketSpider(configDto);
        System.out.println(spider.spider("350623107426", "a123456"));
    }

}

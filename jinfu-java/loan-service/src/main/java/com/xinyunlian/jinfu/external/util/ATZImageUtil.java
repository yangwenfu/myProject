package com.xinyunlian.jinfu.external.util;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.UUID;

/**
 * Created by godslhand on 2017/6/25.
 */
public class ATZImageUtil  {
   private static Logger  logger =LoggerFactory.getLogger(ATZImageUtil.class);
    public static File download(String url){
        URL dl = null;
        File fl = null;
        String x = null;
        try {
            fl = new File(UUID.randomUUID().toString()+".jpg");
            dl = new URL(url);
            OutputStream os = new FileOutputStream(fl);
            InputStream is = dl.openStream();
            dl.openConnection().getHeaderField("Content-Length");
            IOUtils.copy(is, os);//begin transfer

            os.close();//close streams
            is.close();//^
        } catch (Exception e) {
            logger.error("下载图片出错",e);
        }
        return fl;
    }
}

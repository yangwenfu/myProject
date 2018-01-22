package com.xinyunlian.jinfu.spider.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by carrot on 2017/8/7.
 */
public class SocketUtil {

    public static final Logger LOGGER = LoggerFactory.getLogger(SocketUtil.class);

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static ByteArrayOutputStream acceptByteArray(InputStream in) throws IOException, InterruptedException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] read = new byte[1024];
        int len;
        while ((len = in.read(read)) > -1) {
            baos.write(read, 0, len);
            read = new byte[1024];
            if(len == 1024)
                Thread.sleep(1000l);
            if (in.available() <= 0)
                break;
        }
        return baos;
    }

    public static StringBuffer accept(InputStream in) throws IOException, InterruptedException {
        ByteArrayOutputStream baos = acceptByteArray(in);
        StringBuffer sb = new StringBuffer();
        for (byte b : baos.toByteArray()) {
            sb.append(String.format("%02X", b));
        }
        return sb;
    }

    public static void writeGB2312StringZ(StringBuffer context, String write) throws UnsupportedEncodingException {
        byte[] bytes = write.getBytes("utf-8");
        for (byte b : bytes) {
            context.append(String.format("%02X", b));
        }
        context.append("00");
    }


    public static void writeUnsignedInt(StringBuffer context, Integer write) {
        String s = Integer.toHexString(write);
        s = padLeft(s, 8);
        context.append(s.substring(6, 8)).append(s.substring(4, 6)).append(s.substring(2, 4)).append(s.substring(0, 2));
    }

    public static void writeUnsignedShort(StringBuffer context, Short write) {
        String s = Integer.toHexString(write);
        s = padLeft(s, 4);
        context.append(s.substring(2, 4)).append(s.substring(0, 2));
    }

    public static int readUnsignedInt(StringBuffer context) {
        String intStr = context.toString().substring(0, 4);
        context.delete(0, 4);
        intStr = intStr.substring(2, 4) + intStr.substring(0, 2);
        return Integer.parseInt(intStr, 16);
    }

    public static short readUnsignedShort(StringBuffer context) {
        String intStr = context.toString().substring(0, 2);
        context.delete(0, 2);
        intStr = intStr.substring(0, 2);
        return Short.parseShort(intStr, 16);
    }

    public static List<String> readStringList(StringBuffer context) {
        List readStr = new ArrayList();
        int i = 0;
        for (; i < context.length() / 2; i++) {
            if (context.substring(i * 2, i * 2 + 2).equals("00"))
                break;
            readStr.add(context.substring(i * 2, i * 2 + 2));
        }
        context.delete(0, 2 * (i + 1));
        return readStr;
    }

    public static String readGB2312StringZ(StringBuffer context) {
        return hexStringListToString(readStringList(context), "gb2312");
    }

    public static String readUTF8StringZ(StringBuffer context) {
        return hexStringListToString(readStringList(context), "UTF-8");
    }

    public static String hexStringListToString(List<String> list, String charset) {
        if (list == null || list.size() <= 0) {
            return null;
        }
        String s = null;
        byte[] baKeyword = new byte[list.size()];
        int i = 0;
        for (String item : list) {
            try {
                baKeyword[i] = (byte) ((Character.digit(item.charAt(0), 16) << 4)
                        + Character.digit(item.charAt(1), 16));
                i++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, charset);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    public static String getBase64(String str) {
        byte[] b = null;
        String s = null;
        try {
            b = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (b != null) {
            s = new BASE64Encoder().encode(b);
        }
        return s;
    }

    public static StringBuffer send(OutputStream os, InputStream in, StringBuffer request, String requestName) throws IOException, InterruptedException {
        requestName = requestName == null ? "" : requestName;
        LOGGER.debug(requestName + " REQ: " + request.toString().toLowerCase());
        os.write(SocketUtil.hexStringToByteArray(request.toString().toLowerCase()));
        os.flush();

        Thread.sleep(500l);
        StringBuffer response = SocketUtil.accept(in);
        LOGGER.debug(requestName + " RESP: " + response.toString());
        return response;
    }

    private static String padLeft(String str, int len) {
        String pad = "0000000000000000";
        return len > str.length() && len <= 16 && len >= 0 ? pad.substring(0, len - str.length()) + str : str;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer("007E068027090000007506");
        System.out.println(readUTF8StringZ(sb));
        sb = new StringBuffer();
        writeGB2312StringZ(sb,"\t");
        System.out.println(sb);
    }



}

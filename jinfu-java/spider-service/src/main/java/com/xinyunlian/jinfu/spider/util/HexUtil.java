package com.xinyunlian.jinfu.spider.util;

/**
 * Created by carrot on 2017/7/31.
 */
public class HexUtil {

    /** 16进制中的字符集 */
    private static final String HEX_CHAR = "0123456789ABCDEF";

    /** 16进制中的字符集对应的字节数组 */
    private static final byte[] HEX_STRING_BYTE = HEX_CHAR.getBytes();

    /**
     * 10进制字节数组转换为16进制字节数组
     *
     * byte用二进制表示占用8位，16进制的每个字符需要用4位二进制位来表示，则可以把每个byte
     * 转换成两个相应的16进制字符，即把byte的高4位和低4位分别转换成相应的16进制字符，再取对应16进制字符的字节
     *
     * @param b 10进制字节数组
     * @return 16进制字节数组
     */
    public static byte[] byte2hex(byte[] b) {
        int length = b.length;
        byte[] b2 = new byte[length << 1];
        int pos;
        for(int i=0; i<length; i++) {
            pos = 2*i;
            b2[pos] = HEX_STRING_BYTE[(b[i] & 0xf0) >> 4];
            b2[pos+1] = HEX_STRING_BYTE[b[i] & 0x0f];
        }
        return b2;
    }

    /**
     * 16进制字节数组转换为10进制字节数组
     *
     * 两个16进制字节对应一个10进制字节，则将第一个16进制字节对应成16进制字符表中的位置(0~15)并向左移动4位，
     * 再与第二个16进制字节对应成16进制字符表中的位置(0~15)进行或运算，则得到对应的10进制字节
     * @param b 10进制字节数组
     * @return 16进制字节数组
     */
    public static byte[] hex2byte(byte[] b) {
        if(b.length%2 != 0) {
            throw new IllegalArgumentException("byte array length is not even!");
        }

        int length = b.length >> 1;
        byte[] b2 = new byte[length];
        int pos;
        for(int i=0; i<length; i++) {
            pos = i << 1;
            b2[i] = (byte) (HEX_CHAR.indexOf( b[pos] ) << 4 | HEX_CHAR.indexOf( b[pos+1] ) );
        }
        return b2;
    }

    /**
     * 将16进制字节数组转成10进制字符串
     * @param b 16进制字节数组
     * @return 10进制字符串
     */
    public static String hex2Str(byte[] b) {
        return new String(hex2byte(b));
    }

    /**
     * 将10进制字节数组转成16进制字符串
     * @param b 10进制字节数组
     * @return 16进制字符串
     */
    public static String byte2HexStr(byte[] b) {
        return Integer.toHexString(Integer.parseInt(new String(b)));
    }

    public static void main(String[] args) {
        System.out.println(hex2Str(byte2hex("60".getBytes())));
        System.out.println(byte2HexStr("60".getBytes()));
    }

}
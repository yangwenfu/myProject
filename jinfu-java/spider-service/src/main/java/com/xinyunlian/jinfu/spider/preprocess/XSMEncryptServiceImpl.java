package com.xinyunlian.jinfu.spider.preprocess;

import org.springframework.stereotype.Component;

/**
 * Created by bright on 2016/12/26.
 */
@Component(value = "xsm_encrypt")
public class XSMEncryptServiceImpl implements ParamProcessService {
    @Override
    public String process(String[] texts) {
        return hex_md5(hex_md5(texts[0]));
    }


    public String hex_md5(String pass){
        String mergePas = pass + "{1#2$3%4(5)6@7!poeeww$3%4(5)djjkkldss}";
        return binl2hex(core_md5(arr2binl(str2binl(mergePas)), 32));
    }

    int[] core_md5(int[] x, int len) {
        int a = 1732584193;
        int b = -271733879;
        int c = -1732584194;
        int d = 271733878;
        for (int i = 0; i < x.length; i += 16) {
            int olda = a;
            int oldb = b;
            int oldc = c;
            int oldd = d;
            a = ff(a, b, c, d, x[i + 0], 7, -680876936);
            d = ff(d, a, b, c, x[i + 1], 12, -389564586);
            c = ff(c, d, a, b, x[i + 2], 17, 606105819);
            b = ff(b, c, d, a, x[i + 3], 22, -1044525330);
            a = ff(a, b, c, d, x[i + 4], 7, -176418897);
            d = ff(d, a, b, c, x[i + 5], 12, 1200080426);
            c = ff(c, d, a, b, x[i + 6], 17, -1473231341);
            b = ff(b, c, d, a, x[i + 7], 22, -45705983);
            a = ff(a, b, c, d, x[i + 8], 7, 1770035416);
            d = ff(d, a, b, c, x[i + 9], 12, -1958414417);
            c = ff(c, d, a, b, x[i + 10], 17, -42063);
            b = ff(b, c, d, a, x[i + 11], 22, -1990404162);
            a = ff(a, b, c, d, x[i + 12], 7, 1804603682);
            d = ff(d, a, b, c, x[i + 13], 12, -40341101);
            c = ff(c, d, a, b, x[i + 14], 17, -1502002290);
            b = ff(b, c, d, a, x[i + 15], 22, 1236535329);
            a = gg(a, b, c, d, x[i + 1], 5, -165796510);
            d = gg(d, a, b, c, x[i + 6], 9, -1069501632);
            c = gg(c, d, a, b, x[i + 11], 14, 643717713);
            b = gg(b, c, d, a, x[i + 0], 20, -373897302);
            a = gg(a, b, c, d, x[i + 5], 5, -701558691);
            d = gg(d, a, b, c, x[i + 10], 9, 38016083);
            c = gg(c, d, a, b, x[i + 15], 14, -660478335);
            b = gg(b, c, d, a, x[i + 4], 20, -405537848);
            a = gg(a, b, c, d, x[i + 9], 5, 568446438);
            d = gg(d, a, b, c, x[i + 14], 9, -1019803690);
            c = gg(c, d, a, b, x[i + 3], 14, -187363961);
            b = gg(b, c, d, a, x[i + 8], 20, 1163531501);
            a = gg(a, b, c, d, x[i + 13], 5, -1444681467);
            d = gg(d, a, b, c, x[i + 2], 9, -51403784);
            c = gg(c, d, a, b, x[i + 7], 14, 1735328473);
            b = gg(b, c, d, a, x[i + 12], 20, -1926607734);
            a = hh(a, b, c, d, x[i + 5], 4, -378558);
            d = hh(d, a, b, c, x[i + 8], 11, -2022574463);
            c = hh(c, d, a, b, x[i + 11], 16, 1839030562);
            b = hh(b, c, d, a, x[i + 14], 23, -35309556);
            a = hh(a, b, c, d, x[i + 1], 4, -1530992060);
            d = hh(d, a, b, c, x[i + 4], 11, 1272893353);
            c = hh(c, d, a, b, x[i + 7], 16, -155497632);
            b = hh(b, c, d, a, x[i + 10], 23, -1094730640);
            a = hh(a, b, c, d, x[i + 13], 4, 681279174);
            d = hh(d, a, b, c, x[i + 0], 11, -358537222);
            c = hh(c, d, a, b, x[i + 3], 16, -722521979);
            b = hh(b, c, d, a, x[i + 6], 23, 76029189);
            a = hh(a, b, c, d, x[i + 9], 4, -640364487);
            d = hh(d, a, b, c, x[i + 12], 11, -421815835);
            c = hh(c, d, a, b, x[i + 15], 16, 530742520);
            b = hh(b, c, d, a, x[i + 2], 23, -995338651);
            a = ii(a, b, c, d, x[i + 0], 6, -198630844);
            d = ii(d, a, b, c, x[i + 7], 10, 1126891415);
            c = ii(c, d, a, b, x[i + 14], 15, -1416354905);
            b = ii(b, c, d, a, x[i + 5], 21, -57434055);
            a = ii(a, b, c, d, x[i + 12], 6, 1700485571);
            d = ii(d, a, b, c, x[i + 3], 10, -1894986606);
            c = ii(c, d, a, b, x[i + 10], 15, -1051523);
            b = ii(b, c, d, a, x[i + 1], 21, -2054922799);
            a = ii(a, b, c, d, x[i + 8], 6, 1873313359);
            d = ii(d, a, b, c, x[i + 15], 10, -30611744);
            c = ii(c, d, a, b, x[i + 6], 15, -1560198380);
            b = ii(b, c, d, a, x[i + 13], 21, 1309151649);
            a = ii(a, b, c, d, x[i + 4], 6, -145523070);
            d = ii(d, a, b, c, x[i + 11], 10, -1120210379);
            c = ii(c, d, a, b, x[i + 2], 15, 718787259);
            b = ii(b, c, d, a, x[i + 9], 21, -343485551);
            a = safe_add(a, olda);
            b = safe_add(b, oldb);
            c = safe_add(c, oldc);
            d = safe_add(d, oldd);
        }
        return new int[]{a, b, c, d};
    }
    int cmn(int q, int a, int b, int x, int s, int t) {
        return safe_add(rol(safe_add(safe_add(a, q), safe_add(x, t)), s), b);
    }
    int  ff(int a, int b, int c, int d, int x, int s, int t) {
        return cmn((b & c) | ((~b) & d), a, b, x, s, t);
    }
    int  gg(int a, int b, int c, int d, int x, int s, int t) {
        return cmn((b & d) | (c & (~d)), a, b, x, s, t);
    }
    int  hh(int a, int b, int c, int d, int x, int s, int t) {
        return cmn(b ^ c ^ d, a, b, x, s, t);
    }
    int  ii(int a, int b, int c, int d, int x, int s, int t) {
        return cmn(c ^ (b | (~d)), a, b, x, s, t);
    }
    int  rol(int num, int cnt) {
        return (num << cnt) | (num >>> (32 - cnt));
    }
    int[] str2binl(String str) {
        int len = str.length();
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            int cc = str.charAt(i);
            arr[i] = cc & 255;
        }
        return arr;
    }
    String binl2hex(int[] binarray) {
        String hex_tab = "0123456789abcdef";
        String str = "";
        for (int i = 0; i < binarray.length * 4; i++) {
            str += ""+ hex_tab.charAt((binarray[i >> 2] >> ((i % 4) * 8 + 4)) & 15) + hex_tab.charAt((binarray[i >> 2] >> ((i % 4) * 8)) & 15);
        }
        return str;
    }
    int[] arr2binl(int[] arr) {
        int nblk = ((arr.length + 8) >> 6) + 1;
        int[] blks = new int[nblk * 16];
        for (int i = 0; i < nblk * 16; i++) {
            blks[i] = 0;
        }
        int j = 0;
        for (j = 0; j < arr.length; j++) {
            blks[j >> 2] |= (arr[j] & 255) << ((j % 4) * 8);
        }
        blks[j >> 2] |= 128 << ((j % 4) * 8);
        blks[nblk * 16 - 2] = arr.length * 8;
        return blks;
    }

    int safe_add(int x, int y) {
        int lsw = (x & 65535) + (y & 65535);
        int msw = (x >> 16) + (y >> 16) + (lsw >> 16);
        return (msw << 16) | (lsw & 65535);
    }
}

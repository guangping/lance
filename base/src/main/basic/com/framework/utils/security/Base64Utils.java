package com.framework.utils.security;

import org.apache.commons.codec.binary.Base64;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-04-24 21:37
 * To change this template use File | Settings | File Templates.
 */
public class Base64Utils {
    /**
     * 文件读取缓冲区大小
     */
    private static final int CACHE_SIZE = 1024;

    /**
     * <p>
     * BASE64字符串解码为二进制数据
     * </p>
     *
     * @param base64
     * @return
     * @throws Exception
     */
    public static byte[] decode(String base64) throws Exception {
        Base64 base=new Base64();
        return base.decode(base64);
    }

    /**
     * <p>
     * 二进制数据编码为BASE64字符串
     * </p>
     *
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String encode(byte[] bytes) throws Exception {
        Base64 base=new Base64();
        return new String(base.encode(bytes));
    }
}

package com.ukayunnuo.fun.feishu.util;


import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.util.StrUtil;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * 飞书 签名加密工具类
 *
 * @author ukayunnuo
 * @since 1.0.0
 */
public class FeiShuRobotSignUtil {

    public static String HMACSHA256_STR = "HmacSHA256";

    /**
     * 加密
     *
     * @param secret    密钥
     * @param timestamp 时间戳 （单位：ms）
     * @return {@link String}
     * @throws NoSuchAlgorithmException  算法异常
     * @throws InvalidKeyException      无效key异常
     */
    public static String genSignForMillisecond(String secret, Long timestamp) throws NoSuchAlgorithmException, InvalidKeyException {
        return genSign(secret, timestamp / 1000);
    }


    /**
     * 加密
     *
     * @param secret    密钥
     * @param timestamp 时间戳 （单位：s）
     * @return {@link String}
     * @throws NoSuchAlgorithmException  算法异常
     * @throws InvalidKeyException      无效key异常
     */
    public static String genSign(String secret, Long timestamp) throws NoSuchAlgorithmException, InvalidKeyException {
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance(HMACSHA256_STR);
        mac.init(new SecretKeySpec(stringToSign.getBytes(StandardCharsets.UTF_8), HMACSHA256_STR));
        return Base64Encoder.encode(mac.doFinal(new byte[]{}));
    }


    /**
     * 加密
     *
     * @param secret     密钥
     * @param timestamp  时间戳 （单位：ms）
     * @param secretFlag 是否加密flag true = 加密，false = 不加密
     * @return {@link String}
     * @throws NoSuchAlgorithmException 算法异常
     * @throws InvalidKeyException      无效key异常
     */
    public static String genSign(Boolean secretFlag, String secret, Long timestamp) throws NoSuchAlgorithmException, InvalidKeyException {
        if (secretFlag) {
            if (StrUtil.isBlank(secret) || Objects.isNull(timestamp)) {
                throw new IllegalArgumentException("Illegal parameter!");
            }
            secret = genSignForMillisecond(secret, timestamp);
        }
        return secret;
    }

}

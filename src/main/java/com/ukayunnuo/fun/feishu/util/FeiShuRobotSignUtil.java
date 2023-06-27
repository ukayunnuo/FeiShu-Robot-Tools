package com.ukayunnuo.fun.feishu.util;


import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 飞书 签名加密工具类
 *
 * @author ukayunnuo
 * @since 1.0.0
 */
public class FeiShuRobotSignUtil {

    public static String HMACSHA256_STR = "HmacSHA256";

    public static String genSign(String secret, Long timestamp) throws NoSuchAlgorithmException, InvalidKeyException {
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance(HMACSHA256_STR);
        mac.init(new SecretKeySpec(stringToSign.getBytes(StandardCharsets.UTF_8), HMACSHA256_STR));
        return new BASE64Encoder().encode(mac.doFinal(new byte[]{}));
    }

}

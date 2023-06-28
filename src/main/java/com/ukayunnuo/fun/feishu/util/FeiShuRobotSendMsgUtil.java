package com.ukayunnuo.fun.feishu.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONObject;
import com.ukayunnuo.fun.feishu.enums.AtType;
import com.ukayunnuo.fun.feishu.enums.RootMsgType;
import com.ukayunnuo.fun.feishu.project.FeiShuRobotTxtMsgType;
import com.ukayunnuo.fun.feishu.project.msgtype.ImageType;
import com.ukayunnuo.fun.feishu.project.msgtype.RichTextMsgType;
import com.ukayunnuo.fun.feishu.project.msgtype.ShareChatType;
import com.ukayunnuo.fun.feishu.project.msgtype.TextMsgType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * 飞书机器人 推送消息工具类
 *
 * @author ukayunnuo
 * @since 1.0.0
 */
@Slf4j
public class FeiShuRobotSendMsgUtil {

    public static HttpResponse sendTextMsg(String url, String content) throws NoSuchAlgorithmException, InvalidKeyException, InstantiationException, IllegalAccessException {
        return sendTextMsg(url, content, Boolean.FALSE, null, null, AtType.NONE, null, null);
    }

    public static HttpResponse sendAtSingleUserTextMsg(String url, String content, String userId) throws NoSuchAlgorithmException, InvalidKeyException, InstantiationException, IllegalAccessException {
        return sendAtSingleUserTextMsg(url, content, userId, null);
    }

    public static HttpResponse sendAtSingleUserTextMsg(String url, String content, String userId, String userName) throws NoSuchAlgorithmException, InvalidKeyException, InstantiationException, IllegalAccessException {
        return sendTextMsg(url, content, Boolean.FALSE, null, null, AtType.SINGLE_USER, userId, userName);
    }

    public static HttpResponse sendSecretTextMsg(String url, String content, String secret) throws NoSuchAlgorithmException, InvalidKeyException, InstantiationException, IllegalAccessException {
        return sendSecretTextMsg(url, content, secret, DateUtil.date().getTime());
    }

    public static HttpResponse sendSecretTextMsg(String url, String content, String secret, Long timestamp) throws NoSuchAlgorithmException, InvalidKeyException, InstantiationException, IllegalAccessException {
        return sendTextMsg(url, content, Boolean.TRUE, secret, timestamp, AtType.NONE, null, null);
    }

    public static HttpResponse sendAtSingleUserSecretTextMsg(String url, String content, String secret, String userId, String userName) throws NoSuchAlgorithmException, InvalidKeyException, InstantiationException, IllegalAccessException {
        return sendTextMsg(url, content, Boolean.TRUE, secret, DateUtil.date().getTime(), AtType.SINGLE_USER, userId, userName);
    }

    public static HttpResponse sendTextMsg(String url, String content, String secret, Long timestamp, String userId, String userName) throws NoSuchAlgorithmException, InvalidKeyException, InstantiationException, IllegalAccessException {
        return sendTextMsg(url, content, Boolean.TRUE, secret, timestamp, AtType.SINGLE_USER, userId, userName);
    }

    public static HttpResponse sendTextMsg(String url, String content, Boolean secretFlag, String secret, Long timestamp, AtType atType, String userId, String userName) throws NoSuchAlgorithmException, InvalidKeyException, InstantiationException, IllegalAccessException {

        if (Boolean.FALSE.equals(StrUtil.isAllNotBlank(url, content))) {
            throw new IllegalArgumentException("Illegal parameter!");
        }

        secret = getSecret(secretFlag, secret, timestamp);

        FeiShuRobotTxtMsgType<TextMsgType> reqBody = assembleMsgType(timestamp, secret, TextMsgType.class);

        content = assembleAtContent(content, atType, userId, userName);

        reqBody.setContent(new TextMsgType(content));
        String req = JSONObject.toJSONString(reqBody);

        HttpResponse res = HttpUtil.createPost(url).body(req).execute();

        log.info("send FeiShu Robot Msg reqBody:{}, resBody:{}", req, res.body());

        return res;

    }

    private static String assembleAtContent(String content, AtType atType, String userId, String userName) {
        if (Objects.nonNull(atType) && AtType.SINGLE_USER.equals(atType)) {
            if (StringUtils.isBlank(userId)) {
                throw new IllegalArgumentException("userId not exist!");
            }
            content = StrUtil.format(content, MapUtil.builder().put("user_id", userId).put("name", StrUtil.emptyIfNull(userName)).map());
        }
        return content;
    }

    private static String getSecret(Boolean secretFlag, String secret, Long timestamp) throws NoSuchAlgorithmException, InvalidKeyException {
        if (secretFlag) {
            if (StrUtil.isBlank(secret) || Objects.isNull(timestamp)) {
                throw new IllegalArgumentException("Illegal parameter!");
            }
            secret = FeiShuRobotSignUtil.genSignForMillisecond(secret, timestamp);
        }
        return secret;
    }

    public static <T> FeiShuRobotTxtMsgType<T> assembleMsgType(Long timestamp, String secret, Class<T> clazz) throws InstantiationException, IllegalAccessException {

        T instance = clazz.newInstance();
        if (instance instanceof TextMsgType){
            return new FeiShuRobotTxtMsgType<>(RootMsgType.text, timestamp, secret);
        }
        if (instance instanceof RichTextMsgType){
            return new FeiShuRobotTxtMsgType<>(RootMsgType.post, timestamp, secret);
        }
        if (instance instanceof ImageType){
            return new FeiShuRobotTxtMsgType<>(RootMsgType.image, timestamp, secret);
        }
        if (instance instanceof ShareChatType){
            return new FeiShuRobotTxtMsgType<>(RootMsgType.share_chat, timestamp, secret);
        }

        throw new InstantiationException("param clazz is Illegal Instantiation!");
    }

    public static HttpResponse sendRichTextMsgMsg(String url, String content, Boolean secretFlag, String secret, Long timestamp, AtType atType, String userId, String userName) throws NoSuchAlgorithmException, InvalidKeyException {

        return null;
    }

}

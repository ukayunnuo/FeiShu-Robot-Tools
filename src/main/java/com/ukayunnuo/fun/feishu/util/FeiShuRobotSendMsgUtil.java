package com.ukayunnuo.fun.feishu.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ukayunnuo.fun.feishu.enums.AtType;
import com.ukayunnuo.fun.feishu.enums.RootMsgType;
import com.ukayunnuo.fun.feishu.project.FeiShuRobotTxtMsgType;
import com.ukayunnuo.fun.feishu.project.base.RichTextContentDto;
import com.ukayunnuo.fun.feishu.project.msgtype.ImageType;
import com.ukayunnuo.fun.feishu.project.msgtype.RichTextMsgType;
import com.ukayunnuo.fun.feishu.project.msgtype.ShareChatType;
import com.ukayunnuo.fun.feishu.project.msgtype.TextMsgType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
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

    /**
     * 发送text消息
     *
     * @param url        url
     * @param content    内容
     * @param secretFlag 加密flag true=加密，false=不加密
     * @param secret     密钥
     * @param timestamp  时间戳（单位：ms）
     * @param atType     @类型 {@link AtType}
     * @param userId     用户id
     * @param userName   用户名
     * @return {@link HttpResponse}
     * @throws NoSuchAlgorithmException 算法异常
     * @throws InvalidKeyException      无效key异常
     * @throws InstantiationException   实例化异常
     * @throws IllegalAccessException   非法访问异常
     */
    public static HttpResponse sendTextMsg(String url, String content, Boolean secretFlag, String secret, Long timestamp, AtType atType, String userId, String userName) throws NoSuchAlgorithmException, InvalidKeyException, InstantiationException, IllegalAccessException {

        if (Boolean.FALSE.equals(StrUtil.isAllNotBlank(url, content))) {
            throw new IllegalArgumentException("Illegal parameter!");
        }

        secret = FeiShuRobotSignUtil.genSign(secretFlag, secret, timestamp);

        FeiShuRobotTxtMsgType<TextMsgType> reqBody = assembleMsgType(timestamp, secret, TextMsgType.class);

        content = assembleAtContent(content, atType, userId, userName);

        reqBody.setContent(new TextMsgType(content));

        return sendPost(url, reqBody);

    }


    public static HttpResponse sendEnUsRichTextMsg(String url, String enUsTitle, List<List<RichTextContentDto>> enUsContents) throws NoSuchAlgorithmException, InvalidKeyException, InstantiationException, IllegalAccessException {
        return sendRichTextMsg(url, null, enUsTitle, null, enUsContents);
    }

    public static HttpResponse sendEnUsRichTextMsg(String url, String enUsTitle, List<List<RichTextContentDto>> enUsContents, String secret) throws NoSuchAlgorithmException, InvalidKeyException, InstantiationException, IllegalAccessException {
        return sendEnUsRichTextMsg(url, enUsTitle, enUsContents, secret, DateUtil.date().getTime());
    }

    public static HttpResponse sendEnUsRichTextMsg(String url, String enUsTitle, List<List<RichTextContentDto>> enUsContents, String secret, Long timestamp) throws NoSuchAlgorithmException, InvalidKeyException, InstantiationException, IllegalAccessException {
        return sendRichTextMsg(url, null, enUsTitle, null, enUsContents, secret, timestamp);
    }

    public static HttpResponse sendZhCnRichTextMsg(String url, String zhCnTitle, List<List<RichTextContentDto>> zhCnContents) throws NoSuchAlgorithmException, InvalidKeyException, InstantiationException, IllegalAccessException {
        return sendRichTextMsg(url, zhCnTitle, null, zhCnContents, null);
    }

    public static HttpResponse sendZhCnRichTextMsg(String url, String zhCnTitle, List<List<RichTextContentDto>> zhCnContents, String secret) throws NoSuchAlgorithmException, InvalidKeyException, InstantiationException, IllegalAccessException {
        return sendZhCnRichTextMsg(url, zhCnTitle, zhCnContents, secret, DateUtil.date().getTime());
    }

    public static HttpResponse sendZhCnRichTextMsg(String url, String zhCnTitle, List<List<RichTextContentDto>> zhCnContents, String secret, Long timestamp) throws NoSuchAlgorithmException, InvalidKeyException, InstantiationException, IllegalAccessException {
        return sendRichTextMsg(url, zhCnTitle, null, zhCnContents, null, secret, timestamp);
    }

    public static HttpResponse sendRichTextMsg(String url, String zhCnTitle, String enUsTitle, List<List<RichTextContentDto>> zhCnContents, List<List<RichTextContentDto>> enUsContents) throws NoSuchAlgorithmException, InvalidKeyException, InstantiationException, IllegalAccessException {
        return sendRichTextMsg(url, zhCnTitle, enUsTitle, zhCnContents, enUsContents, Boolean.FALSE, null, null);
    }

    public static HttpResponse sendRichTextMsg(String url, String zhCnTitle, String enUsTitle, List<List<RichTextContentDto>> zhCnContents, List<List<RichTextContentDto>> enUsContents, String secret) throws NoSuchAlgorithmException, InvalidKeyException, InstantiationException, IllegalAccessException {
        return sendRichTextMsg(url, zhCnTitle, enUsTitle, zhCnContents, enUsContents, secret, DateUtil.date().getTime());
    }

    public static HttpResponse sendRichTextMsg(String url, String zhCnTitle, String enUsTitle, List<List<RichTextContentDto>> zhCnContents, List<List<RichTextContentDto>> enUsContents, String secret, Long timestamp) throws NoSuchAlgorithmException, InvalidKeyException, InstantiationException, IllegalAccessException {
        return sendRichTextMsg(url, zhCnTitle, enUsTitle, zhCnContents, enUsContents, Boolean.TRUE, secret, timestamp);
    }


    /**
     * 发送富文本消息
     *
     * @param url          url
     * @param zhCnTitle    zh标题
     * @param enUsTitle    en标题
     * @param zhCnContents zh内容
     * @param enUsContents en内容
     * @param secretFlag   加密flag true=加密，false=不加密
     * @param secret       密钥
     * @param timestamp    时间戳（单位：ms）
     * @return {@link HttpResponse}
     * @throws NoSuchAlgorithmException 算法异常
     * @throws InvalidKeyException      无效key异常
     * @throws InstantiationException   实例化异常
     * @throws IllegalAccessException   非法访问异常
     */
    public static HttpResponse sendRichTextMsg(String url, String zhCnTitle, String enUsTitle, List<List<RichTextContentDto>> zhCnContents, List<List<RichTextContentDto>> enUsContents, Boolean secretFlag, String secret, Long timestamp) throws NoSuchAlgorithmException, InvalidKeyException, InstantiationException, IllegalAccessException {

        if (StrUtil.isBlank(url)) {
            throw new IllegalArgumentException("Illegal parameter!");
        }

        if (StrUtil.isBlank(zhCnTitle) && StrUtil.isBlank(enUsTitle)) {
            throw new IllegalArgumentException("Illegal parameter!");
        }

        if (CollUtil.isEmpty(zhCnContents) && CollUtil.isEmpty(enUsContents)) {
            throw new IllegalArgumentException("Illegal parameter!");
        }

        secret = FeiShuRobotSignUtil.genSign(secretFlag, secret, timestamp);

        FeiShuRobotTxtMsgType<RichTextMsgType> reqBody = assembleMsgType(timestamp, secret, RichTextMsgType.class);

        RichTextMsgType content = new RichTextMsgType();

        if (StrUtil.isNotBlank(zhCnTitle) || CollUtil.isNotEmpty(zhCnContents)) {
            RichTextMsgType.RichTextPostDTO richTextPostDTO =
                    new RichTextMsgType.RichTextPostDTO(new RichTextMsgType.RichTextPostDTO.RichTextPostDto(StrUtil.emptyIfNull(zhCnTitle), CollUtil.emptyIfNull(zhCnContents)));
            content.setPost(richTextPostDTO);
        }

        if (StrUtil.isNotBlank(enUsTitle) || CollUtil.isNotEmpty(enUsContents)) {
            RichTextMsgType.RichTextPostDTO post = content.getPost();
            post.setEnUs(new RichTextMsgType.RichTextPostDTO.RichTextPostDto(StrUtil.emptyIfNull(enUsTitle), CollUtil.emptyIfNull(enUsContents)));
        }

        reqBody.setContent(content);
        return sendPost(url, reqBody);
    }

    public static HttpResponse sendShareChatMsg(String url, String shareChatId) throws NoSuchAlgorithmException, InvalidKeyException, InstantiationException, IllegalAccessException {
        return sendShareChatMsg(url, shareChatId, Boolean.FALSE, null, null);
    }

    public static HttpResponse sendShareChatMsg(String url, String shareChatId, String secret) throws NoSuchAlgorithmException, InvalidKeyException, InstantiationException, IllegalAccessException {
        return sendShareChatMsg(url, shareChatId,  secret, DateUtil.date().getTime());
    }

        public static HttpResponse sendShareChatMsg(String url, String shareChatId, String secret, Long timestamp) throws NoSuchAlgorithmException, InvalidKeyException, InstantiationException, IllegalAccessException {
        return sendShareChatMsg(url, shareChatId, Boolean.TRUE, secret, timestamp);
    }


    /**
     * 发送群名片消息
     *
     * @param url         url
     * @param shareChatId 群 ID
     * @param secretFlag  加密flag true=加密，false=不加密
     * @param secret      密钥
     * @param timestamp   时间戳（单位：ms）
     * @return {@link HttpResponse}
     * @throws NoSuchAlgorithmException 算法异常
     * @throws InvalidKeyException      无效key异常
     * @throws InstantiationException   实例化异常
     * @throws IllegalAccessException   非法访问异常
     */
    public static HttpResponse sendShareChatMsg(String url, String shareChatId, Boolean secretFlag, String secret, Long timestamp) throws NoSuchAlgorithmException, InvalidKeyException, InstantiationException, IllegalAccessException {

        if (Boolean.FALSE.equals(StrUtil.isAllNotBlank(url, shareChatId))){
            throw new IllegalArgumentException("Illegal parameter!");
        }

        secret = FeiShuRobotSignUtil.genSign(secretFlag, secret, timestamp);

        FeiShuRobotTxtMsgType<ShareChatType> reqBody = assembleMsgType(timestamp, secret, ShareChatType.class);

        reqBody.setContent(new ShareChatType(shareChatId));
        return sendPost(url, reqBody);
    }

    public static HttpResponse sendImagMsg(String url, String imageKey) throws NoSuchAlgorithmException, InvalidKeyException, InstantiationException, IllegalAccessException {
        return sendImagMsg(url, imageKey, Boolean.FALSE, null, null);
    }

    public static HttpResponse sendImagMsg(String url, String imageKey, String secret) throws NoSuchAlgorithmException, InvalidKeyException, InstantiationException, IllegalAccessException {
        return sendImagMsg(url, imageKey, secret, DateUtil.date().getTime());
    }

    public static HttpResponse sendImagMsg(String url, String imageKey, String secret, Long timestamp) throws NoSuchAlgorithmException, InvalidKeyException, InstantiationException, IllegalAccessException {
     return sendImagMsg(url, imageKey, Boolean.TRUE, secret, timestamp);
    }


    /**
     * 发送图片消息
     *
     * @param url         url
     * @param imageKey    图片Key
     * @param secretFlag  加密flag true=加密，false=不加密
     * @param secret      密钥
     * @param timestamp   时间戳（单位：ms）
     * @return {@link HttpResponse}
     * @throws NoSuchAlgorithmException 算法异常
     * @throws InvalidKeyException      无效key异常
     * @throws InstantiationException   实例化异常
     * @throws IllegalAccessException   非法访问异常
     */
    public static HttpResponse sendImagMsg(String url, String imageKey, Boolean secretFlag, String secret, Long timestamp) throws NoSuchAlgorithmException, InvalidKeyException, InstantiationException, IllegalAccessException {

        if (Boolean.FALSE.equals(StrUtil.isAllNotBlank(url, imageKey))){
            throw new IllegalArgumentException("Illegal parameter!");
        }

        secret = FeiShuRobotSignUtil.genSign(secretFlag, secret, timestamp);

        FeiShuRobotTxtMsgType<ImageType> reqBody = assembleMsgType(timestamp, secret, ImageType.class);

        reqBody.setContent(new ImageType(imageKey));
        return sendPost(url, reqBody);
    }


    /**
     * 发送post 消息
     *
     * @param url url
     * @param t   body参数Dto
     * @return {@link HttpResponse}
     */
    private static <T> HttpResponse sendPost(String url, T t) {

        String req = JSONObject.toJSONString(t);
        HttpResponse res = HttpUtil.createPost(url).body(req).execute();

        if (res == null) {
            log.warn("send FeiShu Robot Msg error! HttpResponse is null! reqBody:{}", req);
            return null;
        }

        String resBody = res.body();
        if (Boolean.FALSE.equals(JSON.isValid(resBody))) {
            log.warn("send FeiShu Robot Msg failed! reqBody:{}, resBody:{}", req, resBody);
            return res;
        }

        Integer code = JSONObject.parseObject(resBody).getInteger("code");
        if (Objects.nonNull(code) && Objects.equals(code, 0)) {
            log.info("send FeiShu Robot Msg succeed! reqBody:{}", req);
        } else {
            log.warn("send FeiShu Robot Msg failed! reqBody:{}, resBody:{}", req, resBody);
        }
        return res;
    }

    /**
     * 组装@内容数据
     *
     * @param content  内容
     * @param atType   @类型
     * @param userId   用户id
     * @param userName 用户名
     * @return {@link String}
     */
    private static String assembleAtContent(String content, AtType atType, String userId, String userName) {
        if (Objects.nonNull(atType) && AtType.SINGLE_USER.equals(atType)) {
            if (StringUtils.isBlank(userId)) {
                throw new IllegalArgumentException("userId not exist!");
            }
            content = StrUtil.format(content, MapUtil.builder().put("user_id", userId).put("name", StrUtil.emptyIfNull(userName)).map());
        }
        return content;
    }

    /**
     * 组装msg类型
     *
     * @param timestamp 时间戳 单位(ms)
     * @param secret    密钥
     * @param clazz     clazz
     * @return {@link FeiShuRobotTxtMsgType}<{@link T}>
     * @throws InstantiationException 实例化异常
     * @throws IllegalAccessException 非法访问异常
     */
    public static <T> FeiShuRobotTxtMsgType<T> assembleMsgType(Long timestamp, String secret, Class<T> clazz) throws InstantiationException, IllegalAccessException {

        T instance = clazz.newInstance();
        if (instance instanceof TextMsgType) {
            return new FeiShuRobotTxtMsgType<>(RootMsgType.text, timestamp, secret);
        }
        if (instance instanceof RichTextMsgType) {
            return new FeiShuRobotTxtMsgType<>(RootMsgType.post, timestamp, secret);
        }
        if (instance instanceof ImageType) {
            return new FeiShuRobotTxtMsgType<>(RootMsgType.image, timestamp, secret);
        }
        if (instance instanceof ShareChatType) {
            return new FeiShuRobotTxtMsgType<>(RootMsgType.share_chat, timestamp, secret);
        }

        throw new InstantiationException("param clazz is Illegal Instantiation!");
    }

}

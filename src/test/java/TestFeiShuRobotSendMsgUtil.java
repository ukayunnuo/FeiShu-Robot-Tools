import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpResponse;
import com.ukayunnuo.fun.feishu.constant.FeiShuMsgConstant;
import com.ukayunnuo.fun.feishu.project.base.RichTextContentDto;
import com.ukayunnuo.fun.feishu.util.FeiShuRobotSendMsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 飞书机器人 发送消息测试类
 *
 * @author ukayunnuo
 * @since 1.0.0
 */
@Slf4j
public class TestFeiShuRobotSendMsgUtil {

    private static String url;
    private static String secret;

    @Before
    public void init() {
        // 设置 机器人的webhook地址
        url = "机器人的webhook地址";
        // 设置 机器人的验签密钥, 如何机器人没有开启验签设置则不用设置
        secret = "机器人的验签密钥";
    }

    /**
     * 测试发送文本消息内容 (无验签)
     */
    @Test
    public void testSendTextMsg() {
        String contentAtAll = "普通文本 @所有人测试 " + FeiShuMsgConstant.AT_ALL_USER;
        String contentAtSingleUser = "测试@单用户: " + FeiShuMsgConstant.AT_SINGLE_USER;
        try (
                HttpResponse resAtAllRes = FeiShuRobotSendMsgUtil.sendTextMsg(url, contentAtAll);
                HttpResponse resAtSingleUserRes = FeiShuRobotSendMsgUtil.sendAtSingleUserTextMsg(url, contentAtSingleUser, "yunnuo", "云诺")) {
            String resAtAllResBody = resAtAllRes.body();
            log.info("send FeiShu Robot resBody:{}", resAtAllResBody);
            String resAtSingleUserResBody = resAtSingleUserRes.body();
            log.info("send FeiShu Robot resBody:{}", resAtSingleUserResBody);
        } catch (Exception e) {
            log.error("send FeiShu Robot error! url:{}, secret:{}, e:{}", url, secret, e.getMessage(), e);
        }
    }

    /**
     * 测试发送签名校验文本消息内容
     */
    @Test
    public void testSendSecretTextMsg() {
        String contentAtAll = "普通文本 @所有人测试 " + FeiShuMsgConstant.AT_ALL_USER;
        String contentAtSingleUser = "测试@单用户: " + FeiShuMsgConstant.AT_SINGLE_USER;
        try (
                HttpResponse resAtAllRes = FeiShuRobotSendMsgUtil.sendSecretTextMsg(url, contentAtAll, secret);
                HttpResponse resAtSingleUserRes = FeiShuRobotSendMsgUtil.sendAtSingleUserSecretTextMsg(url, contentAtSingleUser, secret, "yunnuo", "云诺")) {
            String resAtAllResBody = resAtAllRes.body();
            log.info("send FeiShu Robot resBody:{}", resAtAllResBody);
            String resAtSingleUserResBody = resAtSingleUserRes.body();
            log.info("send FeiShu Robot resBody:{}", resAtSingleUserResBody);
        } catch (Exception e) {
            log.error("send FeiShu Robot error! url:{}, secret:{}, e:{}", url, secret, e.getMessage(), e);
        }
    }

    /**
     * 测试发送富文本消息内容 (无验签)
     */
    @Test
    public void testSendRichTextMsg() {
        String title = "测试标题";
        List<List<RichTextContentDto>> contentDtos = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            RichTextContentDto textTag = RichTextContentDto.setTextTag(StrUtil.format("测试数据-{}: ", i));
            RichTextContentDto textHref = RichTextContentDto.setHyperlinkTag("http://www.ukayunnuo.test","点击链接");
            RichTextContentDto textAt = RichTextContentDto.setAtAllTag();
            // RichTextContentDto textImage = RichTextContentDto.setImageTag("image_key_demo");
            List<RichTextContentDto> contentDtosDiv = new ArrayList<>();
            contentDtosDiv.add(textTag);
            contentDtosDiv.add(textHref);
            contentDtosDiv.add(textAt);
            // contentDtosDiv.add(textImage);
            contentDtos.add(contentDtosDiv);
        }

        try (HttpResponse res = FeiShuRobotSendMsgUtil.sendZhCnRichTextMsg(url, title, contentDtos)) {
            String resAtSingleUserResBody = res.body();
            log.info("send FeiShu Robot resBody:{}", resAtSingleUserResBody);
        } catch (Exception e) {
            log.error("send FeiShu Robot error! url:{}, secret:{}, e:{}", url, secret, e.getMessage(), e);
        }
    }

}

# 飞书机器人工具

## 项目介绍
该项目是一个`Maven项目的`飞书机器人消息推送的工具, 通过该工具可以快速的进行对接飞书机器人消息推送.支持机器人设置签名校验

### 项目功能：
- 发送文本消息: 普通文本消息支持`@用户和@所有人`

![image](https://github.com/ukayunnuo/FeiShu-Robot-Tools/blob/master/image/img_1.png)

- 发送富文本消息:支持中/英,包含文本、超链接、图标等多种文本样式的复合文本信息。

![image](https://github.com/ukayunnuo/FeiShu-Robot-Tools/blob/master/image/img_2.png)

- 分享群名片消息:支持分享群名片消息

![image](https://github.com/ukayunnuo/FeiShu-Robot-Tools/blob/master/image/img_3.png)

- 发送图片消息:支持发送图片功能

![image](https://github.com/ukayunnuo/FeiShu-Robot-Tools/blob/master/image/img_4.png)

## 官方飞书机器人使用指南
自定义飞书机器人使用指南：https://open.feishu.cn/document/client-docs/bot-v3/add-custom-bot#f62e72d5


## 该项目包含的依赖

> lombok+hutool工具类+slf4j+fastjson2+commons-lang3+junit

## 使用教程

在项目中`src/test/java/TestFeiShuRobotSendMsgUtil.java` java文件是一个测试发送飞书消息的测试类，可以直接进行运行测试使用

### 简单使用示例代码

该示例是使用工具类进行发送文本消息： @单个用户和所有用户和签名验证功能

```java
public class TestFeiShuRobotSendMsgUtil {

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
}
```
### 效果：

![image](https://github.com/ukayunnuo/FeiShu-Robot-Tools/blob/master/image/img_5.png)

## Star History

[![Star History Chart](https://api.star-history.com/svg?repos=ukayunnuo/FeiShu-Robot-Tools&type=Date)](https://star-history.com/#ukayunnuo/FeiShu-Robot-Tools&Date)


package com.ukayunnuo.fun.feishu.project;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.annotation.JSONField;
import com.alibaba.fastjson2.annotation.JSONType;
import com.ukayunnuo.fun.feishu.enums.RootMsgType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 飞书机器人 [文本、富文本、群名片、图片] 消息类型
 *
 * @author yunnuo
 * @since 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JSONType(orders = {"timestamp","sign","msg_type","content"})
public class FeiShuRobotTxtMsgType<T> {

    /**
     * 时间戳 timestamp是指距当前时间不超过 1 小时（3600 秒）的时间戳，时间单位：s。例如，1599360473。
     */
    private String timestamp;

    /**
     * 签名
     */
    private String sign;

    @JSONField(name = "msg_type")
    private RootMsgType msgType;

    private T content;

    public FeiShuRobotTxtMsgType(RootMsgType msgType, Long timestamp, String sign) {
        this.msgType = msgType;
        if (Objects.nonNull(timestamp)) {
            this.timestamp = String.valueOf((timestamp / 1000));
        }
        if (StringUtils.isNotBlank(sign)) {
            this.sign = sign;
        }
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}

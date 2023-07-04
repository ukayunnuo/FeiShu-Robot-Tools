package com.ukayunnuo.fun.feishu.project;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.annotation.JSONType;
import com.ukayunnuo.fun.feishu.enums.RootMsgType;
import com.ukayunnuo.fun.feishu.project.base.MsgBaseType;
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
public class FeiShuRobotTxtMsgType<T> extends MsgBaseType {

    private T content;

    public FeiShuRobotTxtMsgType(RootMsgType msgType, Long timestamp, String sign) {
        this.setMsgType(msgType);
        if (Objects.nonNull(timestamp)) {
            this.setTimestamp(String.valueOf((timestamp / 1000)));
        }
        if (StringUtils.isNotBlank(sign)) {
            this.setSign(sign);
        }
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}

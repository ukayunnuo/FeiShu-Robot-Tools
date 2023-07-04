package com.ukayunnuo.fun.feishu.project.card;

import com.alibaba.fastjson2.annotation.JSONType;
import com.ukayunnuo.fun.feishu.enums.RootMsgType;
import com.ukayunnuo.fun.feishu.project.base.MsgBaseType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 飞书机器人 卡片消息类型
 * <a href = "https://open.feishu.cn/document/common-capabilities/message-card/message-cards-content/card-structure/card-content"> 卡片结构介绍 </a>
 *
 * @author ukayunnuo
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@JSONType(orders = {"timestamp", "sign", "card", "header", "elements", "i18n_elements", "config"})
public class FeiShuRobotCardMsgType extends MsgBaseType {

    private CardConfig config = new CardConfig();

    public FeiShuRobotCardMsgType(RootMsgType msgType, Long timestamp, String sign) {
        this.setMsgType(msgType);
        if (Objects.nonNull(timestamp)) {
            this.setTimestamp(String.valueOf((timestamp / 1000)));
        }
        if (StringUtils.isNotBlank(sign)) {
            this.setSign(sign);
        }
    }

}

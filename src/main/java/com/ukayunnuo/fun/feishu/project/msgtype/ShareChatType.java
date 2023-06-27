package com.ukayunnuo.fun.feishu.project.msgtype;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * 飞书机器人 分享群名片 类型
 *
 * @author ukayunnuo
 * @since 1.0.0
 */
@Data
public class ShareChatType {

    @JSONField(name = "share_chat_id")
    private String shareChatId;

}

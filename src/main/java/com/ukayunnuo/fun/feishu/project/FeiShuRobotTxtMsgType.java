package com.ukayunnuo.fun.feishu.project;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class FeiShuRobotTxtMsgType<T> {

    @JSONField(name = "msg_type")
    private String msgType;

    private T content;

    /**
     * 时间戳
     */
    private Long timestamp;


    /**
     * 签名
     */
    private String sign;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}

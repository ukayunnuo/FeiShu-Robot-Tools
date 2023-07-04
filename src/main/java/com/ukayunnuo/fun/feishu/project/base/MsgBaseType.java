package com.ukayunnuo.fun.feishu.project.base;

import com.alibaba.fastjson2.annotation.JSONField;
import com.ukayunnuo.fun.feishu.enums.RootMsgType;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ukayunnuo
 * @since 1.0.0
 */
@Getter
@Setter
public class MsgBaseType {


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

}

package com.ukayunnuo.fun.feishu.project.msgtype;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * 飞书机器人 发送图片 类型
 *
 * @author ukayunnuo
 * @since 1.0.0
 */
@Data
public class ImageType {

    @JSONField(name = "image_key")
    private String imageKey;

}

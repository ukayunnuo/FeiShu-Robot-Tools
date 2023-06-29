package com.ukayunnuo.fun.feishu.project.msgtype;

import com.ukayunnuo.fun.feishu.project.base.TextContentDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 飞书机器人 文本 类型
 *
 * @author ukayunnuo
 * @since 1.0.0
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class TextMsgType extends TextContentDTO {

    public TextMsgType(String text) {
        this.setText(text);
    }

}

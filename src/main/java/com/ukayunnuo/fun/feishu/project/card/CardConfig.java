package com.ukayunnuo.fun.feishu.project.card;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 卡片属性
 *
 * @author ukayunnuo
 * @since 1.0.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CardConfig {

    @JSONField(name = "enable_forward")
    private Boolean enableForward = Boolean.TRUE;

    @JSONField(name = "update_multi")
    private Boolean updateMulti = Boolean.FALSE ;

    @Deprecated
    @JSONField(name = "wide_screen_mode")
    private Boolean wideScreenMode;

    public CardConfig(Boolean enableForward, Boolean updateMulti) {
        this.enableForward = enableForward;
        this.updateMulti = updateMulti;
    }
}


package com.ukayunnuo.fun.feishu.project.base;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.annotation.JSONField;
import com.alibaba.fastjson2.annotation.JSONType;
import com.ukayunnuo.fun.feishu.enums.AtType;
import com.ukayunnuo.fun.feishu.enums.RichTagType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Locale;

/**
 * 富文本基础内容
 *
 * @author ukayunnuo
 * @since 1.0.0
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@JSONType(orders = {"tag", "text", "href", "user_id", "user_name", "image_key"})
public class RichTextContentDto extends TextContentDTO {

    private RichTagType tag;

    private String href;

    @JSONField(name = "user_id")
    private String userId;


    @JSONField(name = "user_name")
    private String userName;

    @JSONField(name = "image_key")
    private String imageKey;

    private RichTextContentDto(Builder builder) {
        this.setText(builder.text);
        this.tag = builder.tag;
        this.href = builder.href;
        this.userId = builder.userId;
        this.userName = builder.userName;
        this.imageKey = builder.imageKey;
    }

    public static RichTextContentDto setTextTag(String text) {
        return new RichTextContentDto.Builder()
                .setTag(RichTagType.text)
                .setText(text)
                .build();
    }

    public static RichTextContentDto setHyperlinkTag(String href, String text) {
        return new RichTextContentDto.Builder()
                .setTag(RichTagType.a)
                .setText(text)
                .setHref(href)
                .build();
    }

    public static RichTextContentDto setAtSingleUserTag(String userId, String userName) {
        RichTextContentDto build = new Builder()
                .setTag(RichTagType.at)
                .setUserId(userId)
                .build();
        if (StrUtil.isNotBlank(userName)) {
            build.setUserName(StrUtil.emptyIfNull(userName));
        }
        return build;
    }

    public static RichTextContentDto setAtAllTag() {
        return new Builder()
                .setTag(RichTagType.at)
                .setUserId(AtType.ALL.name().toLowerCase(Locale.ROOT))
                .build();
    }

    public static RichTextContentDto setImageTag(String imageKey) {
        return new RichTextContentDto.Builder()
                .setTag(RichTagType.img)
                .setImageKey(imageKey)
                .build();
    }

    public static class Builder {

        private String text;
        private RichTagType tag;
        private String href;
        @JSONField(name = "user_id")
        private String userId;
        @JSONField(name = "user_name")
        private String userName;

        @JSONField(name = "image_key")
        private String imageKey;

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setTag(RichTagType tag) {
            this.tag = tag;
            return this;
        }

        public Builder setHref(String href) {
            this.href = href;
            return this;
        }

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder setImageKey(String imageKey) {
            this.imageKey = imageKey;
            return this;
        }

        public RichTextContentDto build() {
            return new RichTextContentDto(this);
        }
    }
}

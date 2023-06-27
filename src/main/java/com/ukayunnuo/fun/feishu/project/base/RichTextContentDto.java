package com.ukayunnuo.fun.feishu.project.base;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 富文本基础内容
 *
 * @author ukayunnuo
 * @since 1.0.0
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class RichTextContentDto extends TextContentDTO {

    private String tag;

    private String href;

    @JSONField(name = "user_id")
    private String userId;


    @JSONField(name = "user_name")
    private String userName;

    private RichTextContentDto(Builder builder) {
        this.setText(builder.text);
        this.tag = builder.tag;
        this.href = builder.href;
        this.userId = builder.userId;
        this.userName = builder.userName;
    }

    public static class Builder {

        private String text;
        private String tag;
        private String href;
        @JSONField(name = "user_id")
        private String userId;
        @JSONField(name = "user_name")
        private String userName;


        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setTag(String tag) {
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

        public RichTextContentDto build() {
            return new RichTextContentDto(this);
        }
    }
}

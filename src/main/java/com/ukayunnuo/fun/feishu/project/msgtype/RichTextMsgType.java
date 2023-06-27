package com.ukayunnuo.fun.feishu.project.msgtype;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.annotation.JSONField;
import com.ukayunnuo.fun.feishu.project.base.RichTextContentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 飞书机器人 富文本 类型
 *
 * @author ukayunnuo
 * @since 1.0.0
 */
@AllArgsConstructor
@Data
public class RichTextMsgType {

    private RichTextPostDTO post;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

    @Getter
    @Setter
    public static class RichTextPostDTO {

        @JSONField(name = "zh_cn")
        private RichTextPostDto zhCn;

        @JSONField(name = "en_us")
        private RichTextPostDto enUs;

        public RichTextPostDTO(RichTextPostDto zhCn) {
            this.zhCn = zhCn;
        }

        public RichTextPostDTO(RichTextPostDto zhCn, RichTextPostDto enUs) {
            this.zhCn = zhCn;
            this.enUs = enUs;
        }

        @Getter
        @Setter
        @AllArgsConstructor
        public static class RichTextPostDto {

            private String title;

            @JSONField(name = "content")
            private List<RichTextContentDto> content;

        }

    }


}

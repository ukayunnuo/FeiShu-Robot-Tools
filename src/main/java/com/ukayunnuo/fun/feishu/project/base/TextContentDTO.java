package com.ukayunnuo.fun.feishu.project.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * text 内容dto
 *
 * @author yunnuo
 * @since 1.0
 */
@Getter
@Setter
@ToString
public class TextContentDTO implements Serializable {

    private String text;

}

package com.yourtion.springcloud.ad.constant;

import lombok.Getter;

/**
 * @author yourtion
 */
@Getter
public enum CreativeType {

    /**
     * 图片
     */
    IMAGE(1, "图片"),
    /**
     * 视频
     */
    VIDEO(2, "视频"),
    /**
     * 文本
     */
    TEXT(3, "文本");

    private int type;
    private String desc;

    CreativeType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}

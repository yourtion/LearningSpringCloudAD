package com.yourtion.springcloud.ad.constant;

import lombok.Getter;

/**
 * @author yourtion
 */
@Getter
public enum CreativeMetrialType {

    /**
     * jpg
     */
    JPG(1, "jpg"),
    /**
     * bmp
     */
    BMP(2, "bmp"),

    /**
     * mp4
     */
    MP4(3, "mp4"),
    /**
     * avi
     */
    AVI(4, "avi"),

    /**
     * txt
     */
    TXT(5, "txt");

    private int type;
    private String desc;

    CreativeMetrialType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}

package com.yourtion.springcloud.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 设备信息
 *
 * @author yourtion
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Device {

    /**
     * 设备 ID
     */
    private String deviceCode;

    /**
     * Mac 地址
     */
    private String mac;

    /**
     * IP
     */
    private String ip;

    /**
     * 机型编号
     */
    private String mode;

    /**
     * 分辨率信息
     */
    private String displaySize;

    /**
     * 屏幕信息
     */
    private String screenSize;

    /**
     * 序列号
     */
    private String serialName;
}

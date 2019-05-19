package com.yourtion.springcloud.ad.index;

import lombok.Getter;


/**
 * @author yourtion
 */
@Getter
public enum DataLevel {

    /**
     * level 2
     */
    LEVEL2("2", "level 2"),
    /**
     * level 3
     */
    LEVEL3("3", "level 3"),
    /**
     * level 4
     */
    LEVEL4("4", "level 4");

    private String level;
    private String desc;

    DataLevel(String level, String desc) {
        this.level = level;
        this.desc = desc;
    }
}

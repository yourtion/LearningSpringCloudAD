package com.yourtion.springcloud.ad.search.vo;

import com.yourtion.springcloud.ad.index.creative.CreativeObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.var;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yourtion
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {

    public Map<String, List<Creative>> adSlot2Ads = new HashMap<>();

    public static Creative convert(CreativeObject obj) {
        var creative = new Creative();
        creative.setAdId(obj.getAdId());
        creative.setAdUrl(obj.getAdUrl());
        creative.setWidth(obj.getWidth());
        creative.setHeaght(obj.getHeight());
        creative.setType(obj.getType());
        creative.setMaterialType(obj.getMaterialType());
        return creative;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Creative {

        private Long adId;
        private String adUrl;
        private Integer width;
        private Integer heaght;
        private Integer type;
        private Integer materialType;

        // URL 临时写死
        /**
         * 展示监测 URL
         */
        private List<String> showMonitorUrl = Arrays.asList("www.yourtion.com", "blog.yourtion.com");
        /**
         * 点击监测 URL
         */
        private List<String> clickMonitorUrl = Arrays.asList("www.yourtion.com", "blog.yourtion.com");
    }
}

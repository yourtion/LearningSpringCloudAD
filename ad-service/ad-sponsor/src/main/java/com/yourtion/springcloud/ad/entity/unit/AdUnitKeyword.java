package com.yourtion.springcloud.ad.entity.unit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author yourtion
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ad_unit_keyword")
public class AdUnitKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "unit_id", nullable = false)
    private Long unitId;

    @Basic
    @Column(name = "keyword", nullable = false)
    private String keyword;

    public AdUnitKeyword(Long unitId, String keyword) {
        this.unitId = unitId;
        this.keyword = keyword;
    }
}

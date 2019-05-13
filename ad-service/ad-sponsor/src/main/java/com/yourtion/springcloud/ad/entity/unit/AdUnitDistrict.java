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
@Table(name = "ad_unit_district")
public class AdUnitDistrict {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "unit_id", nullable = false)
    private Long unitId;

    @Basic
    @Column(name = "province", nullable = false)
    private String province;

    @Basic
    @Column(name = "city", nullable = false)
    private String city;

    public AdUnitDistrict(Long unitId, String province, String city) {
        this.unitId = unitId;
        this.province = province;
        this.city = city;
    }
}

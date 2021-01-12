package com.kingdee.model.po;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name = "zjb_xstd_h")
public class SaleOrder {
    @Id
    @Column(name = "vfid")
    private String vfid;
    @Column(name = "vgsdm")
    private String vgsdm;
    @Column(name = "cqj")
    private String cqj;
    @Column(name = "vxstdbh")
    private String vxstdbh;
    @Column(name = "vkhbh")
    private String vkhbh;
    @Column(name = "cdjrq")
    private String cdjrq;
    @Column(name = "vhblx")
    private String vhblx;
    @Column(name = "nzhhl")
    private BigDecimal nzhhl;
    @Column(name = "vtdzt")
    private String vtdzt;
    @Column(name = "dcjrq")
    private Date dcjrq;
    @Column(name = "dxgrq")
    private Date dxgrq;
    @Column(name = "vywy")
    private String vywy;
    @Column(name = "vxslx")
    private String vxslx;
}

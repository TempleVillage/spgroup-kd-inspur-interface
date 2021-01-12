package com.kingdee.model.po;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name = "zjb_xstd_b")
public class SaleOrderEntry {
    @Id
    @Column(name = "vfid")
    private String vfid;
    @Column(name = "vgsdm")
    private String vgsdm;
    @Column(name = "vtdbh")
    private String vtdbh;
    @Column(name = "vtdxmh")
    private String vtdxmh;
    @Column(name = "vwlbm")
    private String vwlbm;
    @Column(name = "nxssl")
    private BigDecimal nxssl;
    @Column(name = "vjldw")
    private String vjldw;
    @Column(name = "nxsje")
    private BigDecimal nxsje;
    @Column(name = "nhsxsje")
    private BigDecimal nhsxsje;
    @Column(name = "dcjrq")
    private Date dcjrq;
    @Column(name = "dxgrq")
    private Date dxgrq;
}

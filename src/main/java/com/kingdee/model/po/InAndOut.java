package com.kingdee.model.po;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 出入库
 */
@Data
@Table(name = "ZJB_CRK")
public class InAndOut {
    @Id
    private String vfid;
    private String vgsdm;
    private String vdjh;
    private String vxmh;
    private String vydlx;
    private String vwlbm;
    private String vpch;
    private BigDecimal nsl;
    private String vjldw;
    private BigDecimal nje;
    private String vhblx;
    private BigDecimal nzhhl;
    private String ccrkrq;
    private Date dcjrq;
    private Date dxgrq;
}

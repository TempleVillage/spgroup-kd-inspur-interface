package com.kingdee.model.po;

import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "zjb_kcye")
public class Inventory {
    @Id
    private String vfid;

    private String vgsdm;

    private String vwlbm;

    private String vpch;

    private BigDecimal nje;

    private BigDecimal nsl;

    private String vjldw;

    private String crkrq;

    private Date dcjrq;

    private Date dxgrq;

    private String vywrq;

    private String vqj;

    /**
     * @return vfid
     */
    public String getVfid() {
        return vfid;
    }

    /**
     * @param vfid
     */
    public void setVfid(String vfid) {
        this.vfid = vfid;
    }

    /**
     * @return vgsdm
     */
    public String getVgsdm() {
        return vgsdm;
    }

    /**
     * @param vgsdm
     */
    public void setVgsdm(String vgsdm) {
        this.vgsdm = vgsdm;
    }

    /**
     * @return vwlbm
     */
    public String getVwlbm() {
        return vwlbm;
    }

    /**
     * @param vwlbm
     */
    public void setVwlbm(String vwlbm) {
        this.vwlbm = vwlbm;
    }

    /**
     * @return vpch
     */
    public String getVpch() {
        return vpch;
    }

    /**
     * @param vpch
     */
    public void setVpch(String vpch) {
        this.vpch = vpch;
    }

    /**
     * @return nje
     */
    public BigDecimal getNje() {
        return nje;
    }

    /**
     * @param nje
     */
    public void setNje(BigDecimal nje) {
        this.nje = nje;
    }

    /**
     * @return nsl
     */
    public BigDecimal getNsl() {
        return nsl;
    }

    /**
     * @param nsl
     */
    public void setNsl(BigDecimal nsl) {
        this.nsl = nsl;
    }

    /**
     * @return vjldw
     */
    public String getVjldw() {
        return vjldw;
    }

    /**
     * @param vjldw
     */
    public void setVjldw(String vjldw) {
        this.vjldw = vjldw;
    }

    /**
     * @return crkrq
     */
    public String getCrkrq() {
        return crkrq;
    }

    /**
     * @param crkrq
     */
    public void setCrkrq(String crkrq) {
        this.crkrq = crkrq;
    }

    /**
     * @return dcjrq
     */
    public Date getDcjrq() {
        return dcjrq;
    }

    /**
     * @param dcjrq
     */
    public void setDcjrq(Date dcjrq) {
        this.dcjrq = dcjrq;
    }

    /**
     * @return dxgrq
     */
    public Date getDxgrq() {
        return dxgrq;
    }

    /**
     * @param dxgrq
     */
    public void setDxgrq(Date dxgrq) {
        this.dxgrq = dxgrq;
    }

    /**
     * @return vywrq
     */
    public String getVywrq() {
        return vywrq;
    }

    /**
     * @param vywrq
     */
    public void setVywrq(String vywrq) {
        this.vywrq = vywrq;
    }

    /**
     * @return vqj
     */
    public String getVqj() {
        return vqj;
    }

    /**
     * @param vqj
     */
    public void setVqj(String vqj) {
        this.vqj = vqj;
    }
}
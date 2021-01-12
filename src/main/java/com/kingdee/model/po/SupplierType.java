package com.kingdee.model.po;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "zjb_dmb_gysfl")
public class SupplierType {
    @Id
    private String vfid;

    private String vgsdm;

    private String vdm;

    private String vmc;

    private Date dcjrq;

    private Date dxgrq;

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
     * @return vdm
     */
    public String getVdm() {
        return vdm;
    }

    /**
     * @param vdm
     */
    public void setVdm(String vdm) {
        this.vdm = vdm;
    }

    /**
     * @return vmc
     */
    public String getVmc() {
        return vmc;
    }

    /**
     * @param vmc
     */
    public void setVmc(String vmc) {
        this.vmc = vmc;
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
}
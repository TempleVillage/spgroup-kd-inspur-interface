package com.kingdee.model.po;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "zjb_khzsj")
public class Customer {
    @Id
    private String vfid;

    private String vgsdm;

    private String vkhbm;

    private String vkhmc;

    private String vszgj;

    private String vszss;

    private String vkhfldm;

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
     * @return vkhbm
     */
    public String getVkhbm() {
        return vkhbm;
    }

    /**
     * @param vkhbm
     */
    public void setVkhbm(String vkhbm) {
        this.vkhbm = vkhbm;
    }

    /**
     * @return vkhmc
     */
    public String getVkhmc() {
        return vkhmc;
    }

    /**
     * @param vkhmc
     */
    public void setVkhmc(String vkhmc) {
        this.vkhmc = vkhmc;
    }

    /**
     * @return vszgj
     */
    public String getVszgj() {
        return vszgj;
    }

    /**
     * @param vszgj
     */
    public void setVszgj(String vszgj) {
        this.vszgj = vszgj;
    }

    /**
     * @return vszss
     */
    public String getVszss() {
        return vszss;
    }

    /**
     * @param vszss
     */
    public void setVszss(String vszss) {
        this.vszss = vszss;
    }

    /**
     * @return vkhfldm
     */
    public String getVkhfldm() {
        return vkhfldm;
    }

    /**
     * @param vkhfldm
     */
    public void setVkhfldm(String vkhfldm) {
        this.vkhfldm = vkhfldm;
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
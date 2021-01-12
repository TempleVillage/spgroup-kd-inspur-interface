package com.kingdee.model.po;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "zjb_wlzsj")
public class Material {
	
    @Id
    @Column(name = "vfid")
    private String vfid;

    @Column(name = "vgsdm")
    private String vgsdm;

    @Column(name = "vwlbm")
    private String vwlbm;

    @Column(name = "vwlmc")
    private String vwlmc;

    @Column(name = "vgg")
    private String vgg;

    @Column(name = "vwlfl")
    private String vwlfl;

    @Column(name = "vjldw")
    private String vjldw;

    @Column(name = "dcjrq")
    private Date dcjrq;

    @Column(name = "dxgrq")
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
     * @return vwlmc
     */
    public String getVwlmc() {
        return vwlmc;
    }

    /**
     * @param vwlmc
     */
    public void setVwlmc(String vwlmc) {
        this.vwlmc = vwlmc;
    }

    /**
     * @return vgg
     */
    public String getVgg() {
        return vgg;
    }

    /**
     * @param vgg
     */
    public void setVgg(String vgg) {
        this.vgg = vgg;
    }

    /**
     * @return vwlfl
     */
    public String getVwlfl() {
        return vwlfl;
    }

    /**
     * @param vwlfl
     */
    public void setVwlfl(String vwlfl) {
        this.vwlfl = vwlfl;
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
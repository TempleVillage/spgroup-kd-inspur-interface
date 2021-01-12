package com.kingdee.model.po;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "zjb_gyszsj")
@Data
public class Supplier {
    @Id
    private String vfid;

    private String vgsdm;

    private String vgysbm;

    private String vgysmc;

    private String vszgj;

    private String vszss;

    private String vgysfldm;

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
     * @return vgysbm
     */
    public String getVgysbm() {
        return vgysbm;
    }

    /**
     * @param vgysbm
     */
    public void setVgysbm(String vgysbm) {
        this.vgysbm = vgysbm;
    }

    /**
     * @return vgysmc
     */
    public String getVgysmc() {
        return vgysmc;
    }

    /**
     * @param vgysmc
     */
    public void setVgysmc(String vgysmc) {
        this.vgysmc = vgysmc;
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
     * @return vgysfldm
     */
    public String getVgysfldm() {
        return vgysfldm;
    }

    /**
     * @param vgysfldm
     */
    public void setVgysfldm(String vgysfldm) {
        this.vgysfldm = vgysfldm;
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
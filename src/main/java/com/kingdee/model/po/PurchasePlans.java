package com.kingdee.model.po;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "PURCHASEPLANS")
public class PurchasePlans {
    @Id
    @Column(name = "K_PURCHASEPLANS")
    private String kPurchaseplans;

    @Column(name = "MATERIALCODE")
    private String materialcode;

    @Column(name = "MATERIALNAME")
    private String materialname;

    @Column(name = "SPEC")
    private String spec;

    @Column(name = "PRIPURUNITID")
    private String pripurunitid;

    @Column(name = "PLANCOMPANYID")
    private String plancompanyid;

    @Column(name = "CREATOR")
    private String creator;

    @Column(name = "QUANTITY")
    private BigDecimal quantity;

    @Column(name = "ERPPRBILLCODE")
    private String erpprbillcode;

    @Column(name = "ERPPRBILLITEMCODE")
    private String erpprbillitemcode;

    @Column(name = "FISCALYEAR")
    private String fiscalyear;

    @Column(name = "FISCALPERIOD")
    private String fiscalperiod;

    @Column(name = "GSLASTTIME")
    private Date gslasttime;

    @Column(name = "ERPLASTTIME")
    private Date erplasttime;

    @Column(name = "ERPCODE")
    private String erpcode;

    @Column(name = "PLANSTATE")
    private String planstate;

    @Column(name = "WRONGSTSTE")
    private String wrongstste;

    @Column(name = "TEXT1")
    private String text1;

    @Column(name = "TEXT2")
    private String text2;

    @Column(name = "TEXT3")
    private String text3;

    @Column(name = "TEXT4")
    private String text4;

    @Column(name = "TEXT5")
    private String text5;

    @Column(name = "DATE1")
    private Date date1;

    @Column(name = "DATE2")
    private Date date2;

    /**
     * @return K_PURCHASEPLANS
     */
    public String getkPurchaseplans() {
        return kPurchaseplans;
    }

    /**
     * @param kPurchaseplans
     */
    public void setkPurchaseplans(String kPurchaseplans) {
        this.kPurchaseplans = kPurchaseplans;
    }

    /**
     * @return MATERIALCODE
     */
    public String getMaterialcode() {
        return materialcode;
    }

    /**
     * @param materialcode
     */
    public void setMaterialcode(String materialcode) {
        this.materialcode = materialcode;
    }

    /**
     * @return MATERIALNAME
     */
    public String getMaterialname() {
        return materialname;
    }

    /**
     * @param materialname
     */
    public void setMaterialname(String materialname) {
        this.materialname = materialname;
    }

    /**
     * @return SPEC
     */
    public String getSpec() {
        return spec;
    }

    /**
     * @param spec
     */
    public void setSpec(String spec) {
        this.spec = spec;
    }

    /**
     * @return PRIPURUNITID
     */
    public String getPripurunitid() {
        return pripurunitid;
    }

    /**
     * @param pripurunitid
     */
    public void setPripurunitid(String pripurunitid) {
        this.pripurunitid = pripurunitid;
    }

    /**
     * @return PLANCOMPANYID
     */
    public String getPlancompanyid() {
        return plancompanyid;
    }

    /**
     * @param plancompanyid
     */
    public void setPlancompanyid(String plancompanyid) {
        this.plancompanyid = plancompanyid;
    }

    /**
     * @return CREATOR
     */
    public String getCreator() {
        return creator;
    }

    /**
     * @param creator
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * @return QUANTITY
     */
    public BigDecimal getQuantity() {
        return quantity;
    }

    /**
     * @param quantity
     */
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    /**
     * @return ERPPRBILLCODE
     */
    public String getErpprbillcode() {
        return erpprbillcode;
    }

    /**
     * @param erpprbillcode
     */
    public void setErpprbillcode(String erpprbillcode) {
        this.erpprbillcode = erpprbillcode;
    }

    /**
     * @return ERPPRBILLITEMCODE
     */
    public String getErpprbillitemcode() {
        return erpprbillitemcode;
    }

    /**
     * @param erpprbillitemcode
     */
    public void setErpprbillitemcode(String erpprbillitemcode) {
        this.erpprbillitemcode = erpprbillitemcode;
    }

    /**
     * @return FISCALYEAR
     */
    public String getFiscalyear() {
        return fiscalyear;
    }

    /**
     * @param fiscalyear
     */
    public void setFiscalyear(String fiscalyear) {
        this.fiscalyear = fiscalyear;
    }

    /**
     * @return FISCALPERIOD
     */
    public String getFiscalperiod() {
        return fiscalperiod;
    }

    /**
     * @param fiscalperiod
     */
    public void setFiscalperiod(String fiscalperiod) {
        this.fiscalperiod = fiscalperiod;
    }

    /**
     * @return GSLASTTIME
     */
    public Date getGslasttime() {
        return gslasttime;
    }

    /**
     * @param gslasttime
     */
    public void setGslasttime(Date gslasttime) {
        this.gslasttime = gslasttime;
    }

    /**
     * @return ERPLASTTIME
     */
    public Date getErplasttime() {
        return erplasttime;
    }

    /**
     * @param erplasttime
     */
    public void setErplasttime(Date erplasttime) {
        this.erplasttime = erplasttime;
    }

    /**
     * @return ERPCODE
     */
    public String getErpcode() {
        return erpcode;
    }

    /**
     * @param erpcode
     */
    public void setErpcode(String erpcode) {
        this.erpcode = erpcode;
    }

    /**
     * @return PLANSTATE
     */
    public String getPlanstate() {
        return planstate;
    }

    /**
     * @param planstate
     */
    public void setPlanstate(String planstate) {
        this.planstate = planstate;
    }

    /**
     * @return WRONGSTSTE
     */
    public String getWrongstste() {
        return wrongstste;
    }

    /**
     * @param wrongstste
     */
    public void setWrongstste(String wrongstste) {
        this.wrongstste = wrongstste;
    }

    /**
     * @return TEXT1
     */
    public String getText1() {
        return text1;
    }

    /**
     * @param text1
     */
    public void setText1(String text1) {
        this.text1 = text1;
    }

    /**
     * @return TEXT2
     */
    public String getText2() {
        return text2;
    }

    /**
     * @param text2
     */
    public void setText2(String text2) {
        this.text2 = text2;
    }

    /**
     * @return TEXT3
     */
    public String getText3() {
        return text3;
    }

    /**
     * @param text3
     */
    public void setText3(String text3) {
        this.text3 = text3;
    }

    /**
     * @return TEXT4
     */
    public String getText4() {
        return text4;
    }

    /**
     * @param text4
     */
    public void setText4(String text4) {
        this.text4 = text4;
    }

    /**
     * @return TEXT5
     */
    public String getText5() {
        return text5;
    }

    /**
     * @param text5
     */
    public void setText5(String text5) {
        this.text5 = text5;
    }

    /**
     * @return DATE1
     */
    public Date getDate1() {
        return date1;
    }

    /**
     * @param date1
     */
    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    /**
     * @return DATE2
     */
    public Date getDate2() {
        return date2;
    }

    /**
     * @param date2
     */
    public void setDate2(Date date2) {
        this.date2 = date2;
    }
}
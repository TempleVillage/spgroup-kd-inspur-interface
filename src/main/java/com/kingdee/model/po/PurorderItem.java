package com.kingdee.model.po;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "PURORDERITEM")
public class PurorderItem {
    @Id
    @Column(name = "K_PURORDERITEM")
    private String kPurorderitem;

    @Column(name = "PURORDERID")
    private String purorderid;

    @Column(name = "PURORDERITEMID")
    private String purorderitemid;

    @Column(name = "ERPPRBILLCODE")
    private String erpprbillcode;

    @Column(name = "ERPPRBILLITEMCODE")
    private String erpprbillitemcode;

    @Column(name = "MATERIALID")
    private String materialid;

    @Column(name = "MATERIALCODE")
    private String materialcode;

    @Column(name = "MATERIALNAME")
    private String materialname;

    @Column(name = "SPECS")
    private String specs;

    @Column(name = "QUANTITY")
    private BigDecimal quantity;

    @Column(name = "PURUNITID")
    private String purunitid;

    @Column(name = "TAXINPRICE")
    private BigDecimal taxinprice;

    @Column(name = "COMPANYID")
    private String companyid;

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
     * @return K_PURORDERITEM
     */
    public String getkPurorderitem() {
        return kPurorderitem;
    }

    /**
     * @param kPurorderitem
     */
    public void setkPurorderitem(String kPurorderitem) {
        this.kPurorderitem = kPurorderitem;
    }

    /**
     * @return PURORDERID
     */
    public String getPurorderid() {
        return purorderid;
    }

    /**
     * @param purorderid
     */
    public void setPurorderid(String purorderid) {
        this.purorderid = purorderid;
    }

    /**
     * @return PURORDERITEMID
     */
    public String getPurorderitemid() {
        return purorderitemid;
    }

    /**
     * @param purorderitemid
     */
    public void setPurorderitemid(String purorderitemid) {
        this.purorderitemid = purorderitemid;
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
     * @return MATERIALID
     */
    public String getMaterialid() {
        return materialid;
    }

    /**
     * @param materialid
     */
    public void setMaterialid(String materialid) {
        this.materialid = materialid;
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
     * @return SPECS
     */
    public String getSpecs() {
        return specs;
    }

    /**
     * @param specs
     */
    public void setSpecs(String specs) {
        this.specs = specs;
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
     * @return PURUNITID
     */
    public String getPurunitid() {
        return purunitid;
    }

    /**
     * @param purunitid
     */
    public void setPurunitid(String purunitid) {
        this.purunitid = purunitid;
    }

    /**
     * @return TAXINPRICE
     */
    public BigDecimal getTaxinprice() {
        return taxinprice;
    }

    /**
     * @param taxinprice
     */
    public void setTaxinprice(BigDecimal taxinprice) {
        this.taxinprice = taxinprice;
    }

    /**
     * @return COMPANYID
     */
    public String getCompanyid() {
        return companyid;
    }

    /**
     * @param companyid
     */
    public void setCompanyid(String companyid) {
        this.companyid = companyid;
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
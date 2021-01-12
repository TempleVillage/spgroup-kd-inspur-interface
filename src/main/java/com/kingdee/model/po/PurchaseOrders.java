package com.kingdee.model.po;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "PURCHASEORDERS")
public class PurchaseOrders {
    @Id
    @Column(name = "K_PURCHASEORDERS")
    private String kPurchaseorders;

    @Column(name = "PURORDERCODE")
    private String purordercode;

    @Column(name = "PURORDERID")
    private String purorderid;

    @Column(name = "CREATEDATE")
    private String createdate;

    @Column(name = "FISCALYEAR")
    private String fiscalyear;

    @Column(name = "FISCALPERIOD")
    private String fiscalperiod;

    @Column(name = "VENDORCODE")
    private String vendorcode;

    @Column(name = "VENDORADDRESS")
    private String vendoraddress;

    @Column(name = "VENDORLINKMAN")
    private String vendorlinkman;

    @Column(name = "LSWLDW_WLDWBH")
    private String lswldwWldwbh;

    @Column(name = "LSWLDW_DWMC")
    private String lswldwDwmc;

    @Column(name = "LSWLDW_CERTIFICATE")
    private String lswldwCertificate;

    @Column(name = "CREATOR")
    private String creator;

    @Column(name = "TOTALTAXINVALUE")
    private BigDecimal totaltaxinvalue;

    @Column(name = "CURRENCY")
    private String currency;

    @Column(name = "ORDERSTATE")
    private String orderstate;

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
     * @return K_PURCHASEORDERS
     */
    public String getkPurchaseorders() {
        return kPurchaseorders;
    }

    /**
     * @param kPurchaseorders
     */
    public void setkPurchaseorders(String kPurchaseorders) {
        this.kPurchaseorders = kPurchaseorders;
    }

    /**
     * @return PURORDERCODE
     */
    public String getPurordercode() {
        return purordercode;
    }

    /**
     * @param purordercode
     */
    public void setPurordercode(String purordercode) {
        this.purordercode = purordercode;
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
     * @return CREATEDATE
     */
    public String getCreatedate() {
        return createdate;
    }

    /**
     * @param createdate
     */
    public void setCreatedate(String createdate) {
        this.createdate = createdate;
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
     * @return VENDORCODE
     */
    public String getVendorcode() {
        return vendorcode;
    }

    /**
     * @param vendorcode
     */
    public void setVendorcode(String vendorcode) {
        this.vendorcode = vendorcode;
    }

    /**
     * @return VENDORADDRESS
     */
    public String getVendoraddress() {
        return vendoraddress;
    }

    /**
     * @param vendoraddress
     */
    public void setVendoraddress(String vendoraddress) {
        this.vendoraddress = vendoraddress;
    }

    /**
     * @return VENDORLINKMAN
     */
    public String getVendorlinkman() {
        return vendorlinkman;
    }

    /**
     * @param vendorlinkman
     */
    public void setVendorlinkman(String vendorlinkman) {
        this.vendorlinkman = vendorlinkman;
    }

    /**
     * @return LSWLDW_WLDWBH
     */
    public String getLswldwWldwbh() {
        return lswldwWldwbh;
    }

    /**
     * @param lswldwWldwbh
     */
    public void setLswldwWldwbh(String lswldwWldwbh) {
        this.lswldwWldwbh = lswldwWldwbh;
    }

    /**
     * @return LSWLDW_DWMC
     */
    public String getLswldwDwmc() {
        return lswldwDwmc;
    }

    /**
     * @param lswldwDwmc
     */
    public void setLswldwDwmc(String lswldwDwmc) {
        this.lswldwDwmc = lswldwDwmc;
    }

    /**
     * @return LSWLDW_CERTIFICATE
     */
    public String getLswldwCertificate() {
        return lswldwCertificate;
    }

    /**
     * @param lswldwCertificate
     */
    public void setLswldwCertificate(String lswldwCertificate) {
        this.lswldwCertificate = lswldwCertificate;
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
     * @return TOTALTAXINVALUE
     */
    public BigDecimal getTotaltaxinvalue() {
        return totaltaxinvalue;
    }

    /**
     * @param totaltaxinvalue
     */
    public void setTotaltaxinvalue(BigDecimal totaltaxinvalue) {
        this.totaltaxinvalue = totaltaxinvalue;
    }

    /**
     * @return CURRENCY
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * @return ORDERSTATE
     */
    public String getOrderstate() {
        return orderstate;
    }

    /**
     * @param orderstate
     */
    public void setOrderstate(String orderstate) {
        this.orderstate = orderstate;
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
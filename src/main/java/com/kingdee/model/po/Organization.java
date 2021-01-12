package com.kingdee.model.po;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 组织机构
 */
@Data
@Table(name = "ZJB_DMB_ZZ")
public class Organization {
    @Id
    private String vfid;
    private String vgsdm;
    private String vdm;
    private String vmc;
    private Date dcjrq;
    private Date dxgrq;

}

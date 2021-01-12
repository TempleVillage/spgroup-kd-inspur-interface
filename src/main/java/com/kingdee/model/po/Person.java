package com.kingdee.model.po;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "ZJB_DMB_YWY")
public class Person {
    @Id
    private String vfid;
    private String vgsdm;
    private String vdm;
    private String vmc;
    private Date dcjrq;
    private Date dxgrq;
}

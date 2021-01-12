package com.kingdee.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jambin
 * @description 浪潮数据同步接口相关参数
 * @date 2020/11/12 11:55
 */
public class InspurConstant {
    //公司代码
    public static final String LMGSDM = "2221";

    public static final String GDZYGSDM = "3159";


    //================================k3cloud===================================
    //星空地址
    public static final String K3CLOUDADDRESS = "http://erp.liminhot.com/K3Cloud/";
    //星空用户
    public static final String USER = "user";
    //星空密码
    public static final String PASSWORD = "222222";
    //星空dbId
    public static final String DBID = "5b724f091adc82";
    //星空lcid
    public static final Integer LCID = 2052;


    //================================k3wise===================================
    //+++++++++++++++++++++单据code=+++++++++++++++++++++
    //供应商
    public static final String SUPPLIER = "Supplier";
    //供应商组
    public static final String SUPPLIERGROUP = "SupplierGroup";
    //组织
    public static final String ORGANIZATION = "BaseItem1001321";
    //客户
    public static final String CUSTOMER = "Customer";
    //客户分类
    public static final String CUSTOMERGROUP = "CustomerGroup";
    //物料
    public static final String MATERIAL = "Material";
    //物料分组
    public static final String MATERIALGROUP = "MaterialGroup";
    //国别地区
    public static final String GBDQ = "ItemClass2026";
    //币别
    public static final String CURRENCY = "Currency";
    //计量单位
    public static final String MEASUREUNIT = "MeasureUnit";
    //职员
    public static final String EMPLOYEE = "Employee";
    //销售订单
    public static final String SALEORDER = "SO";
    //盘盈（盘亏）(40/41)
    public static final String PYRK = "Inventory_Gain";
    public static final String PKCK = "Inventory_Loss";
    //其他入库（出库）(90/91)
    public static final String QTRK = "Miscellaneous_Receipt";
    public static final String QTCK = "Miscellaneous_Delivery";

    // TODO:  库存余额,   存货余额,    期初入库（出库）(10/11),    采购入库（销售出库）(20/21),   移库入库（出库）(30/31),   生产入库（出库）(50/51),

    //+++++++++++++++++++++WISE接口地址+++++++++++++++++++++
    public static final Map<String, String> WISEADDRESSMAP = new HashMap<String, String>();

    static {
        WISEADDRESSMAP.put("1a937d1a68db7c5ae8c511c57fe000ad2d5077ba0cfbab06", "http://218.69.112.158:8000/K3API");//广大纸业
//        WISEADDRESSMAP.put("0ecb7b340cd8660598a25a4a4bd5d940cb0ed76193b8b989", "http://172.23.70.252:16687/K3API");//钟澳（天津）奶牛有限公司
//        WISEADDRESSMAP.put("71dcfd6f3cd7e764981b73996d8f6ba80b1308224b2830b4", "http://172.23.70.252:16687/K3API");//嘉立荷（山东）牧业有限公司
//        WISEADDRESSMAP.put("cc7c2a733c0a29ec04f444605e93f704448993ef032f21f5", "http://172.23.70.252:16687/K3API");//天津嘉立荷牧业集团有限公司
//        WISEADDRESSMAP.put("6e7280885f4cfa9519b8ba746978b1f72c8890b81115b894", "http://172.23.70.252:16687/K3API");//嘉立荷唐山牧业有限公司
//        WISEADDRESSMAP.put("1a937d1a68db7c5a3bdeb5629745f7bc82f080335fd072a6", "http://172.23.70.252:16687/K3API");//天津嘉立荷饲料有限公司
    }

    //广大纸业
    public static final String GDZY_ADDRESS = "http://218.69.112.158:8000/K3API";
    public static final String GDZY_AUTHORITYCODE = "1a937d1a68db7c5ae8c511c57fe000ad2d5077ba0cfbab06";
}

package com.kingdee.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName InspurWCConstant
 * @Description TODO
 * @Autor Strong Li
 * @Date 11/19/2020 10:47
 * @Version V1.0
 **/
public class InspurWCConstant {
    //公司代码
    public static final String GSDM = "2279";
    //星空地址
    public static final String K3CLOUDADDRESS = "http://kd.dynasty.com.cn:11555/k3cloud/";
    //星空用户
    public static final String USER = "Administrator";
    //星空密码
    public static final String PASSWORD = "888888";
    //星空dbId 生产 5d5b886ac848de 测试 5f698d61600a4b 测试1125 5fc0dfe2cfa36f
    public static final String DBID = "5fc0dfe2cfa36f";
    //星空lcid
    public static final Integer LCID = 2052;
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
}
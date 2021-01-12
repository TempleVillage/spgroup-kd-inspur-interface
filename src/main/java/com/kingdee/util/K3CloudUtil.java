package com.kingdee.util;

import com.cloud.webapi.K3CloudApiClient;
import com.kingdee.constant.InspurConstant;
import com.kingdee.constant.InspurLMConstant;
import com.kingdee.constant.InspurWCConstant;

/**
 * @author Jambin and Stong Li
 * @description 星空工具类
 * @date 2020/11/12 11:45
 */
public class K3CloudUtil {
    /**
     * 获取解析链接
     * 获取k3 json对象
     *
     * @return
     */
    public static K3CloudApiClient getK3CloudApiClient() {

        K3CloudApiClient client = new K3CloudApiClient(InspurConstant.K3CLOUDADDRESS);
        try {
            Boolean resultType = client.login(InspurConstant.DBID, InspurConstant.USER, InspurConstant.PASSWORD, InspurConstant.LCID);
            if (resultType) {
                return client;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取利民 解析链接k3 json对象 服务与电采接口
     * @return
     */
    public static K3CloudApiClient getWCK3CloudApiClient() {

        K3CloudApiClient client = new K3CloudApiClient(InspurWCConstant.K3CLOUDADDRESS);
        try {
            Boolean resultType = client.login(InspurWCConstant.DBID, InspurWCConstant.USER, InspurWCConstant.PASSWORD, InspurWCConstant.LCID);
            if (resultType) {
                return client;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取利民 解析链接k3 json对象 服务与电采接口
     * @return
     */
    public static K3CloudApiClient getLMK3CloudApiClient() {

        K3CloudApiClient client = new K3CloudApiClient(InspurLMConstant.K3CLOUDADDRESS);
        try {
            Boolean resultType = client.login(InspurLMConstant.DBID, InspurLMConstant.USER, InspurLMConstant.PASSWORD, InspurLMConstant.LCID);
            if (resultType) {
                return client;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

package com.kingdee.util;


import cn.hutool.core.util.StrUtil;

/**
 * Title: URLTemplate Description: k3wise 请求URL模板
 *
 * @author yacong_liu Email:2682505646@qq.com
 * @date 2019年4月28日
 */
public class URLTemplate {

    /**
     * <p>
     * Title: getTemplate
     * </p>
     * <p>
     * Description: 获取模板URL
     * </p>
     *
     * @param gatewayHost   网关地址
     * @param itemClassCode 基础资料类型码
     * @param token         令牌
     * @return
     */
    public static String getTemplate(String gatewayHost, String itemClassCode, String token) {
        return URL(gatewayHost, itemClassCode, "GetTemplate", token);
    }

    /**
     * <p>
     * Title: getTemplate
     * </p>
     * <p>
     * Description: 保存URL
     * </p>
     *
     * @param gatewayHost   网关地址
     * @param itemClassCode 基础资料类型码
     * @param token         令牌
     * @return
     */
    public static String save(String gatewayHost, String itemClassCode, String token) {
        return URL(gatewayHost, itemClassCode, "Save", token);
    }

    /**
     * <p>
     * Title: delete
     * </p>
     * <p>
     * Description: 删除URL
     * </p>
     *
     * @param gatewayHost   网关地址
     * @param itemClassCode 基础资料类型码
     * @param token         令牌
     * @return
     */
    public static String delete(String gatewayHost, String itemClassCode, String token) {
        return URL(gatewayHost, itemClassCode, "Delete", token);
    }

    /**
     * <p>
     * Title: getTemplate
     * </p>
     * <p>
     * Description: 查询明细URL
     * </p>
     *
     * @param gatewayHost   网关地址
     * @param itemClassCode 基础资料类型码
     * @param token         令牌
     * @return
     */
    public static String getDetail(String gatewayHost, String itemClassCode, String token) {
        return URL(gatewayHost, itemClassCode, "GetDetail", token);
    }

    /**
     * <p>
     * Title: getTemplate
     * </p>
     * <p>
     * Description: 查询列表URL
     * </p>
     *
     * @param gatewayHost   网关地址
     * @param itemClassCode 基础资料类型码
     * @param token         令牌
     * @return
     */
    public static String getList(String gatewayHost, String itemClassCode, String token) {
        return URL(gatewayHost, itemClassCode, "GetList", token);
    }

    /**
     * <p>
     * Title: getTemplate
     * </p>
     * <p>
     * Description: 修改URL
     * </p>
     *
     * @param gatewayHost   网关地址
     * @param itemClassCode 基础资料类型码
     * @param token         令牌
     * @return
     */
    public static String update(String gatewayHost, String itemClassCode, String token) {
        return URL(gatewayHost, itemClassCode, "Update", token);
    }

    /**
     * <p>
     * Title: URL
     * </p>
     * <p>
     * Description: 请求URL
     * </p>
     *
     * @param gatewayHost   网关地址
     * @param itemClassCode 基础资料类型码
     * @param action        请求动作类型
     * @param token         令牌
     * @return URL
     */
    private static String URL(String gatewayHost, String itemClassCode, String action, String token) {

        if (StrUtil.isEmpty(gatewayHost) || StrUtil.isEmpty(itemClassCode) || StrUtil.isEmpty(action)
                || StrUtil.isEmpty(token)) {
            return null;
        }

        // return "http://218.69.112.158:8000/K3API/ItemClass2024/GetDetail?token=".concat(token);
        return gatewayHost.concat("/").concat(itemClassCode).concat("/").concat(action).concat("?token=")
                .concat(token);
    }

}

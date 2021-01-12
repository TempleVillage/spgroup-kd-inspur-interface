package com.kingdee.util;


import cn.hutool.core.util.StrUtil;

/**
 * Title: Template
 * <p>
 * Description: k3wise 参数模板
 *
 * @author yacong_liu Email:2682505646@qq.com
 * @date 2019年4月28日
 */
public class ParamTemplate {

    /**
     * <p>
     * Title: save
     * </p>
     * <p>
     * Description: 保存参数模板
     * </p>
     *
     * @param name   基础资料名称
     * @param number 基础资料编码
     * @return
     */
    public static String save(String name, String number) {
        return "{\"Data\":{\"FName\":'" + name + "',\"FNumber\":'" + number + "'}}";
    }

    /**
     * <p>
     * Title: update
     * </p>
     * <p>
     * Description: 修改参数模板
     * </p>
     *
     * @param itemNum    基础资料编码
     * @param itemName   基础资料名称
     * @param newItemNum 新基础资料编码 （若不修改编码，与itemNum保持一致即可）
     * @return
     */
    public static String update(String itemNum, String itemName, String newItemNum) {
        return "{\"Data\":{\"FNumber\":'" + itemNum + "',\"Data\":{\"FName\":'" + itemName + "',\"FNumber\":'"
                + newItemNum + "' }}}";
    }

    /**
     * <p>
     * Title: deleteTemplate
     * </p>
     * <p>
     * Description: 删除参数模板
     * </p>
     *
     * @param number 编码
     * @return
     */
    public static String delete(String number) {
        return "{\"Data\": {\"FNumber\": '" + number + "'},\"FDelete\": \"5\"}";
    }

    /**
     * <p>
     * Title: deleteTemplate
     * </p>
     * <p>
     * Description: 禁用 参数模板
     * </p>
     *
     * @param number 编码
     * @return
     */
    public static String forbid(String number) {
        return "{\"Data\": {\"FNumber\": '" + number + "'},\"FDelete\": \"1\"}";
    }

    /**
     * <p>
     * Title: searchTemplate
     * </p>
     * <p>
     * Description: 查询参数模板
     * </p>
     *
     * @param number 编码
     * @return
     */
    public static String getDetail(String number) {
        return commonTemplate(number);
    }

    /**
     * <p>
     * Title: listByFilter
     * </p>
     * <p>
     * Description: 过滤查询信息<br>
     * eg: F_115 = '11111111111111111' <br>
     * String filter = "F_115".concat("=").concat("'").concat("1111111111111111").concat("'");
     * </p>
     *
     * @param filter 过滤条件
     * @return
     */
    public static String listByFilter(String filter) {

        if (StrUtil.isEmpty(filter)) {
            return null;
        }

        return "{\"Data\": {\"Top\": \"1\", \"PageSize\": \"1\",\"PageIndex\": \"1\",\"Filter\": \"" + filter
                + "\",\"Fields\": \"\" }}";
    }

    private static String commonTemplate(String number) {

        if (StrUtil.isEmpty(number)) {
            return null;
        }
        return "{\"Data\": {\"FNumber\": '" + number + "'},\"GetProperty\": false" + "}";
    }

}

package com.kingdee.token;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <p>
 * 版权所有： 天津金蝶软件有限公司
 * <p>
 * Title: TokenService
 * <p>
 * Description: Token
 *
 * @author yacong_liu Email:2682505646@qq.com
 * @date 2018年12月13日
 */
public class TokenKit {
    private static Logger logger = LoggerFactory.getLogger(TokenKit.class);

    /**
     * <p>
     * Title: getAccessToken
     * </p>
     * <p>
     * Description: 获取授权令牌Token
     * </p>
     *
     * @param gatewayHost   API host ex：http://127.0.0.1/K3API
     * @param authorityCode 授权码
     * @return token
     */
    public static String getAccessToken(String gatewayHost, String authorityCode) {

        Token token = TokenDao.getToken(authorityCode);
        if (!checkTokenExpired(token)) {
            logger.info("返回有效期内的token: {} ", token.getToken());
            return token.getToken();
        }

        // 若缓存中没有token或者已过期,重新获取token
        JSONObject result = requestToken(gatewayHost, authorityCode);
        Token tokenBean = JSONUtil.toBean(result, Token.class);
        // 缓存新token
        if (tokenBean != null && tokenBean.getToken() != null) {
            TokenDao.setToken(authorityCode, tokenBean);
            if (logger.isDebugEnabled()) {
                logger.debug("返回新获取的token: {}", tokenBean.getToken());

            }
            return tokenBean.getToken();
        }

        logger.error("获取token信息失败!, 返回null  ");
        return null;

    }

    /**
     * <p>
     * Title: checkTokenExpired
     * </p>
     * <p>
     * Description: 校验token是否过期
     * </p>
     *
     * @param token Token
     * @return boolean
     */
    private static boolean checkTokenExpired(Token token) {
        if (token != null && StrUtil.isNotEmpty(token.getToken()) && token.getCreate() != null) {
            // 校验缓存中的token是否在有效期内
            if ((System.currentTimeMillis() - token.getCreate().getTime()) / 1000 < (token.getValidity())) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Title: requestToken
     * </p>
     * <p>
     * Description: 重新请求Token
     * </p>
     *
     * @param host          AIP HOST
     * @param authorityCode 授权码
     * @return
     */
    private static JSONObject requestToken(String host, String authorityCode) {

        if (StrUtil.isEmpty(host) || StrUtil.isEmpty(authorityCode)) {
            return null;
        }

        String url = host.concat("/Token/Create?authorityCode=").concat(authorityCode);
        JSONObject result = null;
        try {
            result = JSONUtil.parseObj(HttpUtil.get(url)).getJSONObject("Data");
        } catch (Exception e) {
            logger.error("WiseAPI获取token信息失败!, 返回null");
        }

        return result;
    }

}

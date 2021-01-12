package com.kingdee.token;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * Title: TokenDao
 * <p>
 * Description: 单应用获取token可临时将token存储在内存 <br>
 * 如果多应用同时获取token会出现抢占失效问题 可存储于共享缓存或数据库中
 * 
 * @author yacong_liu Email:2682505646@qq.com
 * @date 2018年12月13日
 */
public class TokenDao {
    /** 缓存Token信息 */
    private static Map<String, Token> tokens = new ConcurrentHashMap<String, Token>(7);

    /**
     * 获取Token信息
     * 
     * @param authCode 授权码
     * @return Token
     */
    public static Token getToken(String authCode) {

        if (tokens.containsKey(authCode)) {
            return tokens.get(authCode);
        }

        return null;
    }

    /**
     * 缓存新获得的token
     * 
     * @param authCode 授权码
     * @param token Token
     */
    public static void setToken(String authCode, Token token) {

        if (tokens.containsKey(authCode)) {
            tokens.remove(authCode);
        }
        tokens.put(authCode, token);
    }
}

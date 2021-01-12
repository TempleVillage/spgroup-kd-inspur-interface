package com.kingdee.model.vo;

import lombok.Data;

/**
 * @author songsongpeijun
 * @title: Result
 * @projectName door
 * @description: 返回结果记录
 * @date 2020/11/39:59 上午
 */
@Data
public class Result {
    //状态
    private int type;
    //出参
    private String result;
    //入参
    private String request;
}

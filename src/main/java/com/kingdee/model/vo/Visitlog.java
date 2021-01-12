package com.kingdee.model.vo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;
/**
 * @author songsongpeijun
 * @title: Visitlog
 * @projectName door
 * @description: 访问日志类
 * @date 2020/11/38:52 上午
 */
@Setter
@Getter
@Table(name = "ksi_log")
public class Visitlog {
    @Id
    private String id;

    private String ip;

    private Date time;

    private String params;

    private String operation;

    private String method;

    private int type;

    private String message;

    private String runtime;

    private String request;
}
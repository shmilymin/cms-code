package com.mm.controller.dto;

import lombok.Data;

/**
 * 数据库信息
 *
 * @author lwl
 */
@Data
public class DbInfo {

    private String ip;
    private String port;
    private String dbName;
    private String username;
    private String password;
}

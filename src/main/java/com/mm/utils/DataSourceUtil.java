package com.mm.utils;

import com.mm.config.DynamicDataSource;
import com.mm.controller.dto.DbInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;

/**
 * 数据源工具类
 *
 * @author lwl
 */
@Slf4j
public class DataSourceUtil {

    public static String CURRENT_DB = "defaultDataSource";

    public static final String DRIVE_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

    public static final String URL = "jdbc:mysql://IP:PORT/DB_NAME" +
            "?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8&useSSL=false";

    /**
     * 创建新的数据源
     *
     * @param dbInfo
     * @return
     */
    public static DataSource newDataSource(DbInfo dbInfo) {
        return DataSourceBuilder.create().url(URL.replace("IP", dbInfo.getIp())
                        .replace("PORT", dbInfo.getPort())
                        .replace("DB_NAME", dbInfo.getDbName()))
                .driverClassName(DRIVE_CLASS_NAME)
                .username(dbInfo.getUsername())
                .password(dbInfo.getPassword())
                .build();
    }

    public static void addDataSource(DbInfo dbInfo) {
        DynamicDataSource dynamicDataSource = SpringContextUtil.getBean(DynamicDataSource.class);
        dynamicDataSource.addDataSource(dbInfo.getDbName(), newDataSource(dbInfo));
    }

    public static void setKey(String key) {
        log.debug("change datasource:{}", key);
        DynamicDataSource.dataSourceKey.set(key);
    }

    public static String getKey() {
        String key = DynamicDataSource.dataSourceKey.get();
        return key == null ? CURRENT_DB : key;
    }

    public static void delKey() {
        DynamicDataSource.dataSourceKey.remove();
    }
}

package com.mm.config;

import com.mm.utils.SpringContextUtil;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 动态数据源
 *
 * @author lwl
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    public static final ThreadLocal<String> dataSourceKey = ThreadLocal.withInitial(() -> "defaultDataSource");

    public static Map<Object, Object> dataSourcesMap = new ConcurrentHashMap<>(10);

    static {
        dataSourcesMap.put("defaultDataSource", SpringContextUtil.getBean("defaultDataSource"));
    }


    @Override
    protected Object determineCurrentLookupKey() {
        return dataSourceKey.get();
    }

    /**
     * 加入新数据源
     *
     * @param key
     * @param dataSource
     */
    public void addDataSource(String key, DataSource dataSource) {
        dataSourceKey.set(key);
        dataSourcesMap.put(key, dataSource);
        super.setTargetDataSources(dataSourcesMap);
        super.afterPropertiesSet();
    }
}

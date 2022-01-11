package com.mm.service;

import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mm.dao.GeneratorDao;
import com.mm.utils.GenUtil;
import com.mm.utils.PageUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器
 *
 * @author lwl
 */
@Service
public class GeneratorService {

    @Resource
    private GeneratorDao generatorDao;

    public PageUtil queryList(Page<Map> page) {
        IPage<Map<String, Object>> iPage = generatorDao.queryList(page);
        return new PageUtil(iPage.getTotal(), iPage.getRecords());
    }

    public Map<String, String> queryTable(String tableName) {
        return generatorDao.queryTable(tableName);
    }

    public List<Map<String, String>> queryColumns(String tableName) {
        return generatorDao.queryColumns(tableName);
    }

    public byte[] generatorCode(String[] tableNames, String mainPath, String packageName, String moduleName, String author) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        for (String tableName : tableNames) {
            //查询表信息
            Map<String, String> table = queryTable(tableName);
            //查询列信息
            List<Map<String, String>> columns = queryColumns(tableName);
            //生成代码
            GenUtil.generatorCode(table, columns, zip, mainPath, packageName, moduleName, author);
        }
        IoUtil.close(zip);
        return outputStream.toByteArray();
    }
}

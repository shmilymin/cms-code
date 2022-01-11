package com.mm.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mm.controller.dto.DbInfo;
import com.mm.service.GeneratorService;
import com.mm.utils.DataSourceUtil;
import com.mm.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 代码生成器
 *
 * @author lwl
 */
@Controller
@RequestMapping("/generator")
public class GeneratorController {
    @Autowired
    private GeneratorService generatorService;

    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/list")
    public PageUtil list(Long page, Long limit) {
        DataSourceUtil.setKey(DataSourceUtil.CURRENT_DB);
        PageUtil pageUtil = generatorService.queryList(new Page<>(page, limit));
        DataSourceUtil.delKey();
        return pageUtil;
    }

    /**
     * 生成代码
     */
    @RequestMapping("/code")
    public void code(String tables, String mainPath, String packageName, String moduleName, String author, HttpServletResponse response) throws IOException {
        DataSourceUtil.setKey(DataSourceUtil.CURRENT_DB);
        byte[] data = generatorService.generatorCode(tables.split(","), mainPath, packageName, moduleName, author);
        DataSourceUtil.delKey();
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"code.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        response.getOutputStream().write(data);
    }

    /**
     * 切换数据源
     *
     * @param dbInfo
     * @return
     */
    @PostMapping("/changeDb")
    @ResponseBody
    public R changeDB(DbInfo dbInfo) {

        //添加数据源
        if (DataSourceUtil.getKey().equals(dbInfo.getDbName())) {
            DataSourceUtil.CURRENT_DB = dbInfo.getDbName();
            return R.ok(null);
        }
        DataSourceUtil.addDataSource(dbInfo);
        DataSourceUtil.CURRENT_DB = dbInfo.getDbName();
        return R.ok(null);
    }
}

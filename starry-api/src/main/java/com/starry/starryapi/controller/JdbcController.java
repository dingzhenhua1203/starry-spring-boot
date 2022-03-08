package com.starry.starryapi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

@Api(tags = "jdbc操作")
@RequestMapping("/jdbc")
@RestController
public class JdbcController {

    @Autowired
    JdbcTemplate JdbcTemplate;

    @ApiOperation("查询")
    @GetMapping("/search")
    public String Search(@ApiParam("Job主题") String titleKey) {
        String sql = "select * from TaskJob where TJTitleKey like '%" + titleKey + "%'";

        // JdbcTemplate.queryForObject(sql);
        return  "";
    }

    @ApiOperation("新增Job")
    @PostMapping("/addjob")
    public Boolean AddJob() {
        String sql = "";
        Integer result = JdbcTemplate.update(sql);
        return result > 0;
    }

    @GetMapping("/update/{remark}")
    public Boolean UpdateJob(@PathVariable("remark") String remark) {
        String sql = "update TaskJob set TJRemark= ? where TJID=1";
        Integer result = JdbcTemplate.update(sql,new String[]{remark});
        return result > 0;
    }
}

package com.starry.starryapi.controller;

import com.starry.starryapi.entity.TaskJobEntity;
import com.starry.starryapi.service.TaskJobMapperImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "定时Job")
@RequestMapping("/task-job")
@RestController
public class TaskJobController {
    @Autowired
    TaskJobMapperImpl taskjobService;

    @ApiOperation("查出所有定时任务")
    @GetMapping("/query")
    public List<TaskJobEntity> queryAll() {
        return taskjobService.queryAll();
    }
}

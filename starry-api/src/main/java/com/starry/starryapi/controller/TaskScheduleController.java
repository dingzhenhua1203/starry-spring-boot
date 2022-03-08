package com.starry.starryapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TaskScheduleController {
    @Autowired
    private ApplicationContext context;

    /*@Autowired
    private SpringScheduledCronRepository cronRepository;*/
    /**
     * 查看任务列表
     */
    @GetMapping("/taskList")
    public String taskList() {
        // model.addAttribute("cronList", cronRepository.findAll());
        return "task-list";
    }
}

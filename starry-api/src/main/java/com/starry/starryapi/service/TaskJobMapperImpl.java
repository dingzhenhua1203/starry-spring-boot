package com.starry.starryapi.service;

import com.starry.starryapi.entity.TaskJobEntity;
import com.starry.starryapi.mapper.TaskJobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskJobMapperImpl implements TaskJobMapper {

    @Autowired
    TaskJobMapper taskJobMapper;

    @Override
    public List<TaskJobEntity> queryAll() {
        return taskJobMapper.queryAll();
    }

    @Override
    public boolean addTask(TaskJobEntity entity) {
        return taskJobMapper.addTask(entity);
    }

    @Override
    public boolean updateTask(TaskJobEntity entity) {
        return taskJobMapper.updateTask(entity);
    }

    @Override
    public boolean dealRowStatus() {
        //throw new Exception("not support dealRowStatus");
        return taskJobMapper.dealRowStatus();
    }
}

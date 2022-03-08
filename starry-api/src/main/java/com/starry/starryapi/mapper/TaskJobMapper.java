package com.starry.starryapi.mapper;

import com.starry.starryapi.entity.TaskJobEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TaskJobMapper {
    public List<TaskJobEntity> queryAll();

    public boolean addTask(TaskJobEntity entity);

    public boolean updateTask(TaskJobEntity entity);

    public boolean dealRowStatus();
}

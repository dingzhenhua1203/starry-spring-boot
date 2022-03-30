package com.starry.starryapi.mapper;

import com.starry.starryapi.entity.UsrUserSimple;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<UsrUserSimple> getUsers();
}

package com.chenxw.zhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chenxw.zhxy.entity.LoginForm;
import com.chenxw.zhxy.entity.Teacher;

public interface TeacherService extends IService<Teacher> {

    Teacher login(LoginForm loginForm);

    Teacher getByTeacherById(Long userId);

    IPage<Teacher> getTechersByOpr(Page<Teacher> pageParam, Teacher teacher);
}

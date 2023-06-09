package com.chenxw.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenxw.zhxy.entity.Teacher;
import com.chenxw.zhxy.service.TeacherService;
import com.chenxw.zhxy.util.MD5;
import com.chenxw.zhxy.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "教师控制器")
@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation("分页获取教师信息，带条件")
    @GetMapping("/getTeachers/{pageNo}/{pageSize}")
    public Result getTeachers(
            @ApiParam("页码")
            @PathVariable("pageNo") Integer pageNo,
            @ApiParam("每页条数")
            @PathVariable("pageSize") Integer pageSize,
            Teacher teacher
    ) {
        Page<Teacher> pageParam = new Page<>(pageNo, pageSize);

        IPage<Teacher> page = teacherService.getTechersByOpr(pageParam, teacher);

        return Result.ok(page);
    }


    @ApiOperation("更新或修改教师信息")
    @PostMapping("/saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(
            @ApiParam("要更新或修改的教师信息")
            @RequestBody Teacher teacher
    ) {
        // 如果是更新，要对密码进行加密
        if (teacher.getId() == null || teacher.getId() == 0) {
            teacher.setPassword(MD5.encrypt(teacher.getPassword()));
        }

        teacherService.saveOrUpdate(teacher);
        return Result.ok();
    }


    @ApiOperation("删除单个或多个教师信息")
    @DeleteMapping("/deleteTeacher")
    public Result deleteTeacher(
            @ApiParam("要删除的教师id列表")
            @RequestBody List<Integer> ids
    ) {
        teacherService.removeByIds(ids);
        return Result.ok();
    }

}

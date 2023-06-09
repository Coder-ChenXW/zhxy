package com.chenxw.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenxw.zhxy.entity.Student;
import com.chenxw.zhxy.service.StudentService;
import com.chenxw.zhxy.util.MD5;
import com.chenxw.zhxy.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "学生控制器")
@RestController
@RequestMapping("/sms/studentController")
public class StudentController {

    @Autowired
    private StudentService studentService;


    @ApiOperation("删除单个或多个学生信息")
    @DeleteMapping("/delStudentById")
    public Result delStudentById(
            @ApiParam("要删除的学生的JSON数组")
            @RequestBody List<Integer> ids
    ) {
        studentService.removeByIds(ids);

        return Result.ok();
    }


    @ApiOperation("保存或修改学生信息")
    @PostMapping("/addOrUpdateStudent")
    public Result addOrUpdateStudent(
            @ApiParam("要保存或修改的学生JSON数据")
            @RequestBody Student student
    ) {
        Integer id = student.getId();
        if (null == id || 0 == id) {
            student.setPassword(MD5.encrypt(student.getPassword()));
        }

        studentService.saveOrUpdate(student);
        return Result.ok();
    }


    @ApiOperation("分页带条件查询学生信息")
    @GetMapping("/getStudentByOpr/{pageNo}/{pageSize}")
    public Result getStudentByOpr(
            @ApiParam("当前页码")
            @PathVariable("pageNo")
                    Integer pageNo,
            @ApiParam("每页显示条数")
            @PathVariable("pageSize")
                    Integer pageSize,
            Student student
    ) {
        // 分页信息封装Page对象
        Page<Student> pageParam = new Page(pageNo, pageSize);

        // 进行分页查询
        IPage<Student> studentPage = studentService.getStudentByOpr(pageParam, student);

        // 封装Result对象并返回
        return Result.ok(studentPage);
    }

}

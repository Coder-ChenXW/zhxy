package com.chenxw.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenxw.zhxy.entity.Grade;
import com.chenxw.zhxy.service.GradeService;
import com.chenxw.zhxy.util.Result;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "年级控制器")
@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {

    @Autowired
    private GradeService gradeService;


    @ApiOperation(value = "获取所有年级")
    @GetMapping("/getGrades")
    public Result getGrades() {

        List<Grade> grades = gradeService.getGrades();

        return Result.ok(grades);

    }


    @ApiOperation(value = "删除年级信息")
    @DeleteMapping("/deleteGrade")
    public Result deleteGrade(
            @ApiParam("要删除的所有的grade的Id的json集合")
            @RequestBody List<Integer> ids) {

        gradeService.removeByIds(ids);

        return Result.ok();
    }


    @ApiOperation("新增或者修改年级信息")
    @PostMapping("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(
            @ApiParam("Json的年级对象，有id属性则修改，没有则添加")
            @RequestBody Grade grade
    ) {

        // 接受参数
        // 调用服务层方法完成保存或者更新
        gradeService.saveOrUpdate(grade);
        return Result.ok();
    }


    @ApiOperation("根据年级名称模糊查询带分页")
    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    public Result getGrades(
            @ApiParam("分页查询的当前页码")
            @PathVariable("pageNo") Integer pageNo,
            @ApiParam("分页查询的每页显示的条数")
            @PathVariable("pageSize") Integer pageSize,
            @ApiParam("分页查询的模糊匹配名称")
                    String gradeName
    ) {
        // 分页带条件查询
        Page<Grade> page = new Page<>(pageNo, pageSize);

        // 通过service层查询
        IPage<Grade> pageRs = gradeService.getBradeByOpr(page, gradeName);

        // 封装Result对象并返回
        return Result.ok(pageRs);
    }

}

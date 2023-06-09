package com.chenxw.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenxw.zhxy.entity.Clazz;
import com.chenxw.zhxy.service.ClazzService;
import com.chenxw.zhxy.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "班级管理器")
@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;


    @ApiOperation("获取所有班级信息")
    @GetMapping("/getClazzs")
    public Result getClazzs() {
        List<Clazz> clazzes = clazzService.getClazzs();

        return Result.ok(clazzes);
    }


    @ApiOperation("删除班级信息")
    @DeleteMapping("/deleteClazz")
    public Result deleteClazz(
            @ApiParam("要删除的多个班级的JSON数组")
            @RequestBody List<Integer> ids
    ) {
        clazzService.removeByIds(ids);
        return Result.ok();
    }


    @ApiOperation("增加或修改班级信息")
    @PostMapping("/saveOrUpdateClazz")
    public Result saveOrUpdateClazz(
            @ApiParam("JSON格式的班级信息")
            @RequestBody Clazz clazz
    ) {
        clazzService.saveOrUpdate(clazz);
        return Result.ok();
    }


    @ApiOperation("分页带条件查询班级信息")
    @GetMapping("/getClazzsByOpr/{pageNo}/{pageSize}")
    public Result getClazzsByOpr(
            @ApiParam("当前页码")
            @PathVariable("pageNo")
                    Integer pageNo,
            @ApiParam("每页显示条数")
            @PathVariable("pageSize")
                    Integer pageSize,
            Clazz clazz
    ) {
        Page<Clazz> page = new Page<>(pageNo, pageSize);

        IPage<Clazz> iPage = clazzService.getClazzsByOpr(page, clazz);

        return Result.ok(iPage);
    }

}

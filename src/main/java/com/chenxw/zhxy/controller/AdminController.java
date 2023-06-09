package com.chenxw.zhxy.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenxw.zhxy.entity.Admin;
import com.chenxw.zhxy.service.AdminService;
import com.chenxw.zhxy.util.MD5;
import com.chenxw.zhxy.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "管理员控制器")
@RestController
@RequestMapping("/sms/adminController")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @ApiOperation("带条件分页查询管理员")
    @GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
    public Result getAllAdmin(
            @ApiParam("页码数")
            @PathVariable("pageNo")
                    Integer pageNo,
            @ApiParam("页大小")
            @PathVariable("pageSize")
                    Integer pageSize,
            @ApiParam("管理员姓名")
                    String adminName
    ) {
        Page<Admin> pageParam = new Page<>(pageNo, pageSize);

        IPage<Admin> iPage = adminService.getAdminByOpr(pageParam, adminName);

        return Result.ok(iPage);
    }


    @ApiOperation("修改或添加管理员信息")
    @PostMapping("/saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(
            @ApiParam("JSON格式的管理员信息")
            @RequestBody Admin admin
    ) {
        Integer id = admin.getId();
        if (id == null || id == 0) {
            admin.setPassword(MD5.encrypt(admin.getPassword()));
        }

        adminService.saveOrUpdate(admin);

        return Result.ok();
    }


    @ApiOperation("删除单个或多个管理员")
    @DeleteMapping("/deleteAdmin")
    public Result deleteAdmin(
            @ApiParam("要删除的管理员id集合")
            @RequestBody List<Integer> ids
    ){
        adminService.removeByIds(ids);


        return Result.ok();
    }


}

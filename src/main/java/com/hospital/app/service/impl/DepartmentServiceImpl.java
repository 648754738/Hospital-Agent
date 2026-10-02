package com.hospital.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hospital.app.dao.DepartmentMapper;
import com.hospital.app.domain.Department;
import com.hospital.app.service.IDepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Override
    public List<Department> searchDepartments(String name) {
        // 创建查询条件
        LambdaQueryWrapper<Department> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            lambdaQueryWrapper.like(Department::getName, name);
        }
        return super.list(lambdaQueryWrapper);
    }
}

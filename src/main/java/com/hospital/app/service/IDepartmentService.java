package com.hospital.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hospital.app.domain.Department;

import java.util.List;

public interface IDepartmentService extends IService<Department> {

    List<Department> searchDepartments(String name);


}

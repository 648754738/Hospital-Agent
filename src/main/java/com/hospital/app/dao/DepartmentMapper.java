package com.hospital.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hospital.app.domain.Department;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentMapper  extends BaseMapper<Department> {
}

package com.hospital.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hospital.app.domain.Doctor;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorMapper extends BaseMapper<Doctor> {
}

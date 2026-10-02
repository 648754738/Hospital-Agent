package com.hospital.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hospital.app.dao.DoctorMapper;
import com.hospital.app.domain.Doctor;
import com.hospital.app.service.IDoctorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl  extends ServiceImpl<DoctorMapper, Doctor> implements IDoctorService {

    @Override
    public List<Doctor> searchDoctors(String name) {
        LambdaQueryWrapper<Doctor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            lambdaQueryWrapper.like(Doctor::getName, name);
        }
        return super.list(lambdaQueryWrapper);
    }
}

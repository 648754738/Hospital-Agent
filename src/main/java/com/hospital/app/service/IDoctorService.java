package com.hospital.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hospital.app.domain.Doctor;

import java.util.List;

public interface IDoctorService  extends IService<Doctor> {
    List<Doctor> searchDoctors(String name);
}

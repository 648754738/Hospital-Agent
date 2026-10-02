package com.hospital.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hospital.app.domain.Schedule;

import java.util.List;

public interface IScheduleService extends IService<Schedule> {
    List<Schedule> searchScheduleDoctors(String doctorName);
}

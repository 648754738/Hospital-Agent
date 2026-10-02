package com.hospital.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hospital.app.dao.ScheduleMapper;
import com.hospital.app.domain.Schedule;
import com.hospital.app.service.IScheduleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleServiceImpl extends ServiceImpl<ScheduleMapper, Schedule> implements IScheduleService {

    @Override
    public List<Schedule> searchScheduleDoctors(String doctorName) {
        LambdaQueryWrapper<Schedule> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (doctorName != null && !doctorName.isEmpty()) {
            lambdaQueryWrapper.like(Schedule::getDoctorName, doctorName);
        }
        return super.list(lambdaQueryWrapper);
    }
}

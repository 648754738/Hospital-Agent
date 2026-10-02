package com.hospital.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hospital.app.domain.Appointment;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentMapper extends BaseMapper<Appointment> {
}

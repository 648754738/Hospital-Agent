package com.hospital.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hospital.app.dao.HistoryAppointmentMapper;
import com.hospital.app.domain.HistoryAppointment;
import com.hospital.app.service.IHistoryAppointmentService;
import org.springframework.stereotype.Service;

@Service
public class HistoryAppointmentServiceImpl extends ServiceImpl<HistoryAppointmentMapper, HistoryAppointment> implements IHistoryAppointmentService {
}

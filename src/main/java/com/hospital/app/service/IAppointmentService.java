package com.hospital.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hospital.app.domain.Appointment;

public interface IAppointmentService  extends IService<Appointment> {
    boolean saveAppointment(String patientName, String idCard, String tel,
                            String appointmentType, String doctorName,
                            String departmentName, String cost,String appointmentTime);

    boolean removeAppointment(String patientName, String idCard,String tel , String departmentName,String doctorName);
}

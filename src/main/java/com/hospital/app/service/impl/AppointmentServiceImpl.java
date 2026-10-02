package com.hospital.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hospital.app.dao.AppointmentMapper;
import com.hospital.app.domain.Appointment;
import com.hospital.app.domain.HistoryAppointment;
import com.hospital.app.domain.Schedule;
import com.hospital.app.service.IAppointmentService;
import com.hospital.app.service.IHistoryAppointmentService;
import com.hospital.app.service.IScheduleService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment> implements IAppointmentService {
    @Autowired
    private IScheduleService scheduleService;
    @Autowired
    private IHistoryAppointmentService historyAppointmentService;

    @Transactional
    @Override
    public boolean saveAppointment(String patientName, String idCard,
                                   String tel, String appointmentType,
                                   String doctorName, String departmentName, String cost, String appointmentTime) {
        // 参数校验
        validateInput(patientName, idCard, tel, appointmentType, doctorName, departmentName, cost, appointmentTime);

        // 身份证校验增强
        if (!isValidIdCard(idCard)) {
            throw new IllegalArgumentException("身份证号格式错误");
        }

        // 手机号校验增强
        if (!isValidPhoneNumber(tel)) {
            throw new IllegalArgumentException("手机号格式错误");
        }

        // 使用数据库层面的原子操作来更新排班信息
        LambdaQueryWrapper<Schedule> scheduleWrapper = new LambdaQueryWrapper<>();
        scheduleWrapper.eq(Schedule::getDoctorName, doctorName);
        scheduleWrapper.gt(Schedule::getAvailableSlots, 0); // 只查询还有空位的排班

        Schedule schedule = scheduleService.getOne(scheduleWrapper);
        if (schedule != null) {
            // 使用乐观锁或数据库更新条件来确保原子性
            LambdaUpdateWrapper<Schedule> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Schedule::getDoctorName, doctorName);
            updateWrapper.gt(Schedule::getAvailableSlots, 0);
            updateWrapper.setSql("available_slots = available_slots - 1");

            boolean updateSuccess = scheduleService.update(updateWrapper);
            if (!updateSuccess) {
                // 更新失败说明并发情况下已经被其他用户预约完
                throw new RuntimeException("该医生号源已满，请选择其他时间");
            }
        } else {
            throw new RuntimeException("该医生无可预约号源");
        }

        Appointment appointment = new Appointment();
        appointment.setId(UUID.randomUUID().toString());
        appointment.setPatientName(patientName);
        appointment.setIdCard(idCard);
        appointment.setTel(tel);
        appointment.setAppointmentType(appointmentType);
        appointment.setDoctorName(doctorName);
        appointment.setDepartmentName(departmentName);
        appointment.setAppointmentTime(appointmentTime);
        appointment.setCost(cost);
        return super.save(appointment);
    }

    @Override
    @Transactional
    public boolean removeAppointment(String patientName, String idCard,String tel , String departmentName, String doctorName) {
        validateInput(patientName, idCard, departmentName, doctorName);
        LambdaQueryWrapper<Schedule> scheduleWrapper = new LambdaQueryWrapper<>();
        scheduleWrapper.eq(Schedule::getDoctorName, doctorName);
        Schedule schedule = scheduleService.getOne(scheduleWrapper);
        if (schedule != null) {
            LambdaUpdateWrapper<Schedule> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Schedule::getDoctorName, doctorName);
            updateWrapper.gt(Schedule::getAvailableSlots, 0);
            updateWrapper.setSql("available_slots = available_slots + 1");
            boolean updateSuccess = scheduleService.update(updateWrapper);
            if (!updateSuccess) {
                throw new RuntimeException("取消预约失败：更新号源失败");
            }
        }
        LambdaQueryWrapper<Appointment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Appointment::getPatientName, patientName);
        queryWrapper.eq(Appointment::getIdCard, idCard);
        queryWrapper.eq(Appointment::getDepartmentName, departmentName);
        queryWrapper.eq(Appointment::getDoctorName, doctorName);
        queryWrapper.eq(Appointment::getTel, tel);
        // 查询预约记录
        Appointment appointment = super.getOne(queryWrapper);
        HistoryAppointment historyAppointment = getHistoryAppointment(appointment);
        // 记录历史记录
        historyAppointmentService.save(historyAppointment);
        appointment.setCost("0");
        return super.removeById(appointment);
    }

    @NotNull
    private static HistoryAppointment getHistoryAppointment(Appointment appointment) {
        HistoryAppointment historyAppointment = new HistoryAppointment();
        historyAppointment.setId(appointment.getId());
        historyAppointment.setPatientName(appointment.getPatientName());
        historyAppointment.setIdCard(appointment.getIdCard());
        historyAppointment.setDepartmentName(appointment.getDepartmentName());
        historyAppointment.setDoctorName(appointment.getDoctorName());
        historyAppointment.setTel(appointment.getTel());
        historyAppointment.setAppointmentTime(appointment.getAppointmentTime());
        historyAppointment.setAppointmentType(appointment.getAppointmentType());
        historyAppointment.setCost(appointment.getCost());
        historyAppointment.setDeleted(0L);
        return historyAppointment;
    }

    private void validateInput(String patientName, String idCard, String departmentName, String doctorName) {
        if (patientName == null || patientName.trim().isEmpty()) {
            throw new IllegalArgumentException("患者姓名不能为空");
        }
        if (idCard == null || idCard.trim().isEmpty()) {
            throw new IllegalArgumentException("身份证号不能为空");
        }
        if (departmentName == null || departmentName.trim().isEmpty()) {
            throw new IllegalArgumentException("科室名称不能为空");
        }
        if (doctorName == null || doctorName.trim().isEmpty()) {
            throw new IllegalArgumentException("医生姓名不能为空");
        }
    }

    // 私有方法：参数校验
    private void validateInput(String patientName, String idCard, String tel, String appointmentType,
                               String doctorName, String departmentName, String cost, String appointmentTime) {
        if (patientName == null || patientName.trim().isEmpty()) {
            throw new IllegalArgumentException("患者姓名不能为空");
        }
        if (idCard == null || idCard.trim().isEmpty()) {
            throw new IllegalArgumentException("身份证号不能为空");
        }
        if (tel == null || tel.trim().isEmpty()) {
            throw new IllegalArgumentException("手机号不能为空");
        }
        if (appointmentType == null || appointmentType.trim().isEmpty()) {
            throw new IllegalArgumentException("门诊类型不能为空");
        }
        if (doctorName == null || doctorName.trim().isEmpty()) {
            throw new IllegalArgumentException("医生姓名不能为空");
        }
        if (departmentName == null || departmentName.trim().isEmpty()) {
            throw new IllegalArgumentException("科室名称不能为空");
        }
        if (cost == null || cost.trim().isEmpty()) {
            throw new IllegalArgumentException("费用不能为空");
        }
        if (appointmentTime == null || appointmentTime.trim().isEmpty()) {
            throw new IllegalArgumentException("预约时间不能为空");
        }
    }

    // 私有方法：校验身份证号
    private boolean isValidIdCard(String idCard) {
        return idCard.matches("^[0-9A-Za-z]{18}$");
    }

    // 私有方法：校验手机号
    private boolean isValidPhoneNumber(String tel) {
        return tel.matches("^1[0-9]{10}$");
    }

}

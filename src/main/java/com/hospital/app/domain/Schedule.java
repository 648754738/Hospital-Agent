package com.hospital.app.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * schedule_id
 * doctor_name
 * department_name
 * appointment_type
 * weekday
 * schedule_date
 * time_period
 * total_slots
 * available_slots
 * appointment_fee
 * address
 * schedule_status
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schedule  implements Serializable {
    @TableId
    private String scheduleId;
    private String doctorName;
    private String departmentName;
    private String appointmentType;
    private String weekday;
    private String scheduleDate;
    private String timePeriod;
    private Long totalSlots;
    private Long availableSlots;
    private String appointmentFee;
    private String address;
    private String scheduleStatus;
}

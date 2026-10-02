package com.hospital.app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 预约记录对象 history_appointment
 * 
 * @author 席老师
 * @date 2025-08-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryAppointment implements Serializable
{

    private String id;

    private String patientName;

    private String idCard;

    private String tel;
    private String appointmentTime;
    private String appointmentType;
    private Long status;
    private String doctorName;
    private String departmentName;
    private String cost;
    private Long deleted;
    private Long lookStatus; //look_status

}

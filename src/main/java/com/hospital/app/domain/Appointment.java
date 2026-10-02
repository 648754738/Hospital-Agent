package com.hospital.app.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment  implements Serializable {
    @TableId
    private String id;
    private String patientName;
    private String idCard;
    private String tel;
    private String appointmentTime;
    private String appointmentType;
    private String status;
    private String doctorName;
    private String departmentName;
    private String cost;
    private String createTime;
    private String updateTime;
    private String lookStatus;
}

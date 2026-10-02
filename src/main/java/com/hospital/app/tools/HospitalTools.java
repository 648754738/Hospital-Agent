package com.hospital.app.tools;

import com.hospital.app.service.IAppointmentService;
import com.hospital.app.service.IDepartmentService;
import com.hospital.app.service.IDoctorService;
import com.hospital.app.service.IScheduleService;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HospitalTools {

    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private IDoctorService doctorService;

    @Autowired
    private IScheduleService scheduleService;

    @Autowired
    private IAppointmentService appointmentService;

    @Tool(name = "查询科室信息", value = "根据名称查询科室信息，输入查询根据名称查询，不输入名称查询全部")
    public String searchDepartments(@P("科室名称") String name) {
        return departmentService.searchDepartments(name).toString();
    }

    @Tool(name = "查询医生", value = "根据名称查询医生信息，输入查询根据名称查询，不输入名称查询全部")
    public String searchDoctors(@P("医生名称") String name) {
        return doctorService.searchDoctors(name).toString();
    }

    @Tool(name = "排班查询医生", value = "根据名称查询排班医生信息，输入排班医生名称根据名称查询，不输入名称查询全部")
    public String searchScheduleDoctors(@P("排班查询医生名称") String doctorName) {
        return scheduleService.searchScheduleDoctors(doctorName).toString();
    }

    /**
     * private String patientName;
     * private String idCard;
     * private String tel;
     * private String appointmentType;
     * private String status;
     * private String doctorName;
     * private String departmentName;
     * private String cost;
     */
    @Tool(name = "预约挂号", value = "")
    public String saveAppointment(@P("患者名称") String patientName,
                                  @P("患者身份证") String idCard,
                                  @P("患者电话号码") String tel,
                                  @P("挂号类型") String appointmentType,
                                  @P("医生名称") String doctorName,
                                  @P("科室名称") String departmentName,
                                  @P("挂号费用") String cost,
                                  @P("预约时间") String appointmentTime) {
        if (appointmentService.saveAppointment(patientName, idCard, tel, appointmentType, doctorName, departmentName, cost, appointmentTime)) {
            return "预约成功";
        }
        return "预约失败";
    }

    @Tool(name = "取消预约挂号", value = "取消预约挂号必须输入患者输入名称、身份证、电话号码、科室名称、医生名称")
    public String removeAppointment(@P("患者名称") String patientName,
                                    @P("患者身份证") String idCard,
                                    @P("电话号码") String tel,
                                    @P("科室名称") String departmentName,
                                    @P("医生名称") String doctorName) {
        if (appointmentService.removeAppointment(patientName, idCard, tel, departmentName, doctorName)) {
            return "取消预约成功";
        }
        return "取消预约失败";
    }

}

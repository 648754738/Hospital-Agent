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
public class Doctor  implements Serializable {
    @TableId
    private String id;
    private String name;
    private String introduction;
    private String tel;
    private String email;
    private String sex;
    private String age;
    private String degree;
    private String title;
    private String departmentName;
    private String createTime;
    private String updateTime;
    private String status;
    @TableLogic
    private Long deleted;


}

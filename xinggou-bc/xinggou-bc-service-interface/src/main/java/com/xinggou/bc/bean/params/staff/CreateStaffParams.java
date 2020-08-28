package com.xinggou.bc.bean.params.staff;

import com.xinggou.common.enums.GenderEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author luoyb
 * Created on 2020/8/17
 */
@Data
public class CreateStaffParams implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userName;
    private String nickName;
    private String realName;
    private String mobile;
    private String avatar;
    private String email;
    private Date birthday;
    private GenderEnum gender;
    private String position;

    private Long deptId;

}

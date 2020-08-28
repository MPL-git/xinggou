package com.xinggou.bc.bean.dto;

import com.xinggou.bc.entity.Staff;
import lombok.Data;

import java.io.Serializable;

/**
 * @author luoyb
 * Created on 2020/8/15
 */
@Data
public class StaffDTO extends Staff implements Serializable {

    private static final long serialVersionUID = 1L;

    private String deptName;

}

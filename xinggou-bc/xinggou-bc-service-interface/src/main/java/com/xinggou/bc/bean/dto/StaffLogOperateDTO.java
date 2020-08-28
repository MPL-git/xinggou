package com.xinggou.bc.bean.dto;

import com.xinggou.bc.entity.StaffLogOperate;
import lombok.Data;

/**
 * @author luoyb
 * Created on 2020/8/19
 */
@Data
public class StaffLogOperateDTO extends StaffLogOperate {

    private static final long serialVersionUID = 1L;

    private String staffName;

}

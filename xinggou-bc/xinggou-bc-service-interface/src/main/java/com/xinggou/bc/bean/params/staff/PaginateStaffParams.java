package com.xinggou.bc.bean.params.staff;

import com.xinggou.bc.enums.StaffStatusEnum;
import com.xinggou.common.vo.PageParams;
import lombok.Data;

import java.io.Serializable;

/**
 * @author luoyb
 * Created on 2020/8/15
 */
@Data
public class PaginateStaffParams extends PageParams implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long deptId;

    private String nickName;

    private String userName;

    private String realName;

    private String mobile;

    private StaffStatusEnum status;

}

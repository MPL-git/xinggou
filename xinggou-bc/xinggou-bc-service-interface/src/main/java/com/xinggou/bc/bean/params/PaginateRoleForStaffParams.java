package com.xinggou.bc.bean.params;

import com.xinggou.common.vo.PageParams;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author luoyb
 * Created on 2020/8/15
 */
@Data
public class PaginateRoleForStaffParams extends PageParams implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long staffId;

    private String roleName;

    //0全部 1已绑定 2未绑定
    private int boundType;

    private List<Long> staffRoleIdList;

}

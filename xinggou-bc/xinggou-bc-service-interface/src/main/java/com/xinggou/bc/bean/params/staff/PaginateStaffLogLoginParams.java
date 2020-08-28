package com.xinggou.bc.bean.params.staff;

import com.xinggou.common.vo.PageParams;
import lombok.Data;

/**
 * @author luoyb
 * Created on 2020/8/19
 */
@Data
public class PaginateStaffLogLoginParams extends PageParams {

    private static final long serialVersionUID = 1L;

    private Long staffId;
    private String ip;

}

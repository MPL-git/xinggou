package com.xinggou.bc.bean.params;

import com.xinggou.bc.enums.ConfigStatusEnum;
import com.xinggou.common.vo.PageParams;
import lombok.Data;

/**
 * @author luoyb
 * Created on 2020/8/19
 */
@Data
public class PaginateConfigParams extends PageParams {

    private static final long serialVersionUID = 1L;

    private String name;
    private ConfigStatusEnum status;

}

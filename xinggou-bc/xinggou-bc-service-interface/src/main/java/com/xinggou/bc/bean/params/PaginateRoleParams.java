package com.xinggou.bc.bean.params;

import com.xinggou.common.vo.PageParams;
import lombok.Data;

import java.io.Serializable;

/**
 * @author luoyb
 * Created on 2020/8/18
 */
@Data
public class PaginateRoleParams extends PageParams implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

}

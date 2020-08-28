package com.xinggou.rc.bean.params;

import com.xinggou.common.vo.PageParams;
import lombok.Data;

import java.util.Date;

/**
 * @author luoyb
 * Created on 2020/8/20
 */
@Data
public class PaginateOssFileParams extends PageParams {

    private static final long serialVersionUID = 1L;

    private Date beginDate;
    private Date endDate;

}

package com.xinggou.bc.bean.params;

import lombok.Data;

import java.io.Serializable;

/**
 * @author luoyb
 * Created on 2020/8/19
 */
@Data
public class SaveConfigParams implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String paramKey;
    private String paramValue;
    private Integer seqNo;
    private String remarks;

}

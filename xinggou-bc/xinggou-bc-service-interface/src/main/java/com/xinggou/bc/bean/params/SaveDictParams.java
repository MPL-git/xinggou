package com.xinggou.bc.bean.params;

import lombok.Data;

import java.io.Serializable;

/**
 * @author luoyb
 * Created on 2020/8/19
 */
@Data
public class SaveDictParams implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String type;

    private String code;

    private String value;

    private Integer seqNo;

    private String remarks;


}

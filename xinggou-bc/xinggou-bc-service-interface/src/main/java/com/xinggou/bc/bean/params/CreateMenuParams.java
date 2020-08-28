package com.xinggou.bc.bean.params;

import com.xinggou.bc.enums.MenuTypeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author luoyb
 * Created on 2020/8/18
 */
@Data
public class CreateMenuParams implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long parentId;
    private String name;
    private MenuTypeEnum type;
    private String icon;
    private String url;
    private String perms;
    private Integer seqNo;

}

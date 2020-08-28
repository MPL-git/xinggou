package com.xinggou.bc.bean.dto;

import com.xinggou.bc.entity.SetMenu;
import lombok.Data;

import java.io.Serializable;

/**
 * @author luoyb
 * Created on 2020/8/18
 */
@Data
public class MenuDTO extends SetMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    private String parentName;

}

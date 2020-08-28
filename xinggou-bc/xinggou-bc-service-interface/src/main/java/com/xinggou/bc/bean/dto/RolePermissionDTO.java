package com.xinggou.bc.bean.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luoyb
 * Created on 2020/8/21
 */
@Data
public class RolePermissionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private RolePermissionNode tree;
    private final List<Long> ownMenuIdList = new ArrayList<>();

}

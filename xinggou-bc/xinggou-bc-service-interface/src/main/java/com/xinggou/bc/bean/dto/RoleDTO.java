package com.xinggou.bc.bean.dto;

import com.xinggou.bc.entity.SetRole;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luoyb
 * Created on 2020/8/18
 */
@Data
public class RoleDTO extends SetRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean bound;

    private final List<String> assignedStaffList = new ArrayList<>();

}

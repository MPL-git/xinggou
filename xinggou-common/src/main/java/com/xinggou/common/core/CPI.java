package com.xinggou.common.core;

import java.util.Map;
import java.util.Set;

/**
 * Cross Package Invoking pattern for package activerecord.
 */
public abstract class CPI {

    /**
     * Return the attributes map of the model
     *
     * @param model the model extends from class Model
     * @return the attributes map of the model
     */
    public static final Map<String, Object> getAttrs(Model model) {
        return model.getAttrs();
    }

    /**
     * Return the attributes map of the model
     *
     * @param model the model extends from class Model
     * @return the attributes map of the model
     */
    public static final Set<String> getRemoveAttrs(Model model) {
        return model.getRemoveAttrs();
    }


}


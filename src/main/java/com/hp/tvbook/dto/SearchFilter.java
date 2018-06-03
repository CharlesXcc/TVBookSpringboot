package com.hp.tvbook.dto;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Map.Entry;

public class SearchFilter {

    public enum Operator {
        EQ, LIKE, GT, LT, GTE, LTE
    }

    public String fieldName;
    public Object value;
    public Operator operator;

    public SearchFilter(String fieldName, Operator operator, Object value) {
        this.fieldName = fieldName;
        this.value = value;
        this.operator = operator;
    }

    public static Map<String, SearchFilter> parse(Map<String, Object> searchParams) {
        Map<String, SearchFilter> filters = Maps.newHashMap();

        for (Entry<String, Object> entry : searchParams.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value == null || StringUtils.isBlank(value.toString())) {
                continue;
            }
            String[] names = StringUtils.split(key, "_");
            if (names.length != 2) {
                throw new IllegalArgumentException(key + " is not a valid search filter name");
            }
            String filedName = names[1];
            Operator operator = Operator.valueOf(names[0]);

            SearchFilter filter = new SearchFilter(filedName, operator, value);
            filters.put(key, filter);
        }

        return filters;
    }
}

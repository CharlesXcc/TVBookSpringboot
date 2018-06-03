package com.hp.tvbook.dto;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DynamicSpecifications {

    public static <T> Specification<T> getSpecification(Map<String, Object> parameters) {
        return DynamicSpecifications.bySearchFilter(SearchFilter.parse(parameters).values());
    }

    public static <T> Specification<T> bySearchFilter(final Collection<SearchFilter> filters) {
        return (root, query, builder) -> {
            if (!CollectionUtils.isEmpty(filters)) {

                List<Predicate> predicates = Lists.newArrayList();
                for (SearchFilter filter : filters) {
                    String[] names = StringUtils.split(filter.fieldName, ".");
                    Path expression = root.get(names[0]);
                    for (int i = 1; i < names.length; i++) {
                        expression = expression.get(names[i]);
                    }

                    // logic operator
                    switch (filter.operator) {
                        case EQ:
                            predicates.add(builder.equal(expression, filter.value));
                            break;
                        case LIKE:
                            predicates.add(builder.like(expression, "%" + filter.value + "%"));
                            break;
                        case GT:
                            predicates.add(builder.greaterThan(expression, (Comparable) filter.value));
                            break;
                        case LT:
                            predicates.add(builder.lessThan(expression, (Comparable) filter.value));
                            break;
                        case GTE:
                            predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) filter.value));
                            break;
                        case LTE:
                            predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) filter.value));
                            break;
                    }
                }

                if (!predicates.isEmpty()) {
                    return builder.and(predicates.toArray(new Predicate[predicates.size()]));
                }
            }

            return builder.conjunction();
        };
    }

    public static PageRequest getPageRequest(Integer page, Integer size) {
        return getPageRequest(page, size, null);
    }

    public static PageRequest getPageRequest(Integer page, Integer size, Sort sort) {
        if (null == page || null == size) {
            return new PageRequest(0, Integer.MAX_VALUE, sort);
        } else {
            return new PageRequest(page, size, sort);
        }
    }
}

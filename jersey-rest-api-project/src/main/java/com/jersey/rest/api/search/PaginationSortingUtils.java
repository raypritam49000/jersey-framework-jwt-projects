package com.jersey.rest.api.search;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.Objects;

public class PaginationSortingUtils {

    public static <T> void applySorting(CriteriaBuilder criteriaBuilder, CriteriaQuery<T> criteriaQuery, Root<T> root, String sortedField, String sortedDirection) {
        if (StringUtils.isNotEmpty(sortedField)) {
            Order order = Objects.equals(sortedDirection, "asc") ? criteriaBuilder.asc(root.get(sortedField)) : criteriaBuilder.desc(root.get(sortedField));
            criteriaQuery.orderBy(order);
        }
    }
}

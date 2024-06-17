package com.jersey.rest.api.search;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Predicate;

public class PaginationFilterUtils {

    public static <T> Predicate applyLikeFilter(CriteriaBuilder criteriaBuilder, Root<T> root, String path, String value) {
        return criteriaBuilder.like(root.get(path), "%" + value + "%");
    }

    public static <T> Predicate applyEqualFilter(CriteriaBuilder criteriaBuilder, Root<T> root, String path, String value) {
        return criteriaBuilder.equal(root.get(path), value);
    }

    public static <T, V extends Comparable<? super V>> Predicate applyBetweenFilter(CriteriaBuilder criteriaBuilder, Root<T> root, String path, V startValue, V endValue) {
        return criteriaBuilder.between(root.get(path), startValue, endValue);
    }

    public static <T, Y extends Comparable<? super Y>> Predicate getGreaterThanOrEqualToPredicate(CriteriaBuilder criteriaBuilder, Root<T> root, String attributeName, Y value) {
        return criteriaBuilder.greaterThanOrEqualTo(root.get(attributeName), value);
    }

    public static <T, Y extends Comparable<? super Y>> Predicate getLessThanOrEqualToPredicate(CriteriaBuilder criteriaBuilder, Root<T> root, String attributeName, Y value) {
        return criteriaBuilder.lessThanOrEqualTo(root.get(attributeName), value);
    }

}

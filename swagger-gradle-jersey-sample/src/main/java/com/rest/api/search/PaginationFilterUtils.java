package com.rest.api.search;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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

    // Method for handling double type salary exact match
    public static <T> Predicate applyEqualSalaryFilter(CriteriaBuilder criteriaBuilder, Root<T> root, String path, double value) {
        return criteriaBuilder.equal(root.get(path), value);
    }

    // Method for handling double type salary range (between)
    public static <T> Predicate applySalaryBetweenFilter(CriteriaBuilder criteriaBuilder, Root<T> root, String path, double startValue, double endValue) {
        return criteriaBuilder.between(root.get(path), startValue, endValue);
    }

    // Method for handling double type salary greater than or equal to
    public static <T> Predicate getSalaryGreaterThanOrEqualToPredicate(CriteriaBuilder criteriaBuilder, Root<T> root, String attributeName, double value) {
        return criteriaBuilder.greaterThanOrEqualTo(root.get(attributeName), value);
    }

    // Method for handling double type salary less than or equal to
    public static <T> Predicate getSalaryLessThanOrEqualToPredicate(CriteriaBuilder criteriaBuilder, Root<T> root, String attributeName, double value) {
        return criteriaBuilder.lessThanOrEqualTo(root.get(attributeName), value);
    }

}

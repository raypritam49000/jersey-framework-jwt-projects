package com.rest.api.search;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class PaginationUtils {

    public static <T> Long countTotalElements(Session session, CriteriaBuilder criteriaBuilder, Class<T> entityClass){
        CriteriaQuery<Long> countTotalQuery = criteriaBuilder.createQuery(Long.class);
        Root<T> countTotalRoot = countTotalQuery.from(entityClass);
        countTotalQuery.select(criteriaBuilder.count(countTotalRoot));
        return session.createQuery(countTotalQuery).getSingleResult();
    }

    public static <T> Long countTotalFilterElements(Session session, CriteriaBuilder criteriaBuilder, List<Predicate> predicates, Class<T> entityClass) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<T> countRoot = countQuery.from(entityClass);
        countQuery.select(criteriaBuilder.count(countRoot));
        countQuery.where(predicates.toArray(new Predicate[0]));
        return session.createQuery(countQuery).getSingleResult();
    }

    public static <T> BasePageDTO<T> createBasePageDTO(List<T> resultList, int pageNumber, int pageSize, Long totalElements,Long totalFilterElements, int totalPages, String sortedField, String sortedDirection,String pluralResourceName) {
        BasePageDTO<T> basePageDTO = new BasePageDTO<>();
        basePageDTO.setHasContent(!resultList.isEmpty());
        basePageDTO.setHasNext(pageNumber < totalPages);
        basePageDTO.setHasPrevious(pageNumber > 1);
        basePageDTO.setFirst(pageNumber == 1);
        basePageDTO.setLast(pageNumber == totalPages);
        basePageDTO.setTotalElements(totalElements);
        basePageDTO.setFilterElements(totalFilterElements);
        basePageDTO.setTotalPages(totalPages);
        basePageDTO.setData(resultList);
        basePageDTO.setPageNumber(pageNumber);
        basePageDTO.setPageSize(pageSize);
        basePageDTO.setPluralResourceName(pluralResourceName);
        basePageDTO.setSorted(StringUtils.isNotEmpty(sortedField));
        basePageDTO.setSortColumn(sortedField);
        basePageDTO.setSortOrder(sortedDirection);

        return basePageDTO;
    }


}

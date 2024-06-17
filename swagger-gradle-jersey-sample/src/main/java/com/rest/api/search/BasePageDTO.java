package com.rest.api.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Collections;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasePageDTO<T> {
    private boolean hasContent;
    private boolean hasNext;
    private boolean hasPrevious;
    private boolean isFirst;
    private boolean isLast;
    private long totalElements;
    private long filterElements;
    private int totalPages;
    private Collection<T> data = Collections.emptyList();
    private int pageNumber;
    private int pageSize;
    private String pluralResourceName;
    private boolean isSorted;
    private String sortColumn;
    private String sortOrder;
}

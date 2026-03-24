package com.custodia.supply.api.util;

import java.util.List;

import org.springframework.data.domain.Page;

public class PageResponse<T> {
	
	private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean first;
    private boolean last;

    public static <T> PageResponse<T> of(Page<T> p) {
        PageResponse<T> r = new PageResponse<>();
        r.content = p.getContent();
        r.page = p.getNumber();
        r.size = p.getSize();
        r.totalElements = p.getTotalElements();
        r.totalPages = p.getTotalPages();
        r.first = p.isFirst();
        r.last = p.isLast();
        return r;
    }
}

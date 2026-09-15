package com.ticketmaster.eventservice.utils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter @Setter @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageResponse<E, R> {

    List<R> content;
    long totalElements;
    int totalPages;
    int pageNumber;
    int pageSize;

    public static <E, R> PageResponse of (Page<E> page, List<R> content) {
        return new PageResponse<>(
                content,
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize()
        );
    }
}

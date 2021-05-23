package com.hrs.common.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class Pagination<T> {

    private Long total;

    private Long pageNumber;

    private Long pageSize;

    private List<T> items;
}

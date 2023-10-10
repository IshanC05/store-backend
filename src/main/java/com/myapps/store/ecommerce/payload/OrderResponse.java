package com.myapps.store.ecommerce.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderResponse {
    private int pageSize;
    private int pageNumber;
    private int totalPage;
    private long totalElements;
    private List<OrderDto> content;
    private boolean isLastPage;
}

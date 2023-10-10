package com.myapps.store.ecommerce.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
    private String orderAddress;
    private int cartId;
}

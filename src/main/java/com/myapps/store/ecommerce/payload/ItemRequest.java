package com.myapps.store.ecommerce.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemRequest {
    private int productId;
    private int quantity;
}

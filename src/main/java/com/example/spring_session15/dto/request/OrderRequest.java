package com.example.spring_session15.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {
    private List<ItemRequest> items;

    @Data
    public static class ItemRequest {
        private Long productId;
        private Integer quantity;
    }
}
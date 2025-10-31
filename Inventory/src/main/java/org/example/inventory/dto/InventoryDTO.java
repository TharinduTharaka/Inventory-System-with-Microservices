package org.example.inventory.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDTO {
    private Long id;
    private String type;
    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("item_id")
    private Long itemId;
    private Integer quantity;
}

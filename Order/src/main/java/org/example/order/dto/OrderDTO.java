package org.example.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    @JsonProperty("item_id")
    private Integer itemId;
    @JsonProperty("order_date")
    private String orderDate;
    private Double amount;
}

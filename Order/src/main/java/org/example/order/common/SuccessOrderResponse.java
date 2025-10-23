package org.example.order.common;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import lombok.Getter;
import org.example.order.dto.OrderDTO;

@Getter
public class SuccessOrderResponse implements OrderResponse {
    @JsonUnwrapped
    private final OrderDTO order;

    public SuccessOrderResponse(OrderDTO order) {
        this.order = order;
    }
}

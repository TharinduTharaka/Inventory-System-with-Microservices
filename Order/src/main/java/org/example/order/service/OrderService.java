package org.example.order.service;


import org.example.inventory.dto.InventoryDTO;
import org.example.order.common.ErrorOrderResponse;
import org.example.order.common.OrderResponse;
import org.example.order.common.SuccessOrderResponse;
import org.example.order.dto.OrderDTO;
import org.example.order.entity.OrderEntity;
import org.example.order.repo.OrderRepo;
import org.example.product.dto.ProductDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@Transactional
public class OrderService {

    private final WebClient inventoryWebClient;
    private final WebClient productWebClient;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ModelMapper modelMapper;

    public OrderService(WebClient inventoryWebClient, WebClient productWebClient, OrderRepo orderRepo, ModelMapper modelMapper) {
        this.inventoryWebClient = inventoryWebClient;
        this.productWebClient = productWebClient;
        this.modelMapper = modelMapper;
        this.orderRepo = orderRepo;
    }


    public List<OrderDTO> getAllOrders() {
        List<OrderEntity> orderList = orderRepo.findAll();
        return modelMapper.map(orderList, new TypeToken<List<OrderDTO>>() {
        }.getType());
    }

    public OrderResponse saveOrder(OrderDTO orderDTO) {

        Integer itemId = orderDTO.getItemId();

        try {
            InventoryDTO inventoryResponse = inventoryWebClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/item/{itemId}").build(itemId))
                    .retrieve()
                    .bodyToMono(InventoryDTO.class)
                    .block();

            assert inventoryResponse != null;

            Long productId = inventoryResponse.getProductId();


            ProductDTO productResponse = productWebClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/{productId}").build(productId))
                    .retrieve()
                    .bodyToMono(ProductDTO.class)
                    .block();

            assert productResponse != null;


            if (inventoryResponse.getQuantity() > 0) {
                if (productResponse.getForSale() == 1) {
                    orderRepo.save(modelMapper.map(orderDTO, OrderEntity.class));
                    return new SuccessOrderResponse(orderDTO);
                }else {
                    return new ErrorOrderResponse("This item is not for sale");
                }

            } else {
                return new ErrorOrderResponse("Order failed: Item out of stock");
            }

        } catch (Exception e) {

        }

        return null;
    }

    public OrderDTO updateOrder(OrderDTO OrderDTO) {
        orderRepo.save(modelMapper.map(OrderDTO, OrderEntity.class));
        return OrderDTO;
    }

    public String deleteOrder(Long orderId) {
        orderRepo.deleteById(orderId);
        return "Order deleted";
    }

    public OrderDTO getOrderById(Long orderId) {
        OrderEntity order = orderRepo.getOrderById(orderId);
        return modelMapper.map(order, OrderDTO.class);
    }
}

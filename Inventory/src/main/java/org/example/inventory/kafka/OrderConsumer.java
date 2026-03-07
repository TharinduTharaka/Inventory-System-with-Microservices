package org.example.inventory.kafka;

import com.example.base.dto.OrderEventDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    private static final Logger logger = LoggerFactory.getLogger(OrderEventDto.class);

    @KafkaListener(
            topics = "${spring.kafka.template.default-topic}",
            groupId = "${spring.kafka.consumer.group-id}"
    )

    public void consume(OrderEventDto orderEvent) {
        logger.info("Received message from Kafka topic: {}", orderEvent);
        // Here you can add logic to update inventory based on the order event
    }
}

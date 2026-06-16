package com.smartpay.transactionservice.producer;

import com.smartpay.transactionservice.event.TransactionCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransactionEventProducer {

    private static final String TOPIC_NAME="transaction-created";

    private final KafkaTemplate<String, TransactionCreatedEvent>kafkaTemplate;

    public TransactionEventProducer(KafkaTemplate<String, TransactionCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishTransactionCreatedEvent(TransactionCreatedEvent event){
        kafkaTemplate.send(TOPIC_NAME,event);
        log.info("Transaction event published to Kafka. Transaction Id: {}",
                event.getTransactionId());
    }


}

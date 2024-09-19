package glebkr.member.kafka.consumer;


import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaTaskOutboxConsumerService {

    @RetryableTopic(attempts = "4", backoff = @Backoff(delay = 3000, multiplier = 1.5, maxDelay = 15000))
    @KafkaListener(topics = "task_outbox_topic", groupId = "task_outbox_group")
    public void consumeTaskOutboxMessages(String payload) {
        log.info("Event consumed {} ", payload);
    }

    @DltHandler
    public void listenDlt(String payload, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) String offset) {
        log.info("DLT Received: {} , from {} , offset {}", payload, topic, offset);
    }

}

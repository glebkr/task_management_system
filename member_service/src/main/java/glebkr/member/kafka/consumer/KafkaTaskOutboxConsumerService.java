package glebkr.member.kafka.consumer;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaTaskOutboxConsumerService {

    @KafkaListener(topics = "task_outbox_topic", groupId = "task_outbox_group")
    public void consumeTaskOutboxMessages(String payload) {
        log.info("Event consumed {} ", payload);
    }

}

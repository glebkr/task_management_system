package glebkr.task.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaTaskOutboxProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void produceTaskOutboxMessage(String topic, String payload) {
        kafkaTemplate.send(topic, payload);
        log.info("Message sent {}", payload);
    }

}

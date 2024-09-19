package glebkr.task.outbox.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

import glebkr.task.kafka.producer.KafkaTaskOutboxProducerService;
import glebkr.task.outbox.entity.Outbox;
import glebkr.task.outbox.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class OutboxWorkerService {

    private final OutboxRepository outboxRepository;
    private final KafkaTaskOutboxProducerService producerService;
    @Value("${kafka.task-outbox-topic}")
    private String taskOutboxTopic;

    @Scheduled(fixedDelay = 50000)
    public void pollOutboxMessagesAndPublish() {
        List<Outbox> outboxList = outboxRepository.findByIsProcessedFalse();

        outboxList.forEach(outbox -> {
                    producerService.produceTaskOutboxMessage(taskOutboxTopic, outbox.getPayload());
                    outbox.setIsProcessed(true);
                    outboxRepository.save(outbox);
                }

        );
    }
}

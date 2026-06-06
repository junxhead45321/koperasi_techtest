package com.example.koperasi.kafka;

import com.example.koperasi.model.event.KoperasiEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KoperasiEventProducer {

    private final KafkaTemplate<String, KoperasiEvent> kafkaTemplate;

    public void publishMemberEvent(KoperasiEvent event) {
        publish(KafkaTopics.MEMBER_EVENTS, event.getMemberId().toString(), event);
    }

    public void publishSavingEvent(KoperasiEvent event) {
        publish(KafkaTopics.SAVING_EVENTS, event.getMemberId().toString(), event);
    }

    public void publishLoanEvent(KoperasiEvent event) {
        publish(KafkaTopics.LOAN_EVENTS, event.getMemberId().toString(), event);
    }

    private void publish(String topic, String key, KoperasiEvent event) {
        kafkaTemplate.send(topic, key, event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to publish Kafka event. topic={}, key={}, eventType={}", topic, key, event.getEventType(), ex);
                        return;
                    }
                    log.info("Kafka event published. topic={}, key={}, eventType={}, offset={}",
                            topic,
                            key,
                            event.getEventType(),
                            result.getRecordMetadata().offset());
                });
    }
}

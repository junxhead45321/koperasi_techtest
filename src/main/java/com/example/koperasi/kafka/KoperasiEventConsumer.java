package com.example.koperasi.kafka;

import com.example.koperasi.model.event.KoperasiEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KoperasiEventConsumer {

    @KafkaListener(topics = KafkaTopics.MEMBER_EVENTS, groupId = "koperasi-service")
    public void consumeMemberEvent(KoperasiEvent event) {
        log.info("Member event consumed. eventType={}, memberId={}, memberName={}",
                event.getEventType(), event.getMemberId(), event.getMemberName());
    }

    @KafkaListener(topics = KafkaTopics.SAVING_EVENTS, groupId = "koperasi-service")
    public void consumeSavingEvent(KoperasiEvent event) {
        log.info("Saving event consumed. eventType={}, memberId={}, savingId={}, amount={}",
                event.getEventType(), event.getMemberId(), event.getSavingId(), event.getAmount());
    }

    @KafkaListener(topics = KafkaTopics.LOAN_EVENTS, groupId = "koperasi-service")
    public void consumeLoanEvent(KoperasiEvent event) {
        log.info("Loan event consumed. eventType={}, memberId={}, loanId={}, status={}",
                event.getEventType(), event.getMemberId(), event.getLoanId(), event.getStatus());
    }
}

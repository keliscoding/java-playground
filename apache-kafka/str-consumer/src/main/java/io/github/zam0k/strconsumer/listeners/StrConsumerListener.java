package io.github.zam0k.strconsumer.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StrConsumerListener {

    @KafkaListener(
            groupId = "group-1",
            topics = "str-topic",
            containerFactory = "strContainerFactory"
    )
    public void listener(String message) {
        log.info("receive message {}", message);
    }

}

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
    public void create(String message) {
        log.info("CREATE ::: Receive message {}", message);
    }

    @KafkaListener(
            groupId = "group-1",
            topics = "str-topic",
            containerFactory = "strContainerFactory"
    )
    public void log(String message) {
        log.info("LOG ::: Receive message {}", message);
    }

}

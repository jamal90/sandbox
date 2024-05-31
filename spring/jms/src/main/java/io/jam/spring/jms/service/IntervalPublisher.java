package io.jam.spring.jms.service;

import io.jam.spring.jms.config.JmsConfig;
import io.jam.spring.jms.model.HelloMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class IntervalPublisher {

    private final JmsTemplate jmsTemplate;

    @Scheduled(fixedDelay = 2000)
    public void sendMessage() {

        System.out.println("Thread: " + Thread.currentThread().getName());
        HelloMessage msg1 = HelloMessage.builder()
                .uuid(UUID.randomUUID())
                .message("Hello World " + new Random().nextInt(10000))
                .build();

        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, msg1);

        System.out.println("Message sent!");
    }
}

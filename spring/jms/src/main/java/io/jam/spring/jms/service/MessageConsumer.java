package io.jam.spring.jms.service;

import io.jam.spring.jms.config.JmsConfig;
import io.jam.spring.jms.model.HelloMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Message;

@Component
public class MessageConsumer {

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(@Payload HelloMessage helloMessage,
                       @Headers MessageHeaders messageHeaders, Message message) {
        System.out.println("Message Received: " + helloMessage);
    }
}

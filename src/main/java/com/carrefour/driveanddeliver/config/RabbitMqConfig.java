package com.carrefour.driveanddeliver.config;

import com.carrefour.driveanddeliver.model.DeliveryMethod;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@EnableRabbit
@Configuration
public class RabbitMqConfig {

    @Value("${amqp.exchange.name}")
    private String exchangeName;

    @Value("${amqp.queue.drive.name}")
    private String driveQueueName;

    @Value("${amqp.queue.delivery_asap.name}")
    private String deliveryAsapQueueName;

    @Bean
    public Declarables exchangeBinding() {
        DirectExchange exchange = new DirectExchange(exchangeName);

        Queue driveQueue = createQueue(driveQueueName);
        Queue deliveryAsapQueue = createQueue(deliveryAsapQueueName);

        return new Declarables(
                exchange,
                driveQueue,
                deliveryAsapQueue,
                BindingBuilder.bind(driveQueue).to(exchange).with(DeliveryMethod.DRIVE),
                BindingBuilder.bind(deliveryAsapQueue).to(exchange).with(DeliveryMethod.DELIVERY_ASAP));
    }

    private Queue createQueue(String queueName) {
        Map<String, Object> args = new HashMap<>();
        // set the queue with a dead letter feature
        args.put("x-dead-letter-exchange", "");
        args.put("x-dead-letter-routing-key", "dead.letter." + queueName);
        return new Queue(queueName, true, false, false, args);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonConverter() {
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory rabbitConnectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(rabbitConnectionFactory);
        rabbitTemplate.setMessageConverter(jsonConverter());
        return rabbitTemplate;
    }
}

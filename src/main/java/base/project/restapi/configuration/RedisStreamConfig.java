package base.project.restapi.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;
import base.project.restapi.consumer.model.ExampleConsumerModel;
import base.project.restapi.util.Topic;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;

@Configuration
public class RedisStreamConfig {
    @Autowired
    private StreamListener<String, ObjectRecord<String, ExampleConsumerModel>> streamExampleListeners;

    @Bean
    public Subscription subscription(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        try {
            var options = StreamMessageListenerContainer
                .StreamMessageListenerContainerOptions
                .builder()
                .pollTimeout(Duration.ofSeconds(1))
                .targetType(ExampleConsumerModel.class)
                .build();

            var listenerContainer = StreamMessageListenerContainer
                .create(redisConnectionFactory, options);

            var subscription = listenerContainer.receiveAutoAck(
                Consumer.from(Topic.EXAMPLE_SERVICE_EVENT, InetAddress.getLocalHost().getHostName()),
                StreamOffset.create(Topic.EXAMPLE_SERVICE_EVENT, ReadOffset.lastConsumed()),
                streamExampleListeners
            );

            listenerContainer.start();

            System.out.println("RUN LISTENER CONFIG");

            return subscription;
        } catch (Exception err) {
            Logger loggerFactory = LoggerFactory.getLogger(RedisStreamConfig.class);

            loggerFactory.error(err.getMessage());

            return null;
        }
    }
}

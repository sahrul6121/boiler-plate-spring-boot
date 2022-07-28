package base.project.restapi.consumer;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Service;
import base.project.restapi.consumer.model.ExampleConsumerModel;
import base.project.restapi.service.model.IExampleService;
import base.project.restapi.util.Topic;

import java.net.InetAddress;

@Service
@Slf4j
public class ExampleConsumer implements StreamListener<String, ObjectRecord<String, ExampleConsumerModel>> {
    @Autowired
    private ReactiveRedisTemplate<String, String> redisTemplate;

    @Autowired
    private IExampleService exa;

    @Override
    @SneakyThrows
    public void onMessage(ObjectRecord<String, ExampleConsumerModel> record) {
        System.out.println(
            InetAddress.getLocalHost().getHostName() + " - consumed :" +
                record.getValue() + record.getStream() + record.getClass()
        );

        this.redisTemplate
            .opsForZSet()
            .incrementScore("revenue", record.getValue().toString(), 1)
            .subscribe();

        ExampleConsumerModel userConsumerModel = record.getValue();

        switch (userConsumerModel.getTopic()) {
            case Topic.EXAMPLE_SERVICE_EVENT_TRIGGER:
                // Add Some Action here
                break;
        }
    }
}

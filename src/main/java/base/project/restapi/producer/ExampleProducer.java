package base.project.restapi.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import base.project.restapi.model.ExampleModel;
import base.project.restapi.producer.model.ProduceModel;
import base.project.restapi.util.Topic;


@Service
public class ExampleProducer {
    @Autowired
    private ReactiveRedisTemplate<String, String> redisTemplate;

    public void publishEventCreate(ExampleModel exampleModel) {
        ObjectRecord<String, ProduceModel> record = StreamRecords.newRecord()
            .ofObject(
                formatRecord(Topic.EXAMPLE_SERVICE_EVENT_TRIGGER, exampleModel, null)
            )
            .withStreamKey(Topic.EXAMPLE_SERVICE_EVENT);

        this.redisTemplate.opsForStream()
            .add(record)
            .subscribe(System.out::println);
    }


    public ProduceModel formatRecord(String topic, ExampleModel data, ExampleModel old) {
        ProduceModel produceModel = new ProduceModel();

        produceModel.setTopic(topic);
        produceModel.setData(data);
        produceModel.setOld(null);

        return produceModel;
    }
}

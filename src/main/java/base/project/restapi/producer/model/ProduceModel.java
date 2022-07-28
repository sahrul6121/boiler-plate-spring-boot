package base.project.restapi.producer.model;

import lombok.Getter;
import lombok.Setter;
import base.project.restapi.base.model.BaseModel;

@Getter
@Setter
public class ProduceModel {
    String topic;

    BaseModel data;

    BaseModel old;
}

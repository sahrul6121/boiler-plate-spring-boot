package base.project.restapi.base.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseConsumerModel {
    String topic;

    BaseModel data;

    BaseModel old;
}

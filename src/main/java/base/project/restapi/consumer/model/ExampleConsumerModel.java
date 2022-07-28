package base.project.restapi.consumer.model;

import lombok.Getter;
import lombok.Setter;
import base.project.restapi.base.model.BaseConsumerModel;
import base.project.restapi.model.ExampleModel;

@Getter
@Setter
public class ExampleConsumerModel extends BaseConsumerModel {
    ExampleModel data;

    ExampleModel old;
}

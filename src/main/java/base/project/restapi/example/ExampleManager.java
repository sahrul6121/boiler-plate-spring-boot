package base.project.restapi.example;

import base.project.restapi.helper.MessageHelper;
import base.project.restapi.model.ExampleModel;
import base.project.restapi.base.manager.BaseManager;
import base.project.restapi.service.ExampleService;

public class ExampleManager extends BaseManager<Object, ExampleService> {
    public ExampleManager(
        ExampleModel exampleModel,
        ExampleService exampleService,
        MessageHelper messageHelper
    ) {
        super(exampleModel, exampleService, messageHelper);
    }

    public Object apply() {
        return new Object();
    }
}

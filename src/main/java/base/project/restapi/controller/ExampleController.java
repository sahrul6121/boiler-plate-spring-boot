package base.project.restapi.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import base.project.restapi.base.controller.BaseController;
import base.project.restapi.helper.MessageHelper;
import base.project.restapi.service.ExampleService;
import base.project.restapi.util.ApiUrl;

@RestController
@RequestMapping(ApiUrl.USER_PREFIX)
public class ExampleController extends BaseController {
    ExampleService exampleService;

    @Autowired
    public ExampleController(
        ExampleService exampleService,
        ModelMapper modelMapper,
        MessageHelper messageSource
    ) {
        super(ExampleController.class, messageSource, modelMapper);

        this.exampleService = exampleService;
    }
}

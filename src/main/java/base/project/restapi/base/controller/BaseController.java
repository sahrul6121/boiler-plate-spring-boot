package base.project.restapi.base.controller;

import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import base.project.restapi.helper.MessageHelper;
import base.project.restapi.transformers.model.UserGatewayTransformerModel;

public abstract class BaseController {
    public Logger loggerFactory;

    public MessageHelper messageHelper;

    public ModelMapper modelMapper;

    public BaseController(Class<?> controller, MessageHelper messageHelper, ModelMapper modelMapper) {
        loggerFactory = LoggerFactory.getLogger(controller.getName());

        this.messageHelper = messageHelper;

        this.modelMapper = modelMapper;
    }

    public UserGatewayTransformerModel parseUser(String user) {
        JSONObject userObject = new JSONObject(user);

        UserGatewayTransformerModel userPrincipalTransformModel = new UserGatewayTransformerModel();

        userPrincipalTransformModel.setId(userObject.getInt("id"));

        userPrincipalTransformModel.setUsername(userObject.getString("username"));

        return  userPrincipalTransformModel;
    }
}

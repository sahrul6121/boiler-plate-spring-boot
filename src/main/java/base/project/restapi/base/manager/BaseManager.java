package base.project.restapi.base.manager;

import base.project.restapi.helper.MessageHelper;

public abstract class BaseManager<BaseModel, IBaseService> {
    public BaseModel entity;

    public IBaseService service;

    public MessageHelper messageHelper;

    public BaseManager(BaseModel baseModel, IBaseService baseService, MessageHelper messageHelper) {
        this.entity = baseModel;
        this.service = baseService;
        this.messageHelper = messageHelper;
    }

    public BaseModel apply() {
        return null;
    }

    public void validation() throws Throwable {

    }
}

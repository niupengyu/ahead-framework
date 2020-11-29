package com.github.niupengyu.core.message;

public abstract class SimpleMessageService<T> extends MessageService<T>{

    private MultipleMessageService multipleMessageService;


    @Override
    protected void endExecute() {
        endOne();
        multipleMessageService.endOne(1);
    }

    protected abstract void endOne();

    public void init(MessageManager messageManager, MultipleMessageService multipleMessageService) {
        super.init(messageManager);
        this.multipleMessageService=multipleMessageService;
    }
}

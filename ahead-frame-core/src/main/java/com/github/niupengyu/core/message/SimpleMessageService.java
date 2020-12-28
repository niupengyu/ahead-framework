package com.github.niupengyu.core.message;

public abstract class SimpleMessageService<T> extends MessageService<T>{

    private MultipleMessageService multipleMessageService;


    @Override
    protected void endExecute() {
        multipleMessageService.endOne(1);
    }

    @Override
    public void run() {
        while(true){
            T messageBean=this.getMessage();
            if(messageBean==null){
                break;
            }
            this.execute(messageBean);
        }
        this.endExecute();
    }


    public void init(MessageManager messageManager, MultipleMessageService multipleMessageService) {
        super.init(messageManager);
        this.multipleMessageService=multipleMessageService;
    }
}

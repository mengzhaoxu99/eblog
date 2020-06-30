package com.mengzhaoxu.eblog.common.mq;
import com.mengzhaoxu.eblog.config.MqConfig;
import com.mengzhaoxu.eblog.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MQRecevier {


    @Autowired
    private SearchService searchService;

    @RabbitListener(queues = MqConfig.POST_QUEUE)
    public void receiveSpikeMesage(PostMqIndexMessage message){
        log.info("recive queues:"+message);

        switch (message.getType()) {
            case PostMqIndexMessage.CREATE_OR_UPDATE:
                searchService.createOrUpdateIndex(message);
                break;
            case PostMqIndexMessage.REMOVE:
                searchService.removeIndex(message);
                break;
            default:
                log.error("没找到对应的消息类型，请注意！！ --》 {}", message.toString());
                break;
        }







    }



}

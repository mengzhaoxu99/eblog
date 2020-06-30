package com.mengzhaoxu.eblog.common.mq;


import com.mengzhaoxu.eblog.config.MqConfig;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j
public class MQSender {



    @Autowired
    private AmqpTemplate amqpTemplate;


//    /**
//     * 发送秒杀消息
//     * @param message
//     */
//    public void sendSpikeMessage(SpikeMessage message){
//        String s = RedisUtils.toJson(message);
//        log.info("send miaosha:"+s);
//        amqpTemplate.convertAndSend(MQConfig.SPIKE_QUEUE,s);
//    }


    public void sendESMessage(Object message){
        String s = String.valueOf(message);
        amqpTemplate.convertAndSend(MqConfig.POST_EXCHANGE,MqConfig.POST_ROUTINGKEY,message);
        log.info("send"+s);
    }
//
//    public void sendTopic(Object message){
//        String s = String.valueOf(message);
//        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE,"topic.key1",message+"======1");
//        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE,"topic.#",message+"======2");
//        log.info("send:"+s);
//    }
//
//
//    public void sendFanout(Object message){
//        String s = String.valueOf(message);
//        amqpTemplate.convertAndSend(MQConfig.Fanout_EXCHANGE,"",s);
//        log.info("send Fanout:"+s);
//    }
//
//    	public void sendHeader(Object message) {
//		String msg = String.valueOf(message);
//		log.info("send headers message:"+msg);
//		MessageProperties properties = new MessageProperties();
//		properties.setHeader("header1", "v1");
//		properties.setHeader("header2", "v2");
//		Message obj = new Message(msg.getBytes(), properties);
//		amqpTemplate.convertAndSend(MQConfig.HEADERS_EXCHANGE, "", obj);
//	}



}
